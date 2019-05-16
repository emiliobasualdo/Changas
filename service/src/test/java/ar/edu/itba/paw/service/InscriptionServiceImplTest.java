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
import org.springframework.security.core.Authentication;

import java.util.Optional;

import static ar.edu.itba.paw.interfaces.util.Validation.OK;
import static ar.edu.itba.paw.interfaces.util.Validation.USER_ALREADY_INSCRIBED;
import static ar.edu.itba.paw.interfaces.util.Validation.USER_OWNS_THE_CHANGA;
import static ar.edu.itba.paw.models.ChangaState.emitted;
import static ar.edu.itba.paw.models.InscriptionState.*;
import static org.mockito.Mockito.when;
import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class InscriptionServiceImplTest {

    private static final String PASSWORD = "password";
    private static final String USER_OWNER_EMAIL = "email1@email.com";
    private static final String USER2_EMAIL = "email2@email.com";
    private static final String USER3_EMAIL = "email3@email.com";
    private static final long USER_OWNER_ID = 111;
    private static final long USER_INSCRIBED_ID = 222;
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
    private Authentication mockedAuthentication;

    @Before
    public void setUp() {
        mockedUser = Mockito.mock(User.class);
        mockedChanga = Mockito.mock(Changa.class);
        mockedInscription = Mockito.mock(Inscription.class);
        Authentication mockedAuthentication = Mockito.mock(Authentication.class);
    }


    @Test
    public void testInscribeInChanga_returnsCorrectValidation() {
        //SETUP
        //Preparamos a la falsa changa
        when(mockedChanga.getUser_id()).thenReturn(USER_OWNER_ID);
        when(mockedChanga.getState()).thenReturn(emitted);
        when(changaDao.getById(CHANGA_ID)).thenReturn(Either.value(mockedChanga));
        //Preparamos al falso usuario que se inscribirá en la changa
        when(mockedUser.isEnabled()).thenReturn(true);
        when(userDao.getById( USER_INSCRIBED_ID)).thenReturn(Either.value(mockedUser));
        //Preparamos a la falsa sesión del usuario loggeado
        when(authenticationService.getLoggedUser()).thenReturn(Optional.of(mockedUser));
        when(mockedUser.getUser_id()).thenReturn( USER_INSCRIBED_ID);
        //no se porque lo de abajo no me dejaba hacerlo. por algo de threads. pregutnar.
        //when(authenticationService.getLoggedUser().isPresent()).thenReturn(true);
        //when(authenticationService.getLoggedUser().get().getUser_id()).thenReturn(USER2_ID);
        //Preparamos al falseo either de la inscripción con validation error con la que verifica que el usuario no esté inscripto ya
        when(inscriptionDao.getInscription( USER_INSCRIBED_ID, CHANGA_ID)).thenReturn(Either.alternative(Validation.USER_NOT_INSCRIBED));
        //Preparamos a la falsa inscripcion del usuario en la changa
        when(inscriptionDao.inscribeInChanga( USER_INSCRIBED_ID, CHANGA_ID)).thenReturn(OK);

        //EXERCISE
        Validation validation = inscriptionService.inscribeInChanga( USER_INSCRIBED_ID, CHANGA_ID);

        //ASSERT
        assertEquals(OK, validation);
    }

        @Test
    public void testInscribeInChanga_returnsUserOwnsChanga() {
        //Preparamos a la falsa sesión del usuario loggeado. En este caso será el dueño de la changa
        when(authenticationService.getLoggedUser()).thenReturn(Optional.of(mockedUser));
        when(mockedUser.getUser_id()).thenReturn( USER_OWNER_ID);
        //Preparamos a la falsa changa de la cual el usuario loggeado es dueño
        when(mockedChanga.getUser_id()).thenReturn(USER_OWNER_ID);
        when(changaDao.getById(CHANGA_ID)).thenReturn(Either.value(mockedChanga));

        //EXERCISE
        Validation validation = inscriptionService.inscribeInChanga(USER_OWNER_ID, CHANGA_ID);

        //ASSERT
        assertEquals(USER_OWNS_THE_CHANGA, validation);
    }

    @Test
    public void testInscribeInChanga_returnsUserAlreadyInscribed() {
        //SETUP
        //Preparamos a la falsa sesión del usuario loggeado
        when(authenticationService.getLoggedUser()).thenReturn(Optional.of(mockedUser));
        when(mockedUser.getUser_id()).thenReturn( USER_INSCRIBED_ID);
        //Preparamos a la falsa changa
        when(mockedChanga.getUser_id()).thenReturn(USER_OWNER_ID);
        when(mockedChanga.getState()).thenReturn(emitted);
        when(changaDao.getById(CHANGA_ID)).thenReturn(Either.value(mockedChanga));
        //Preparamos al falso usuario que ya esta inscripto en la changa
        when(mockedUser.isEnabled()).thenReturn(true);
        when(userDao.getById( USER_INSCRIBED_ID)).thenReturn(Either.value(mockedUser));
        //Creamos la falsa inscriptcion del usuario que se volverá a inscribir
        when(inscriptionDao.getInscription( USER_INSCRIBED_ID, CHANGA_ID)).thenReturn(Either.value(mockedInscription));
        when(mockedInscription.getState()).thenReturn(requested);

        //EXERCISE
        Validation validation = inscriptionService.inscribeInChanga(USER_INSCRIBED_ID, CHANGA_ID);

        //ASSERT
        assertEquals(USER_ALREADY_INSCRIBED, validation);
    }


    @Test
    public void testInscribeInChanga_returnsOkAfterChangingOptoutToRequested() {
        //SETUP
        //Preparamos a la falsa sesión del usuario loggeado
        when(authenticationService.getLoggedUser()).thenReturn(Optional.of(mockedUser));
        when(mockedUser.getUser_id()).thenReturn( USER_INSCRIBED_ID);
        //Preparamos a la falsa changa
        when(mockedChanga.getUser_id()).thenReturn(USER_OWNER_ID);
        when(mockedChanga.getState()).thenReturn(emitted);
        when(changaDao.getById(CHANGA_ID)).thenReturn(Either.value(mockedChanga));
        //Preparamos al falso usuario que ya esta inscripto en la changa pero en optout
        when(mockedUser.isEnabled()).thenReturn(true);
        when(userDao.getById( USER_INSCRIBED_ID)).thenReturn(Either.value(mockedUser));
        //Creamos la falsa inscriptcion del usuario que está con estado optout
        when(mockedInscription.getState()).thenReturn(optout);
        when(mockedInscription.getChanga_id()).thenReturn(CHANGA_ID);
        when(mockedInscription.getUser_id()).thenReturn(USER_INSCRIBED_ID);
        when(inscriptionDao.getInscription( USER_INSCRIBED_ID, CHANGA_ID)).thenReturn(Either.value(mockedInscription));
        when(inscriptionDao.changeUserStateInChanga(mockedInscription, requested)).thenReturn(OK);

        //EXERCISE
        Validation validation = inscriptionService.inscribeInChanga(USER_INSCRIBED_ID, CHANGA_ID);

        //ASSERT
        assertEquals(OK, validation);
    }

}
