package ar.edu.itba.paw.presistence;

import org.hsqldb.jdbc.JDBCDriver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.DatabasePopulator;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import javax.sql.DataSource;

@ComponentScan({ "ar.edu.itba.paw.persistence", })
@Configuration
public class TestConfig {
    @Bean
    public DataSource dataSource() {
        final SimpleDriverDataSource ds = new SimpleDriverDataSource();
        ds.setDriverClass(JDBCDriver.class);
        ds.setUrl("jdbc:hsqldb:mem:paw");
        ds.setUsername("ha");
        ds.setPassword("");
        return ds;
    }

    @Value("classpath:sql/a_create_tables.sql")
    private Resource createTables;
    @Value("classpath:sql/b_changas_public_users.sql")
    private Resource populateUsers;
    @Value("classpath:sql/c_changas_public_changas.sql")
    private Resource populateChangas;
    @Value("classpath:sql/d_changas_public_user_inscribed.sql")
    private Resource populateUsersInscribed;
    @Value("classpath:sql/e_changas_public_filters.sql")
    private Resource populateCategories;

    @Bean
    public DataSourceInitializer dataSourceInitializer(final DataSource ds) {
        final DataSourceInitializer dsi = new DataSourceInitializer();
        dsi.setDataSource(ds);
        dsi.setDatabasePopulator(databasePopulator());
        return dsi;
    }

    private DatabasePopulator databasePopulator() {
        final ResourceDatabasePopulator dbp = new ResourceDatabasePopulator();
        dbp.addScript(createTables);
        dbp.addScript(populateUsers);
        dbp.addScript(populateChangas);
        dbp.addScript(populateCategories);
        dbp.addScript(populateUsersInscribed);
        return dbp;
    }
}