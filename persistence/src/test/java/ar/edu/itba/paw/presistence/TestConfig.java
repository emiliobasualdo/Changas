package ar.edu.itba.paw.presistence;

import org.hsqldb.jdbc.JDBCDriver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

import javax.sql.DataSource;

@ComponentScan({ "ar.edu.itba.paw.persistence", })
@Configuration
public class TestConfig {
    @Bean
    public DataSource dataSource() {
        final SimpleDriverDataSource ds = new SimpleDriverDataSource();
 /*       ds.setDriverClass(org.postgresql.Driver.class);
        String username = System.getenv("CHANGAS_USERNAME");
        String passwd = System.getenv("CHANGAS_PASSWD");
        ds.setUrl("jdbc:postgresql://localhost/changas_testing");
        ds.setUsername(username);
        ds.setPassword(passwd);*/
        ds.setDriverClass(JDBCDriver.class);
        ds.setUrl("jdbc:hsqldb:mem:paw");
        ds.setUsername("ha");
        ds.setPassword("");
        return ds;
    }
}