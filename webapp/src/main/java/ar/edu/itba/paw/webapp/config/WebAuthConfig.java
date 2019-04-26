package ar.edu.itba.paw.webapp.config;

import ar.edu.itba.paw.webapp.auth.CostumeUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import java.io.*;
import java.util.concurrent.TimeUnit;

@Configuration
@EnableWebSecurity
@ComponentScan("ar.edu.itba.paw.webapp.auth")
public class WebAuthConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private CostumeUserDetailsService userDetailsService;
    
    @Value("classpath:rememberme.key")
    private Resource rememberMeKey;

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http.userDetailsService(userDetailsService).sessionManagement()
//                .invalidSessionUrl("/logIn") //upon logout, you will be forwarded here por ahora no lo usamos
                .and().authorizeRequests()
                    .antMatchers("/", "/sign-up").permitAll()
                    .antMatchers("/log-in").anonymous()
//                    .antMatchers("/admin/**").hasRole("ADMIN")
                    .antMatchers("/**").authenticated()
                .and().formLogin()
                    .usernameParameter("j_username")
                    .passwordParameter("j_password")
                    .defaultSuccessUrl("/", true) //the landing page after a successful login
                    .loginPage("/log-in") //the custom login page
                    //loginProcessingUrl() – the url to submit the username and password to
                .and().rememberMe()
                    .rememberMeParameter("j_rememberme")
                    .userDetailsService(userDetailsService)
                    .key(getRememberMeKey())
                    .tokenValiditySeconds((int) TimeUnit.DAYS.toSeconds(30))
                .and().logout()
                    .logoutUrl("/logout")
                    .logoutSuccessUrl("/")
//                .and().exceptionHandling()
//                    .accessDeniedPage("/403")
                .and().csrf()
                    .disable();
    }


    @Override
    public void configure(final WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/css/**", "/js/**", "/img/**", "/favicon.ico", "/403");
    }


    private String getRememberMeKey() {
        final StringWriter stringWriter = new StringWriter();
        try (Reader reader = new InputStreamReader(rememberMeKey.getInputStream())){
            final char[] buf = new char[1024];
            int length;
            while ((length = reader.read(buf)) != -1){
                stringWriter.write(buf,0,length);
            }
        } catch (IOException e){
            throw new RuntimeException(e);
        }

        return stringWriter.toString();
    }
}
