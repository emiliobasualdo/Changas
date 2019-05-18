package ar.edu.itba.paw.presistence;

import ar.edu.itba.paw.interfaces.util.Validation;
import ar.edu.itba.paw.models.Changa;
import ar.edu.itba.paw.models.ChangaState;
import ar.edu.itba.paw.models.Either;
import ar.edu.itba.paw.persistence.ChangaJdbcDao;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.jdbc.JdbcTestUtils;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.*;

import static ar.edu.itba.paw.constants.DBChangaFields.*;
import static ar.edu.itba.paw.constants.DBTableName.changas;
import static ar.edu.itba.paw.constants.DBTableName.users;
import static ar.edu.itba.paw.constants.DBUserFields.email;
import static ar.edu.itba.paw.constants.DBUserFields.passwd;
import static ar.edu.itba.paw.interfaces.util.Validation.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.RETURNS_DEEP_STUBS;
import static org.mockito.Mockito.when;

@Sql("classpath:sql/a_create_tables.sql")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Rollback
public class ChangaJdbcDaoTest {

    private static final long USER_ID = 123;
    private static final long CHANGA_ID = 321;
    private static final double PRICE = 3.2;
    private static final int NUMBER = 5;
    private static final String EMAIL = "hola@tv.com";
    private static final String PASSWD = "misuperpass";
    private static final String TITLE = "Es un título";
    private static final String TITLE2 = "Es un título 2";
    private static final String DESCR = "Es una descripción";
    private static final String DESCR2 = "Es una descripción 2";
    private static final String NAN = "nanan";

    @Autowired
    private DataSource ds;

    private JdbcTemplate chJdbcTemplate;
    private SimpleJdbcInsert chJdbcInsert;
    private SimpleJdbcInsert usJdbcInsert;

    @Autowired
    private ChangaJdbcDao changaDao;

    @Before
    public void setUp() {
        chJdbcTemplate = new JdbcTemplate(ds);
        JdbcTestUtils.deleteFromTables(chJdbcTemplate, changas.name());
        chJdbcInsert = new SimpleJdbcInsert(ds)
                .withTableName(changas.name())
                .usingGeneratedKeyColumns(changa_id.name());
        usJdbcInsert = new SimpleJdbcInsert(ds)
                .withTableName(users.name())
                .usingGeneratedKeyColumns(user_id.name());
    }

    //TODO No puedo usar métodos auxiliares de la clase que quiero probar, solo los métodos que quiero probar

    @Test
    public void testGetById_returnsChanga() {
        // SETUP:
        // preparamos el usuario
        Map<String, Object> userRow = new HashMap<>();
        userRow.put(email.name(), EMAIL);
        userRow.put(passwd.name(), PASSWD);
        Number userId = usJdbcInsert.executeAndReturnKey(userRow);
        // preparamos la changa
        Map<String, Object> changaRow = new HashMap<>();
        changaRow.put(user_id.name(), userId);
        changaRow.put(state.name(), ChangaState.emitted);
        Number changaId = chJdbcInsert.executeAndReturnKey(changaRow);
        // EJERCITAR
        final Either<Changa, Validation> either = changaDao.getById(changaId.longValue());
        // ASSERT
        assertEquals(userId.longValue(), either.getValue().getUser_id());
        assertEquals(changaId.longValue(), either.getValue().getChanga_id());
        assertEquals(1, JdbcTestUtils.countRowsInTableWhere(chJdbcTemplate, changas.name(), String.format("%s = %d", changaId, changaId.longValue())));
    }

    @Test
    public void testGetById_returnsError() {
        // SETUP:
        // EJERCITAR
        final Either<Changa, Validation> either = changaDao.getById(CHANGA_ID);
        // ASSERT
        assertEquals(NO_SUCH_CHANGA, either.getAlternative());
        assertEquals(0, JdbcTestUtils.countRowsInTableWhere(chJdbcTemplate, changas.name(), String.format("%s = %d", changa_id, CHANGA_ID)));
    }

    @Test
    public void testCreate_returnsChanga() {
        // SETUP:
        Map<String, Object> userRow = new HashMap<>();
        userRow.put(email.name(), EMAIL);
        userRow.put(passwd.name(), PASSWD);
        Number userId = usJdbcInsert.executeAndReturnKey(userRow);
        Changa.Builder chBuilder = new Changa.Builder()
                .withUserId(userId.longValue());
        // EJERCITAR
        final Either<Changa, Validation> either = changaDao.create(chBuilder);
        // ASSERT
        assertEquals(userId.longValue(), either.getValue().getUser_id());
        assertEquals(ChangaState.emitted, either.getValue().getState());
        assertEquals(1, JdbcTestUtils.countRowsInTableWhere(chJdbcTemplate, changas.name(), String.format("%s = %d", changa_id, either.getValue().getChanga_id())));
    }

    @Test
    public void testCreate_returnsError() {
        // SETUP:
        Changa.Builder chBuilder = new Changa.Builder()
                .withUserId(USER_ID);
        // EJERCITAR
        final Either<Changa, Validation> either = changaDao.create(chBuilder);
        // ASSERT
        assertEquals(NO_SUCH_USER, either.getAlternative());
        assertEquals(0, JdbcTestUtils.countRowsInTable(chJdbcTemplate, changas.name()));
    }

    @Test
    public void testGetAll_returnsList() {
        // SETUP:
        // preparamos el usuario
        Map<String, Object> userRow = new HashMap<>();
        userRow.put(email.name(), EMAIL);
        userRow.put(passwd.name(), PASSWD);
        Number userId = usJdbcInsert.executeAndReturnKey(userRow);
        // preparamos 2 changas
        Map<String, Object> changaRow = new HashMap<>();
        changaRow.put(user_id.name(), userId);
        changaRow.put(state.name(), ChangaState.emitted);
        Number changaId1 = chJdbcInsert.executeAndReturnKey(changaRow);
        Number changaId2 = chJdbcInsert.executeAndReturnKey(changaRow);

        // EJERCITAR
        final Either<List<Changa>, Validation> either = changaDao.getAll(ChangaState.emitted,0);
        // ASSERT
        // asumimos que vuelven en el mismo orden
        assertEquals(changaId1.longValue(), either.getValue().get(0).getChanga_id());
        assertEquals(changaId2.longValue(), either.getValue().get(1).getChanga_id());
        assertEquals(2, either.getValue().size());
    }

    @Test
    public void testGetUserOwnedChangas_returnsList() {
        // SETUP:
        // preparamos el usuario
        Map<String, Object> userRow = new HashMap<>();
        userRow.put(email.name(), EMAIL);
        userRow.put(passwd.name(), PASSWD);
        // preparamos usuario 1
        Number userId1 = usJdbcInsert.executeAndReturnKey(userRow);
        // preparamos usuario 2
        userRow.put(email.name(), EMAIL+"a");
        Number userId2 = usJdbcInsert.executeAndReturnKey(userRow);
        // preparamos 2 changas para el 1
        Map<String, Object> changaRow = new HashMap<>();
        changaRow.put(user_id.name(), userId1);
        changaRow.put(state.name(), ChangaState.emitted);
        Number changaId1 = chJdbcInsert.executeAndReturnKey(changaRow);
        Number changaId2 = chJdbcInsert.executeAndReturnKey(changaRow);
        // preparamos 1 changa para el 2
        changaRow.put(user_id.name(), userId2);
        chJdbcInsert.executeAndReturnKey(changaRow);

        // EJERCITAR
        final Either<List<Changa>, Validation> either = changaDao.getUserOwnedChangas(userId1.longValue());
        // ASSERT
        // asumimos que vuelven en el mismo orden
        assertEquals(changaId1.longValue(), either.getValue().get(0).getChanga_id());
        assertEquals(changaId2.longValue(), either.getValue().get(1).getChanga_id());
        assertEquals(2, either.getValue().size());
    }

    @Test
    public void testUpdate_returnsChanga() {
        // aclaración: este es el test más feo de la historia
        // SETUP:
        // preparamos el usuario
        Map<String, Object> userRow = new HashMap<>();
        userRow.put(email.name(), EMAIL);
        userRow.put(passwd.name(), PASSWD);
        // preparamos usuario
        Number userId1 = usJdbcInsert.executeAndReturnKey(userRow);
        // preparamos una changa
        Map<String, Object> map = new HashMap<>();
        map.put(description.name(),DESCR);
        map.put(title.name(), TITLE);
        map.put(state.name(), ChangaState.emitted.toString());
        map.put(user_id.name(), userId1);
        map.put(neighborhood.name(), NAN);
        map.put(street.name(), NAN);
        map.put(number.name(), NUMBER);
        map.put(price.name(), PRICE);
        map.put(creation_date.name(), java.sql.Date.valueOf(LocalDateTime.now().toLocalDate()));
        Number changaId1 = chJdbcInsert.executeAndReturnKey(map);
        // preparamos un builder
        Changa.Builder changaBuilder = new Changa.Builder()
                // campos que cambio
                .withDescription(DESCR2)
                .withTitle(TITLE2)
                .withState(ChangaState.settled)
                // campos que no cambio
                .atAddress(NAN, NAN, NUMBER)
                .withPrice(PRICE);
        // EJERCITAR
        final Changa changa = changaDao.update(
                changaId1.longValue(),
                changaBuilder
        ).getValue();
        // ASSERT
        // aseguramos que estos cambiaron
        assertEquals(TITLE2, changa.getTitle());
        assertEquals(ChangaState.settled, changa.getState());
        // y aseguramos que estos no cambiaron
        assertEquals(map.get(user_id.name()), (int)changa.getUser_id());
        assertEquals(map.get(price.name()), changa.getPrice());
    }

    @Test
    public void testUpdate_returnsError() {
        // SETUP:
        // preparamos un builder
        Changa.Builder changaBuilder = new Changa.Builder()
                // campos que cambio
                .withDescription(DESCR2)
                .withTitle(TITLE2)
                .withState(ChangaState.settled)
                // campos que no cambio
                .atAddress(NAN, NAN, NUMBER)
                .withPrice(PRICE);
        // EJERCITAR
        final Validation val = changaDao.update(
                CHANGA_ID,
                changaBuilder
        ).getAlternative();
        // ASSERT
        assertEquals(NO_SUCH_CHANGA, val);
    }

    @Test
    public void testChangeChangaState_returnsChanga() {
        // aclaración: este es el test más feo de la historia
        // SETUP:
        // preparamos el usuario
        Map<String, Object> userRow = new HashMap<>();
        userRow.put(email.name(), EMAIL);
        userRow.put(passwd.name(), PASSWD);
        // preparamos usuario
        Number userId1 = usJdbcInsert.executeAndReturnKey(userRow);
        // Preparamos una changa
        Map<String, Object> map = new HashMap<>();
        map.put(description.name(),DESCR);
        map.put(title.name(), TITLE);
        map.put(state.name(), ChangaState.emitted.toString());
        map.put(user_id.name(), userId1);
        map.put(neighborhood.name(), NAN);
        map.put(street.name(), NAN);
        map.put(number.name(), NUMBER);
        map.put(price.name(), PRICE);
        map.put(creation_date.name(), java.sql.Date.valueOf(LocalDateTime.now().toLocalDate()));
        Number changaId1 = chJdbcInsert.executeAndReturnKey(map);
        // EJERCITAR
        final Changa changa = changaDao.changeChangaState(
                changaId1.longValue(),
                ChangaState.closed
        ).getValue();
        // ASSERT
        // aseguramos que estos cambiaron
        assertEquals(ChangaState.closed, changa.getState());
        // ASSERT
        // aseguramos que estos no cambiaron
        assertEquals(map.get(title.name()), changa.getTitle());
        assertEquals(map.get(description.name()), changa.getDescription());
    }

    @Test
    public void testChangeChangaState_returnsError() {
        // aclaración: este es el test más feo de la historia
        // SETUP:
        // EJERCITAR
        final Validation val = changaDao.changeChangaState(
                CHANGA_ID,
                ChangaState.closed
        ).getAlternative();
        // ASSERT
        assertEquals(DATABASE_ERROR, val);
    }

    private Connection conn;

    @Test
    public void testGetECFPageCount_resturnCorrectInt() throws SQLException {
        List<String> a = new ArrayList<>();
        a.add("hola");
        String[] arr = a.toArray(new String[]{});
        assertEquals(1,  arr.length);
        assertEquals("hola",  arr[0]);

        // EJERCITAR
        Either<Integer, Validation> resp = changaDao.getFilteredPageCount(ChangaState.emitted,"", "","");
        // ASSERT
        assertEquals(1,  (int) resp.getValue());
        //assertEquals("emitted",  ChangaState.emitted.toString());
        //assertEquals("emitted",  ChangaState.emitted.name());
    }
}

