package ar.edu.itba.paw.service;

import ar.edu.itba.paw.interfaces.daos.ChangaDao;
import ar.edu.itba.paw.interfaces.daos.InscriptionDao;
import ar.edu.itba.paw.interfaces.daos.UserDao;
import ar.edu.itba.paw.interfaces.services.AuthenticationService;
import ar.edu.itba.paw.interfaces.util.Validation;
import ar.edu.itba.paw.models.Changa;
import ar.edu.itba.paw.models.Either;
import ar.edu.itba.paw.models.Inscription;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.services.InscriptionServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import static ar.edu.itba.paw.interfaces.util.Validation.OK;
import static ar.edu.itba.paw.models.ChangaState.emitted;
import static org.mockito.Mockito.when;
import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class InscriptionServiceImplTest {

    private static final String PASSWORD = "password";
    private static final String USER1_EMAIL = "email1@email.com";
    private static final String USER2_EMAIL = "email2@email.com";
    private static final String USER3_EMAIL = "email3@email.com";
    private static final long USER1_ID = 111;
    private static final long USER2_ID = 222;
    private static final long CHANGA_ID = 321;

    @Mock
    private AuthenticationService authenticationService;

    @Mock
    private ChangaDao changaDao;

    @Mock
    private UserDao userDao;

    @Mock
    private InscriptionDao inscriptionDao;


    @InjectMocks
    private InscriptionServiceImpl inscriptionService;

    private User mockedUser;
    private Changa mockedChanga;
    private Inscription mockedInscription;

    @Before
    public void setUp() {
        mockedUser = Mockito.mock(User.class);
        mockedChanga = Mockito.mock(Changa.class);
        mockedInscription = Mockito.mock(Inscription.class);
    }


    @Test
    public void testInscribeInChanga_returnsCorrectValidation() {
        //SETUP
        //Preparamos a la falsa changa
        when(mockedChanga.getUser_id()).thenReturn(USER1_ID);
        when(mockedChanga.getState()).thenReturn(emitted);
        when(changaDao.getById(CHANGA_ID)).thenReturn(Either.value(mockedChanga));
        //Preparamos al falso usuario que se inscribirá en la changa
        when(mockedUser.isEnabled()).thenReturn(true);
        when(userDao.getById(USER2_ID)).thenReturn(Either.value(mockedUser));
        //Preparamos a la falsa sesión del usuario loggeado
        when(authenticationService.getLoggedUser().isPresent()).thenReturn(true);
        when(authenticationService.getLoggedUser().get().getUser_id()).thenReturn(USER2_ID);
        //Preparamos al either de la inscripción con validation error con la que verifica que el usuario no esté inscripto ya

        when(inscriptionDao.getInscription(USER2_ID, CHANGA_ID)).thenReturn(Either.alternative(Validation.USER_NOT_INSCRIBED));

        //EXERCISE
        Validation validation = inscriptionService.inscribeInChanga(USER2_ID, CHANGA_ID);

        //ASSERT
        assertEquals(OK, validation);
    }



//    @Test
//    public void testInscribeInChanga_returnsCorrectValidation() {
//        //SETUP
//
//
//        //Make sure the user owner of the changa is in the DB
//        Number userOwnerId =  addUserToDatabase(USER1_EMAIL, PASSWORD);
//
//        //Make sure the changa is in the DB
//        Number changaId =  addChangaToDatabase(userOwnerId.longValue());
//        Changa changa = new Changa(changaId.longValue(), new Changa.Builder().withUserId(userOwnerId.longValue()));
//
//        //Make sure the user to be inscribed is in the DB
//        Number userInscribedId =  addUserToDatabase(USER2_EMAIL, PASSWORD);
//
//        //Mock changaDao
//        Mockito.when(changaDao.getById(changaId.longValue())).thenReturn(Either.value(changa));
//
//        //EXERCISE
//        Validation validation = inscriptionDao.inscribeInChanga(userInscribedId.longValue(), changaId.longValue());
//
//        //ASSERT
//        assertEquals(OK, validation.getEc());
//        assertEquals(1, JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, user_inscribed.name(), String.format("%s = %d", user_id, userInscribedId.longValue())));
//    }
//
//    //pasar este test a service
//    @Test
//    public void testInscribeInChanga_returnsUserOwnsChanga() {
//        //Make sure the user owner of the changa is in the DB
//        Number userOwnerId =  addUserToDatabase(USER1_EMAIL, PASSWORD);
//
//        //Make sure the changa is in the DB
//        Number changaId =  addChangaToDatabase(userOwnerId.longValue());
//        Changa changa = new Changa(changaId.longValue(), new Changa.Builder().withUserId(userOwnerId.longValue()));
//
//        //Mock changaDao
//        Mockito.when(changaDao.getById(changaId.longValue())).thenReturn(Either.value(changa));
//
//        //EXERCISE
//        Validation validation = inscriptionDao.inscribeInChanga(userOwnerId.longValue(), changaId.longValue());
//
//        //ASSERT
//        assertEquals(USER_OWNS_THE_CHANGA, validation.getEc());
//    }
//
//    //pasar este test a service
//    @Test
//    public void testInscribeInChanga_returnsUserAlreadyInscribed() {
//        //Make sure the user owner of the changa is in the DB
//        Number userOwnerId =  addUserToDatabase(USER1_EMAIL, PASSWORD);
//
//        //Make sure the changa is in the DB
//        Number changaId =  addChangaToDatabase(userOwnerId.longValue());
//        Changa changa = new Changa(changaId.longValue(), new Changa.Builder().withUserId(userOwnerId.longValue()));
//
//        //Make sure the user inscribed is in the DB
//        Number userInscribedId =  addUserToDatabase(USER2_EMAIL, PASSWORD);
//
//        // Make sure the inscription is in the DB
//        addInscriptionToDataBase(userInscribedId.longValue(), changaId.longValue());
//
//        //Mock changaDao
//        Mockito.when(changaDao.getById(changaId.longValue())).thenReturn(Either.value(changa));
//
//        //EXERCISE
//        Validation validation = inscriptionDao.inscribeInChanga(userInscribedId.longValue(), changaId.longValue());
//
//        //ASSERT
//        assertEquals(USER_ALREADY_INSCRIBED, validation.getEc());
//    }
//
//    //pasar este test a service
//    @Test
//    public void testInscribeInChanga_returnsOkAfterChangingOptoutToRequested() {
//        //Make sure the user owner of the changa is in the DB
//        Number userOwnerId =  addUserToDatabase(USER1_EMAIL, PASSWORD);
//
//        //Make sure the changa is in the DB
//        Number changaId =  addChangaToDatabase(userOwnerId.longValue());
//        Changa changa = new Changa(changaId.longValue(), new Changa.Builder().withUserId(userOwnerId.longValue()));
//
//        //Make sure the user who opted out is in the DB
//        Number userWhoOptedOutId =  addUserToDatabase(USER2_EMAIL, PASSWORD);
//
//        // Make sure the inscription is in the DB with the state in opt out
//        Map<String, Object> userInscribedRow = new HashMap<>();
//        userInscribedRow.put(user_id.name(), userWhoOptedOutId);
//        userInscribedRow.put(changa_id.name(), changaId);
//        userInscribedRow.put(state.name(), optout.toString());
//        jdbcInsertUserInscribedRow.execute(userInscribedRow);
//
//        //Mock changaDao
//        Mockito.when(changaDao.getById(changaId.longValue())).thenReturn(Either.value(changa));
//
//        //EXERCISE
//        Validation validation = inscriptionDao.inscribeInChanga(userWhoOptedOutId.longValue(), changaId.longValue());
//
//        //ASSERT
//        assertEquals(OK, validation.getEc());
//
//    }
}
