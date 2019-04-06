package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.daos.GeneralDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

import static ar.edu.itba.paw.constants.DBInscriptionState.requested;

@Repository
public class GeneralJdbcDao implements GeneralDao {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public GeneralJdbcDao(final DataSource ds) {
        jdbcTemplate = new JdbcTemplate(ds);
        createTables();
    }

    @Override
    public void createTables() { // todo preguntar como mejorar esto
        String query =
            "CREATE TABLE IF NOT EXISTS users ( " +
                "user_id    SERIAL PRIMARY KEY, " +
                "name       VARCHAR(100), " +
                "surname    VARCHAR(100), " +
                "tel        VARCHAR(100)," +
                "mail       VARCHAR(100)," +
                "passwd     VARCHAR(100)" +
            "); " +

            "CREATE TABLE IF NOT EXISTS changas ( " +
                "changa_id      SERIAL PRIMARY KEY, " +
                "user_id        SERIAL, " +
                "street         VARCHAR(100), " +
                "neighborhood 	VARCHAR(100), " +
                "number 		INTEGER," +
                "creation_date  TIMESTAMP, " +
                "title          VARCHAR(100), " +
                "description    VARCHAR(100), " +
                "state 			VARCHAR(100), " +
                "price 			DOUBLE PRECISION, " +
                "FOREIGN KEY (user_id) REFERENCES users(user_id)" +
            "); " +

            "CREATE TABLE IF NOT EXISTS user_owns ( " +
                "user_id        SERIAL, " +
                "changa_id      SERIAL UNIQUE, " +
                "FOREIGN KEY (user_id) REFERENCES users(user_id), " +
                "FOREIGN KEY (changa_id) REFERENCES changas(changa_id)" +
            "); " +

            "CREATE TABLE IF NOT EXISTS user_inscribed ( " +
                "user_id        SERIAL , " +
                "changa_id      SERIAL, " +
                "state 		    VARCHAR(100) DEFAULT "+requested.value()+","  +
                "FOREIGN KEY (user_id) REFERENCES users(user_id), " +
                "FOREIGN KEY (changa_id) REFERENCES changas(changa_id)" +
            ");";

        jdbcTemplate.execute(query);
    }
}
