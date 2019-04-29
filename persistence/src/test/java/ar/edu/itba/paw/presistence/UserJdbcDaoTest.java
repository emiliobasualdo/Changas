package ar.edu.itba.paw.presistence;

import ar.edu.itba.paw.interfaces.util.Validation;
import ar.edu.itba.paw.models.Either;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.persistence.UserJdbcDao;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.jdbc.JdbcTestUtils;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static ar.edu.itba.paw.constants.DBTableName.users;
import static ar.edu.itba.paw.constants.DBUserFields.*;
import static ar.edu.itba.paw.interfaces.util.Validation.ErrorCodes.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;

@Sql("classpath:sql/a_create_tables.sql")
//@RunWith(MockitoJUnitRunner.class)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class UserJdbcDaoTest {

    private static final String PASSWORD = "Password";
    private static final String EMAIL = "Email@email.com";
    private static final long ID = 123;

    @Autowired
    private DataSource ds;

    @Autowired
    @InjectMocks
    private UserJdbcDao userDao;

    @Mock
    private JdbcTemplate jdbcTemplate;

    private SimpleJdbcInsert jdbcInsert;


    @Before
    public void setUp() {
        jdbcTemplate = new JdbcTemplate(ds);
        JdbcTestUtils.deleteFromTables(jdbcTemplate, users.name());
        jdbcInsert = new SimpleJdbcInsert(ds)
                .withTableName(users.name())
                .usingGeneratedKeyColumns(user_id.name());
        MockitoAnnotations.initMocks(this);
        //MockitoAnnotations.initMocks(JdbcTemplate.class);
        MockitoAnnotations.initMocks(UserJdbcDao.class);
    }

    //TODO No puedo usar métodos auxiliares de la clase que quiero probar, solo los métodos que quiero probar

    @Test
    public void testCreateUser_returnsNewUser() {
        // SETUP
        // EJERCITAR
        final Either<User, Validation> either = userDao.create( // todo creo que está mal, porque estoy testando la BD tambien
                new User.Builder()  // todo tendría que ser con mockito when(simpleInsert).then(123)
                .withEmail(EMAIL)
                .withPasswd(PASSWORD) // todo tengo que poner TODOS los campos?
        ); // todo asumo que Either anda bien?
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
        User mockedUser = Mockito.mock(User.class);
        List<User> list = new ArrayList<>();
        list.add(mockedUser);
        Mockito.when(mockedUser.getUser_id()).thenReturn(ID);
        Mockito.when(mockedUser.getEmail()).thenReturn(EMAIL);
        Mockito.when(mockedUser.getPasswd()).thenReturn(PASSWORD);
        Mockito.when(jdbcTemplate
                .query(any(String.class), any(RowMapper.class), any(Object.class))).thenReturn(list);
        // EXERCISE
        User user = userDao.getById(ID).getValue();
        // ASSERT
        assertNotNull(user);
        assertEquals(ID, user.getUser_id());
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
        User mockedUser = Mockito.mock(User.class);
        List<User> list = new ArrayList<>();
        list.add(mockedUser);
        Mockito.when(mockedUser.getUser_id()).thenReturn(ID);
        Mockito.when(mockedUser.getEmail()).thenReturn(EMAIL);
        Mockito.when(mockedUser.getPasswd()).thenReturn(PASSWORD);
        Mockito.when(jdbcTemplate
                .query(any(String.class), any(RowMapper.class), any(Object.class))).thenReturn(list);
        // EXERCISE
        User user = userDao.findByMail(EMAIL).getValue();
        // ASSERT
        assertNotNull(user);
        assertEquals(ID, user.getUser_id());
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
    public void testFindByMail_returnsError2() {
        // Testeamos que retorne error si hay más de uno
        // SETUP: Metemos 2 en la "bd"
        User mockedUser1 = Mockito.mock(User.class);
        User mockedUser2 = Mockito.mock(User.class);
        List<User> list = new ArrayList<>();
        list.add(mockedUser1);
        list.add(mockedUser2);
        Mockito.when(mockedUser1.getUser_id()).thenReturn(ID);
        Mockito.when(mockedUser1.getEmail()).thenReturn(EMAIL);
        Mockito.when(mockedUser1.getPasswd()).thenReturn(PASSWORD);
        Mockito.when(mockedUser2.getUser_id()).thenReturn(ID+1);
        Mockito.when(mockedUser2.getEmail()).thenReturn(EMAIL);
        Mockito.when(mockedUser2.getPasswd()).thenReturn(PASSWORD);
        Mockito.when(jdbcTemplate
                .query(any(String.class), any(RowMapper.class), any(Object.class))).thenReturn(list);
        // EXERCISE
        Validation validation = userDao.findByMail(EMAIL).getAlternative();
        // ASSERT
        assertNotNull(validation);
        assertEquals(validation.getEc(), DATABASE_ERROR);
    }
}
