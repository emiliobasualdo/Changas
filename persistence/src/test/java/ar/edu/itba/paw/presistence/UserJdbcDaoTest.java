package ar.edu.itba.paw.presistence;

import ar.edu.itba.paw.interfaces.util.Validation;
import ar.edu.itba.paw.models.Either;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.persistence.UserJdbcDao;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.jdbc.JdbcTestUtils;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

import static ar.edu.itba.paw.constants.DBTableName.users;
import static ar.edu.itba.paw.constants.DBUserFields.*;
import static ar.edu.itba.paw.interfaces.util.Validation.ErrorCodes.*;
import static org.junit.Assert.*;

@Sql("classpath:sql/a_create_tables.sql")
//@RunWith(MockitoJUnitRunner.class)
//@PrepareForTest(fullyQualifiedNames = "ar.edu.itba.paw.presistence.*")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Rollback
public class UserJdbcDaoTest {

    private static final String PASSWORD = "Password";
    private static final String EMAIL = "Email@email.com";
    private static final long ID = 123;

    @Autowired
    private DataSource ds;

    private JdbcTemplate jdbcTemplate;

    @Autowired
    private UserJdbcDao userDao;

    private SimpleJdbcInsert jdbcInsert;


    @Before
    public void setUp() {
        jdbcTemplate = new JdbcTemplate(ds);
        JdbcTestUtils.deleteFromTables(jdbcTemplate, users.name());
        jdbcInsert = new SimpleJdbcInsert(ds)
                .withTableName(users.name())
                .usingGeneratedKeyColumns(user_id.name());
    }

    //TODO No puedo usar métodos auxiliares de la clase que quiero probar, solo los métodos que quiero probar

    @Test
    public void testCreateUser_returnsNewUser() throws Exception {
        // SETUP // todo private methods
        /*User user = PowerMockito.mock(User.class);
        PowerMockito.when(user.getEmail()).thenReturn(EMAIL);
        PowerMockito.when(user.getPasswd()).thenReturn(PASSWORD);
        PowerMockito.when(userDao, method(UserJdbcDao.class, "getUserFromUserId", long.class))
                .withArguments(anyString(), anyInt())
                .thenReturn(user);*/
        // EJERCITAR
        final Either<User, Validation> either = userDao.create(
                new User.Builder()
                .withEmail(EMAIL)
                .withPasswd(PASSWORD)
        );
        // ASSERT
        assertNotNull(either.getValue());
        assertEquals(EMAIL, either.getValue().getEmail());
        assertEquals(PASSWORD, either.getValue().getPasswd());
        assertEquals(1, JdbcTestUtils.countRowsInTable(jdbcTemplate, users.name()));
    }

    @Test
    public void testCreateUser_returnsError() {
        // Testeamos que se arroje un validation Error si ya existe el usuario
        // SETUP: Make sure the user is in the DB
        Map<String, Object> userRow = new HashMap<>();
        userRow.put(email.name(), EMAIL);
        userRow.put(passwd.name(), PASSWORD);
        jdbcInsert.executeAndReturnKey(userRow);
        // EXERCISE
        final Validation validation = userDao.create(
                new User.Builder()
                        .withEmail(EMAIL)
                        .withPasswd(PASSWORD)
        ).getAlternative();
        // ASSERT
        assertNotNull(validation);
        assertNotNull(validation.getEc());
        assertEquals(USER_ALREADY_EXISTS, validation.getEc());
    }

    @Test
    public void testGetById_returnsUser1() {
        // Testeamos el método, independientemente del query, jdbcTemplate y la DB.
        // SETUP: Make sure the user is in the DB
        Map<String, Object> userRow = new HashMap<>();
        userRow.put(email.name(), EMAIL);
        userRow.put(passwd.name(), PASSWORD);
        Number userId = jdbcInsert.executeAndReturnKey(userRow);
        // EXERCISE
        User user = userDao.getById(userId.longValue()).getValue();
        // ASSERT
        assertNotNull(user);
        assertEquals(EMAIL, user.getEmail());
        assertEquals(PASSWORD, user.getPasswd());
    }

    @Test
    public void testGetById_returnsUser2() {
        // Testeamos el método considerando el query
        // SETUP: Make sure the user is in the DB
        Map<String, Object> userRow = new HashMap<>();
        userRow.put(email.name(), EMAIL);
        userRow.put(passwd.name(), PASSWORD);
        Number userId = jdbcInsert.executeAndReturnKey(userRow);
        // EXERCISE
        User user = userDao.getById(userId.longValue()).getValue();
        // ASSERT
        assertNotNull(user);
        assertEquals(userId.longValue(), user.getUser_id());
        assertEquals(EMAIL, user.getEmail());
        assertEquals(PASSWORD, user.getPasswd());
    }

    @Test
    public void testGetById_returnsError() {
        // Testeamos que retorne error si no existe
        // SETUP: No debería haber nada en al DB
        // EXERCISE
        Validation validation = userDao.getById(ID).getAlternative();
        // ASSERT
        assertNotNull(validation);
        assertEquals(validation.getEc(), NO_SUCH_USER);
    }

    @Test
    public void testFindByMail_returnsUser1() {
        // Testeamos el método, independientemente del query, jdbcTemplate y la DB.
        // SETUP: Make sure the user is in the DB
        Map<String, Object> userRow = new HashMap<>();
        userRow.put(email.name(), EMAIL);
        userRow.put(passwd.name(), PASSWORD);
        Number userId = jdbcInsert.executeAndReturnKey(userRow);
        // EXERCISE
        User user = userDao.findByMail(EMAIL).getValue();
        // ASSERT
        assertNotNull(user);
        assertEquals(userId.longValue(), user.getUser_id());
        assertEquals(EMAIL, user.getEmail());
        assertEquals(PASSWORD, user.getPasswd());
    }

    @Test
    public void testFindByMail_returnsUser2() {
        // Testeamos el método considerando el query
        // SETUP: Make sure the user is in the DB
        Map<String, Object> userRow = new HashMap<>();
        userRow.put(email.name(), EMAIL);
        userRow.put(passwd.name(), PASSWORD);
        Number userId = jdbcInsert.executeAndReturnKey(userRow);
        // EXERCISE
        User user = userDao.findByMail(EMAIL).getValue();
        // ASSERT
        assertNotNull(user);
        assertEquals(userId.longValue(), user.getUser_id());
        assertEquals(EMAIL, user.getEmail());
        assertEquals(PASSWORD, user.getPasswd());
    }

    @Test
    public void testFindByMail_returnsError1() {
        // Testeamos que retorne error si no existe
        // SETUP: No debería haber nada en al DB
        // EXERCISE
        Validation validation = userDao.findByMail(EMAIL).getAlternative();
        // ASSERT
        assertNotNull(validation);
        assertEquals(validation.getEc(), NO_SUCH_USER);
    }

    @Test
    public void testSetUserStatus__returnsCorrectValidation() {
        // Testeamos que se pueda cambair al estado que queremos
        // SETUP: Make sure the user is in the DB
        Map<String, Object> userRow = new HashMap<>();
        userRow.put(email.name(), EMAIL);
        userRow.put(passwd.name(), PASSWORD);
        userRow.put(enabled.name(), false);
        Number userId = jdbcInsert.executeAndReturnKey(userRow);
        // EXERCISE
        Validation val = userDao.setUserStatus(userId.longValue(), true);
        Validation val2 = userDao.setUserStatus(userId.longValue(), false);
        // ASSERT
        assertNotNull(val);
        assertEquals(val.getEc(), OK);
        assertNotNull(val2);
        assertEquals(val.getEc(), OK);
    }

    @Test
    public void testSetUserStatus__returnsErrorValidation() {
        // Testeamos que retorne el usuario que buscamos
        // SETUP: Make sure the user is NO user in the DB
        // EXERCISE
        Validation val = userDao.setUserStatus(ID, true);
        // ASSERT
        assertNotNull(val);
        assertNotEquals(val.getEc(), OK);
        assertNotEquals(val.getEc(), DATABASE_ERROR);
        assertEquals(val.getEc(), NO_SUCH_USER);
    }
}

