package ar.edu.itba.paw.webapp.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

import javax.sql.DataSource;
import java.nio.charset.StandardCharsets;

@EnableWebMvc
@ComponentScan({ "ar.edu.itba.paw.webapp.controllers" , "ar.edu.itba.paw.services", "ar.edu.itba.paw.persistence"  })
@Configuration
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

    @Bean
    public DataSource dataSource() {

        final SimpleDriverDataSource ds = new SimpleDriverDataSource();
        ds.setDriverClass(org.postgresql.Driver.class);
        // todo sacar para la entrega

        boolean local = Boolean.valueOf(System.getenv("CHANGAS_LOCAL")); // cambiar esto si quieren conectarse a la db local
        String url = local? "jdbc:postgresql://localhost/changas": "jdbc:postgresql://isilo.db.elephantsql.com";
        String username = local? System.getenv("CHANGAS_USERNAME"): "phhlctzu";
        String passwd = local? System.getenv("CHANGAS_PASSWD"): "YYoLBl5QrXsPA2ga-akkGimITHTfmyTL";

        ds.setUrl(url);
        ds.setUsername(username);
        ds.setPassword(passwd);
        System.out.println(String.format("//////\nConectando a la BD %s con el usuario %s y la contraseña %s\n//////",url, username,passwd));
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

}
