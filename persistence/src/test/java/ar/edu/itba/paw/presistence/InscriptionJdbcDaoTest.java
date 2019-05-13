package ar.edu.itba.paw.presistence;

import ar.edu.itba.paw.interfaces.daos.ChangaDao;
import ar.edu.itba.paw.interfaces.daos.UserDao;
import ar.edu.itba.paw.interfaces.util.Validation;
import ar.edu.itba.paw.models.*;
import ar.edu.itba.paw.persistence.InscriptionJdbcDao;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.jdbc.JdbcTestUtils;

import javax.sql.DataSource;
import java.util.*;

import static ar.edu.itba.paw.constants.DBChangaFields.changa_id;
import static ar.edu.itba.paw.constants.DBInscriptionFields.*;
import static ar.edu.itba.paw.constants.DBTableName.*;
import static ar.edu.itba.paw.constants.DBUserFields.email;
import static ar.edu.itba.paw.constants.DBUserFields.passwd;
import static ar.edu.itba.paw.constants.DBUserFields.user_id;
import static ar.edu.itba.paw.interfaces.util.Validation.ErrorCodes.*;
import static ar.edu.itba.paw.models.InscriptionState.accepted;
import static ar.edu.itba.paw.models.InscriptionState.optout;
import static ar.edu.itba.paw.models.InscriptionState.requested;
import static org.junit.Assert.*;



@Sql("classpath:sql/a_create_tables.sql")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Rollback
public class InscriptionJdbcDaoTest {
    private static final String PASSWORD = "password";
    private static final String USER1_EMAIL = "email1@email.com";
    private static final String USER2_EMAIL = "email2@email.com";
    private static final String USER3_EMAIL = "email3@email.com";
    private static final long USER1_ID = 111;
    private static final long CHANGA_ID = 321;

    @Autowired
    private DataSource ds;
    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert jdbcInsertUserInscribedRow;
    private SimpleJdbcInsert jdbcInsertUserRow;
    private SimpleJdbcInsert jdbcInsertChangaRow;

    @Mock
    private ChangaDao changaDao;

    @Mock
    private UserDao userDao;

    @Autowired
    @InjectMocks
    private InscriptionJdbcDao inscriptionDao;



    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        jdbcTemplate = new JdbcTemplate(ds);
        JdbcTestUtils.deleteFromTables(jdbcTemplate, user_inscribed.name());
        jdbcInsertUserInscribedRow = new SimpleJdbcInsert(ds)
                .withTableName(user_inscribed.name())
                .usingColumns(user_id.name(), changa_id.name(), state.name());
        jdbcInsertUserRow = new SimpleJdbcInsert(ds)
                .withTableName(users.name())
                .usingGeneratedKeyColumns(user_id.name());
        jdbcInsertChangaRow = new SimpleJdbcInsert(ds)
                .withTableName(changas.name())
                .usingGeneratedKeyColumns(changa_id.name());
    }


    @Test
    public void testGetInscription_returnsInscription() {
        //SETUP
        //Make sure the user owner of the changa is in the DB
        Number userOwnerId =  addUserToDatabase(USER1_EMAIL, PASSWORD);

        //Make sure the user to be inscribed is in the DB
        Number userInscribedId =  addUserToDatabase(USER2_EMAIL, PASSWORD);

        //Make sure the changa is in the DB
        Number changaId =  addChangaToDatabase(userOwnerId.longValue());

        // Make sure the inscription is in the DB
        addInscriptionToDataBase(userInscribedId.longValue(), changaId.longValue());

        // EXERCISE
        Either<Inscription, Validation> inscriptionEither = inscriptionDao.getInscription(userInscribedId.longValue(), changaId.longValue());

        // ASSERT
        //assertNotNull(inscriptionEither);
        //assertTrue(inscriptionEither.isValuePresent());
        assertEquals(userInscribedId.longValue(), inscriptionEither.getValue().getUser_id());
        assertEquals(changaId.longValue(), inscriptionEither.getValue().getChanga_id());
    }



    @Test
    public void testGetInscription_returnsUserNotInscribed() {
        //Make sure the user owner of the changa is in the DB
        Number userOwnerId =  addUserToDatabase(USER1_EMAIL, PASSWORD);

        //Make sure the changa is in the DB
        Number changaId =  addChangaToDatabase(userOwnerId.longValue());

        //Create a user who is for sure not inscribed in the changa previously created
        Number userNotInscribed = addUserToDatabase(USER2_EMAIL, PASSWORD);

        //EXERCISE
        Either<Inscription, Validation> inscriptionEither = inscriptionDao.getInscription(userNotInscribed.longValue(), changaId.longValue());

        // ASSERT
        //assertNotNull(inscriptionEither);
        assertNotNull(inscriptionEither.getAlternative());
        assertEquals(USER_NOT_INSCRIBED, inscriptionEither.getAlternative().getEc());
    }


    @Test
    public void testHasInscribedUsers_returnsTrue() {
        //SETUP
        //Make sure the user owner of the changa is in the DB
        Number userOwnerId =  addUserToDatabase(USER1_EMAIL, PASSWORD);

        //Make sure the user inscribed is in the DB
        Number userInscribedId =  addUserToDatabase(USER2_EMAIL, PASSWORD);

        //Make sure the changa is in the DB
        Number changaId =  addChangaToDatabase(userOwnerId.longValue());

        // Make sure the inscription is in the DB
        addInscriptionToDataBase(userInscribedId.longValue(), changaId.longValue());

        //EXERCISE
        Boolean hasInscribedUsers = inscriptionDao.hasInscribedUsers(changaId.longValue());

        //ASSERT
        assertTrue(hasInscribedUsers);
    }

    @Test
    public void testHasInscribedUsers_returnsFalse() {
        //SETUP
        //Make sure the user owner of the changa is in the DB
        Number userOwnerId =  addUserToDatabase(USER1_EMAIL, PASSWORD);

        //Make sure the changa is in the DB with no inscription made
        Number changaId =  addChangaToDatabase(userOwnerId.longValue());

        //EXERCISE
        Boolean hasInscribedUsers = inscriptionDao.hasInscribedUsers(changaId.longValue());

        //ASSERT
        assertFalse(hasInscribedUsers);
    }

    @Test
    public void testIsUserInscribedInChanga_returnsTrue() {
        //SETUP
        //Make sure the user owner of the changa is in the DB
        Number userOwnerId =  addUserToDatabase(USER1_EMAIL, PASSWORD);

        //Make sure the user inscribed is in the DB
        Number userInscribedId =  addUserToDatabase(USER2_EMAIL, PASSWORD);

        //Make sure the changa is in the DB
        Number changaId =  addChangaToDatabase(userOwnerId.longValue());

        // Make sure the inscription is in the DB
        addInscriptionToDataBase(userInscribedId.longValue(), changaId.longValue());

        //EXERCISE
        Either<Boolean, Validation> eitherIsUserInscribedInChanga = inscriptionDao.isUserInscribedInChanga(userInscribedId.longValue(), changaId.longValue());

        //ASSERT
        //assertNotNull(eitherIsUserInscribedInChanga);
        //assertTrue(eitherIsUserInscribedInChanga.isValuePresent());
        assertTrue(eitherIsUserInscribedInChanga.getValue());
    }

    @Test
    public void testIsUserInscribedInChanga_returnsFalseBecauseUserWasNeverInscribed() {
        //SETUP
        //Make sure the user owner of the changa is in the DB
        Number userOwnerId =  addUserToDatabase(USER1_EMAIL, PASSWORD);

        //Make sure the changa is in the DB
        Number changaId =  addChangaToDatabase(userOwnerId.longValue());

        //Make sure another user who is not inscribed in the changa is in the DB
        Number userNotInscribed =  addUserToDatabase(USER2_EMAIL, PASSWORD);

       // when(inscriptionDao.getInscription(userNotInscribed.longValue(), changaId.longValue())).thenReturn(Either.alternative(USER_NOT_INSCRIBED));

        //EXERCISE
        Either<Boolean, Validation> eitherIsUserInscribedInChanga = inscriptionDao.isUserInscribedInChanga(userNotInscribed.longValue(), changaId.longValue());

        //ASSERT
        //assertNotNull(eitherIsUserInscribedInChanga);
        assertFalse(eitherIsUserInscribedInChanga.getValue());
    }

    @Test
    public void testIsUserInscribedInChanga_returnsFalseBecauseUserOptedOut() {
        //SETUP
        //Make sure the user owner of the changa is in the DB
        Number userOwnerId =  addUserToDatabase(USER1_EMAIL, PASSWORD);

        //Make sure the changa is in the DB
        Number changaId =  addChangaToDatabase(userOwnerId.longValue());

        //Make sure the user who will opt out is in the DB
        Number userWhoOptedOutId =  addUserToDatabase(USER2_EMAIL, PASSWORD);

        // Make sure the inscription is in the DB with the state in optout
        Map<String, Object> userInscribedRow = new HashMap<>();
        userInscribedRow.put(user_id.name(), userWhoOptedOutId);
        userInscribedRow.put(changa_id.name(), changaId);
        userInscribedRow.put(state.name(), optout.toString());
        jdbcInsertUserInscribedRow.execute(userInscribedRow);

        //EXERCISE
        Either<Boolean, Validation> eitherIsUserInscribedInChanga = inscriptionDao.isUserInscribedInChanga(userWhoOptedOutId.longValue(), changaId.longValue());

        System.out.println(eitherIsUserInscribedInChanga.getValue());
        //ASSERT
        //assertNotNull(eitherIsUserInscribedInChanga);
        assertFalse(eitherIsUserInscribedInChanga.getValue());
    }


    @Test
    public void testGetUserInscriptions_returnsUserChangasInscriptions() {
        //SETUP
        //Create changas
        Number user1OwnerId =  addUserToDatabase(USER1_EMAIL, PASSWORD);
        Number changa1Id =  addChangaToDatabase(user1OwnerId.longValue());
        Changa changa1 = new Changa(changa1Id.longValue(), new Changa.Builder().withUserId(user1OwnerId.longValue()));

        Number user2OwnerId =  addUserToDatabase(USER2_EMAIL, PASSWORD);
        Number changa2Id =  addChangaToDatabase(user2OwnerId.longValue());
        Changa changa2 = new Changa(changa2Id.longValue(), new Changa.Builder().withUserId(user2OwnerId.longValue()));
        Number changa3Id =  addChangaToDatabase(user2OwnerId.longValue());
        Changa changa3 = new Changa(changa3Id.longValue(), new Changa.Builder().withUserId(user2OwnerId.longValue()));

        //Create user who will be inscribed to previous changas
        Number userInscribedId =  addUserToDatabase(USER3_EMAIL, PASSWORD);

        // Inscribe user into changa1, changa2 and changa 3
        addInscriptionToDataBase(userInscribedId.longValue(), changa1Id.longValue());
        Inscription inscripiton1 = new Inscription.Builder().withUserId(userInscribedId.longValue()).withChangaId(changa1Id.longValue()).withState(requested).build();
        addInscriptionToDataBase(userInscribedId.longValue(), changa2Id.longValue());
        Inscription inscripiton2 = new Inscription.Builder().withUserId(userInscribedId.longValue()).withChangaId(changa2Id.longValue()).withState(requested).build();
        addInscriptionToDataBase(userInscribedId.longValue(), changa3Id.longValue());
        Inscription inscripiton3 = new Inscription.Builder().withUserId(userInscribedId.longValue()).withChangaId(changa3Id.longValue()).withState(requested).build();

        //Expected userInscriptions
        List<Pair<Changa, Inscription>> expectedUserInscriptions = new LinkedList<>();
        expectedUserInscriptions.add(Pair.buildPair(changa1, inscripiton1));
        expectedUserInscriptions.add(Pair.buildPair(changa2, inscripiton2));
        expectedUserInscriptions.add(Pair.buildPair(changa3, inscripiton3));

        //Mock changaDao
        Mockito.when(changaDao.getById(changa1Id.longValue())).thenReturn(Either.value(changa1));
        Mockito.when(changaDao.getById(changa2Id.longValue())).thenReturn(Either.value(changa2));
        Mockito.when(changaDao.getById(changa3Id.longValue())).thenReturn(Either.value(changa3));

        //EXERCISE
        Either<List<Pair<Changa, Inscription>>, Validation> userInscriptions = inscriptionDao.getUserInscriptions(userInscribedId.longValue());

        //ASSERT
        assertEquals(expectedUserInscriptions.size(), userInscriptions.getValue().size());
        assertTrue(userInscriptions.getValue().contains(expectedUserInscriptions.get(0)));
        assertTrue(userInscriptions.getValue().contains(expectedUserInscriptions.get(1)));

    }

    @Test
    public void testGetUserInscriptions_returnsEmptyList() {
        //SETUP
        //Create user
        Number userId =  addUserToDatabase(USER1_EMAIL, PASSWORD);

        //EXERCISE
        Either<List<Pair<Changa, Inscription>>, Validation> userInscriptions = inscriptionDao.getUserInscriptions(userId.longValue());

        //ASSERT
        assertEquals(0, userInscriptions.getValue().size());
    }

//    @Test
//    public void testGetUserInscriptions_returnsError() {
//        //SETUP
//        //Create changa
//        Number user1OwnerId =  addUserToDatabase(USER1_EMAIL, PASSWORD);
//        Number changa1Id =  addChangaToDatabase(user1OwnerId.longValue());
//        Changa changa1 = new Changa(changa1Id.longValue(), new Changa.Builder().withUserId(user1OwnerId.longValue()));
//
//        //Create user who will be inscribed to previous changa
//        Number userInscribedId =  addUserToDatabase(USER2_EMAIL, PASSWORD);
//
//        // Inscribe user into changa1
//        addInscriptionToDataBase(userInscribedId.longValue(), changa1Id.longValue());
//        Inscription inscripiton1 = new Inscription.Builder().withUserId(userInscribedId.longValue()).withChangaId(changa1Id.longValue()).withState(optout).build();
//
//        //Mock changaDao
//        Mockito.when(changaDao.getById(changa1Id.longValue())).thenReturn(Either.alternative(new Validation(NO_SUCH_CHANGA)));
//
//        //EXERCISE
//        Either<List<Pair<Changa, Inscription>>, Validation> userInscriptions = inscriptionDao.getUserInscriptions(userInscribedId.longValue());
//
//        System.out.println(userInscriptions.getAlternative().getEc());
//        //ASSERT
//        assertEquals(NO_SUCH_CHANGA, userInscriptions.getAlternative().getEc());
//
//    }


    @Test
    public void testGetInscribedUsers_returnsChangaInscribedUsers() {
        //SETUP
        //Create changa
        Number userOwnerId =  addUserToDatabase(USER1_EMAIL, PASSWORD);
        Number changaId =  addChangaToDatabase(userOwnerId.longValue());

        //Create users to inscribe in changa
        Number user2Id =  addUserToDatabase(USER2_EMAIL, PASSWORD);
        User user2 = new User(user2Id.longValue(), new User.Builder().withEmail(USER2_EMAIL).withPasswd(PASSWORD));
        Number user3Id =  addUserToDatabase(USER3_EMAIL, PASSWORD);
        User user3 = new User(user3Id.longValue(), new User.Builder().withEmail(USER3_EMAIL).withPasswd(PASSWORD));

        // Inscribe users into changa
        addInscriptionToDataBase(user2Id.longValue(), changaId.longValue());
        Inscription inscripiton2 = new Inscription.Builder().withUserId(user2Id.longValue()).withChangaId(changaId.longValue()).build();
        addInscriptionToDataBase(user3Id.longValue(), changaId.longValue());
        Inscription inscripiton3 = new Inscription.Builder().withUserId(user3Id.longValue()).withChangaId(changaId.longValue()).build();


        //Expected user inscriptions
        List<Pair<User, Inscription>> expectedUserInscriptions = new LinkedList<>();
        expectedUserInscriptions.add(Pair.buildPair(user2, inscripiton2));
        expectedUserInscriptions.add(Pair.buildPair(user3, inscripiton3));

        //Mock userDao
        Mockito.when(userDao.getById(user2Id.longValue())).thenReturn(Either.value(user2));
        Mockito.when(userDao.getById(user3Id.longValue())).thenReturn(Either.value(user3));

        //EXERCISE
        Either<List<Pair<User, Inscription>>, Validation> userInscriptions = inscriptionDao.getInscribedUsers(changaId.longValue());

        //ASSERT
        assertEquals(expectedUserInscriptions.size(), userInscriptions.getValue().size());
        assertTrue(userInscriptions.getValue().contains(expectedUserInscriptions.get(0)));
        assertTrue(userInscriptions.getValue().contains(expectedUserInscriptions.get(1)));
        assertTrue(userInscriptions.getValue().contains(expectedUserInscriptions.get(1)));

    }

    @Test
    public void testGetInscribedUsers_returnsEmptyList() {
        //SETUP
        //Create changa
        Number userOwnerId =  addUserToDatabase(USER1_EMAIL, PASSWORD);
        Number changaId =  addChangaToDatabase(userOwnerId.longValue());

        //EXERCISE
        Either<List<Pair<User, Inscription>>, Validation> userInscriptions = inscriptionDao.getInscribedUsers(changaId.longValue());

        //ASSERT
        assertEquals(0, userInscriptions.getValue().size());
    }



    @Test
    public void testChangeUserStateInChanga_returnsValidationOk() {
        //SETUP
        //Make sure the user owner of the changa is in the DB
        Number userOwnerId =  addUserToDatabase(USER1_EMAIL, PASSWORD);

        //Make sure the user to be inscribed is in the DB
        Number userInscribedId =  addUserToDatabase(USER2_EMAIL, PASSWORD);

        //Make sure the changa is in the DB
        Number changaId =  addChangaToDatabase(userOwnerId.longValue());

        // Make sure the inscription is in the DB with state in default value (request)
        addInscriptionToDataBase(userInscribedId.longValue(), changaId.longValue());
        Inscription inscripiton = new Inscription.Builder().withUserId(userInscribedId.longValue()).withChangaId(changaId.longValue()).build();

        //EXERCISE
        Validation validation = inscriptionDao.changeUserStateInChanga(inscripiton, accepted);

        //ASSERT
        assertEquals(OK, validation.getEc());
        //se que esto no se puede hacer, pero me gustaria hacerlo para verificar q se haya updateado bien y no simplemente el return OK.
        //assertEquals(accepted, inscriptionDao.getInscription(userInscribedId.longValue(), changaId.longValue()).getValue().getState());
    }

    @Test
    public void testChangeUserStateInChanga_returnsValidationError() {
        //SETUP
        //Fake inscription
        Inscription inscripiton = new Inscription.Builder().withUserId(USER1_ID).withChangaId(CHANGA_ID).build();

        //EXERCISE
        Validation validation = inscriptionDao.changeUserStateInChanga(inscripiton, accepted);

        //ASSERT
        assertEquals(DATABASE_ERROR, validation.getEc());
    }

    @Test
    public void testChangeUserStateInChangaWrapper_returnsValidationOk() {
        //SETUP
        //Make sure the user owner of the changa is in the DB
        Number userOwnerId =  addUserToDatabase(USER1_EMAIL, PASSWORD);

        //Make sure the changa is in the DB
        Number changaId =  addChangaToDatabase(userOwnerId.longValue());

        //Make sure the user to be inscribed is in the DB
        Number userInscribedId =  addUserToDatabase(USER2_EMAIL, PASSWORD);

        // Make sure the inscription is in the DB with state in default value (request)
        addInscriptionToDataBase(userInscribedId.longValue(), changaId.longValue());
        Inscription inscripiton = new Inscription.Builder().withUserId(userInscribedId.longValue()).withChangaId(changaId.longValue()).build();

        //EXERCISE
        Validation validation = inscriptionDao.changeUserStateInChanga(userInscribedId.longValue(), changaId.longValue(), accepted);

        //ASSERT
        assertEquals(OK, validation.getEc());
        //se que esto no se puede hacer, pero me gustaria hacerlo para verificar q se haya updateado bien y no simplemente el return OK.
        //assertEquals(accepted, inscriptionDao.getInscription(userInscribedId.longValue(), changaId.longValue()).getValue().getState());
    }

    @Test
    public void testChangeUserStateInChanga_returnsDataBaseErrorBecauseOfInexistentInscription() {
        //SETUP
        //Fake inscription
        Inscription inscripiton = new Inscription.Builder().withUserId(USER1_ID).withChangaId(CHANGA_ID).build();

        //EXERCISE
        Validation validation = inscriptionDao.changeUserStateInChanga(inscripiton, accepted);

        //ASSERT
        assertEquals(DATABASE_ERROR, validation.getEc());
    }

    //pasar este test a service
    @Test
    public void testInscribeInChanga_returnsCorrectValidation() {
        //Make sure the user owner of the changa is in the DB
        Number userOwnerId =  addUserToDatabase(USER1_EMAIL, PASSWORD);

        //Make sure the changa is in the DB
        Number changaId =  addChangaToDatabase(userOwnerId.longValue());
        Changa changa = new Changa(changaId.longValue(), new Changa.Builder().withUserId(userOwnerId.longValue()));

        //Make sure the user to be inscribed is in the DB
        Number userInscribedId =  addUserToDatabase(USER2_EMAIL, PASSWORD);

        //EXERCISE
        Validation validation = inscriptionDao.inscribeInChanga(userInscribedId.longValue(), changaId.longValue());

        //ASSERT
        assertEquals(OK, validation.getEc());
        assertEquals(1, JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, user_inscribed.name(), String.format("%s = %d", user_id, userInscribedId.longValue())));
    }

    @Test
    public void testInscribeInChanga_returnsError() {
        //EXERCISE
        Validation validation = inscriptionDao.inscribeInChanga(USER1_ID, CHANGA_ID);
        //ASSERT
        assertEquals(DATABASE_ERROR, validation.getEc());
    }


    private Number addUserToDatabase(String mail, String password) {
        Map<String, Object> userOwnerRow = new HashMap<>();
        userOwnerRow.put(email.name(), mail);
        userOwnerRow.put(passwd.name(), password);
        return jdbcInsertUserRow.executeAndReturnKey(userOwnerRow);
    }

    private Number addChangaToDatabase(long userOwnerId) {
        Map<String, Object> changaRow = new HashMap<>();
        changaRow.put(user_id.name(), userOwnerId);
        return  jdbcInsertChangaRow.executeAndReturnKey(changaRow);
    }

    public void addInscriptionToDataBase(long userInscribedId, long changaId) {
        Map<String, Object> userInscribedRow = new HashMap<>();
        userInscribedRow.put(user_id.name(), userInscribedId);
        userInscribedRow.put(changa_id.name(), changaId);
        userInscribedRow.put(state.name(), requested.toString());
        jdbcInsertUserInscribedRow.execute(userInscribedRow);
    }

}
