package ar.edu.itba.paw.webapp.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

import javax.sql.DataSource;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

@EnableWebMvc
@ComponentScan({ "ar.edu.itba.paw.webapp.controllers" , "ar.edu.itba.paw.services", "ar.edu.itba.paw.persistence"})
@Configuration
@EnableTransactionManagement
@PropertySource("classpath:/config/application.properties")
public class WebConfig extends WebMvcConfigurerAdapter {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry
                .addResourceHandler("/webjars/**")
                //.addResourceLocations("classpath:/META-INF/resources/webjars/");
                .addResourceLocations("/webjars/");
    }

    @Bean
    public ViewResolver viewResolver() {
        final InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setViewClass(JstlView.class);
        viewResolver.setPrefix("/WEB-INF/jsp/");
        viewResolver.setSuffix(".jsp");
        return viewResolver;
    }

    @Value( "${datasource.url}" )
    private String dbUrl;
    @Value( "${datasource.username}" )
    private String username;
    @Value( "${datasource.password}" )
    private String password;

    @Bean
    public DataSource dataSource() {

        final SimpleDriverDataSource ds = new SimpleDriverDataSource();
        ds.setDriverClass(org.postgresql.Driver.class);

        //boolean local = Boolean.valueOf(System.getenv("CHANGAS_LOCAL")); // cambiar esto si quieren conectarse a la db local
        //String url = local? "jdbc:postgresql://localhost/changas": "jdbc:postgresql://10.16.1.110/paw-2019a-3";
        //String username = local? System.getenv("CHANGAS_USERNAME"): "paw-2019a-3";
        //String passwd = local? System.getenv("CHANGAS_PASSWD"): "tbpkI6aN8";
        Database db = new Database();
        System.out.println(db.getUrl());
        ds.setUrl(dbUrl);
        ds.setUsername(username);
        ds.setPassword(password);
        System.out.println(String.format("//////\nConectando a la BD %s con el usuario %s y la contraseña %s\n//////",dbUrl, username, password));

        return ds;
    }

    @Bean
    public MessageSource messageSource() {
        final ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("classpath:i18n/messages");
        messageSource.setDefaultEncoding(StandardCharsets .UTF_8.displayName());
        messageSource.setCacheSeconds(5);
        return messageSource;
    }

    @Bean
    public JavaMailSender getJavaMailSender() {
        JavaMailSenderImpl ms = new JavaMailSenderImpl();
        ms.setHost("smtp.gmail.com");
        ms.setPort(587);

        ms.setUsername("changas.do.not.reply@gmail.com");
        ms.setPassword("charlyGarcia23");

        Properties mailProperties = ms.getJavaMailProperties();
        mailProperties.put("mail.transport.protocol", "smtp");
        mailProperties.put("mail.smtp.auth", true);
        mailProperties.put("mail.smtp.starttls.enable", true);
        mailProperties.put("mail.smtp.debug", true);

        return ms;
    }

    @Bean
    public PlatformTransactionManager transactionManager(final DataSource ds) {
        return new DataSourceTransactionManager(ds);
    }

    @ConfigurationProperties(prefix = "datasource")
    private class Database {
        String url;
        String username;
        String password;
        // standard getters and setters

        public String getUrl() {
            return url;
        }

        public String getUsername() {
            return username;
        }

        public String getPassword() {
            return password;
        }
    }
}
