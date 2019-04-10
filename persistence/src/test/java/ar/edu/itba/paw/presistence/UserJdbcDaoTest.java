package ar.edu.itba.paw.presistence;

import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.persistence.UserJdbcDao;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.jdbc.JdbcTestUtils;

import javax.sql.DataSource;

import static ar.edu.itba.paw.constants.DBTableName.users;
import static ar.edu.itba.paw.constants.DBUserFields.email;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@Sql("classpath:sql/a_create_tables.sql")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class UserJdbcDaoTest {

    private static final String PASSWORD = "Password";
    private static final String EMAIL = "Email@email.com";

    @Autowired
    private DataSource ds;

    @Autowired
    private UserJdbcDao userDao;

    private JdbcTemplate jdbcTemplate;

    @Before
    public void setUp() {
        jdbcTemplate = new JdbcTemplate(ds);
        JdbcTestUtils.deleteFromTables(jdbcTemplate, users.name());
    }

    @Test
    public void testCreate() {
        // SETUP
        // todo MAL,no puedo usar métodos de la clase que quiero probar
        final User user = userDao.create(new User.Builder()
                .withEmail(EMAIL)
                .withPasswd(PASSWORD)
        ).getValue();
        // EJERCITAR

        // ASSERT
        assertNotNull(user);
        assertEquals(EMAIL, user.getEmail());
        assertEquals(PASSWORD, user.getPasswd());
        assertEquals(1, JdbcTestUtils.countRowsInTable(jdbcTemplate, users.name()));
        JdbcTestUtils.countRowsInTableWhere(
                jdbcTemplate, users.name(),String.format("%s = '%s'", email.name(), EMAIL)
        );
    }

    /*@Test
    public void testFindById() {

    }*/
}
