package ar.edu.itba.paw.service;

import ar.edu.itba.paw.interfaces.daos.UserDao;
import ar.edu.itba.paw.interfaces.daos.VerificationTokenDao;
import ar.edu.itba.paw.interfaces.util.Validation;
import ar.edu.itba.paw.models.Either;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.services.UserServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.crypto.password.PasswordEncoder;

import static ar.edu.itba.paw.interfaces.util.Validation.ErrorCodes.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class UserServiceImplTest {

    private static final String PASSWORD = "Password";
    private static final String EMAIL = "Email@email.com";
    private static final long ID = 123;

    @Mock
    private UserDao userDao;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private VerificationTokenDao verificationTokenDao;

    @InjectMocks
    private UserServiceImpl userService;

    @Before
    public void setUp() {
    }

    //TODO No puedo usar métodos auxiliares de la clase que quiero probar, solo los métodos que quiero probar

    @Test
    public void testfFindById_returnsUser() {
        // SETUP
        // preparamos el falso usuario
        User user = Mockito.mock(User.class);
        when(user.getUser_id()).thenReturn(ID);
        when(user.getPasswd()).thenReturn(PASSWORD);
        when(user.getEmail()).thenReturn(EMAIL);
        // preparamos el falso either
        Either<User, Validation> mockedEither = Mockito.mock(Either.class); // no si esto está bien
        when(mockedEither.getValue()).thenReturn(user);
        // preparamos el falso dao
        when(userDao.getById(ID)).thenReturn(mockedEither);
        // EJERCITAR
        final Either<User, Validation> either = userService.findById(ID);
        // ASSERT
        assertNotNull(either);
        assertNotNull(either.getValue());
        assertEquals(EMAIL, either.getValue().getEmail());
        assertEquals(PASSWORD, either.getValue().getPasswd());
        assertEquals(ID, either.getValue().getUser_id());
    }

    @Test
    public void testfFindById_returnsError() {
        // SETUP
        // preparamos el falso dao
        when(userDao.getById(ID)).thenReturn(Either.alternative(new Validation(DATABASE_ERROR)));
        // EJERCITAR
        final Either<User, Validation> either = userService.findById(ID);
        // ASSERT
        assertNotNull(either);
        assertNotNull(either.getAlternative());
        assertEquals(DATABASE_ERROR, either.getAlternative().getEc());
    }

    @Test
    public void testfFindByEmail_returnsUser() {
        // SETUP
        // preparamos el falso usuario
        User user = Mockito.mock(User.class);
        when(user.getUser_id()).thenReturn(ID);
        when(user.getPasswd()).thenReturn(PASSWORD);
        when(user.getEmail()).thenReturn(EMAIL);
        // preparamos el falso either
        Either<User, Validation> mockedEither = Mockito.mock(Either.class); // no si esto está bien
        when(mockedEither.getValue()).thenReturn(user);
        // preparamos el falso dao
        when(userDao.findByMail(EMAIL)).thenReturn(mockedEither);
        // EJERCITAR
        final Either<User, Validation> either = userService.findByMail(EMAIL);
        // ASSERT
        assertNotNull(either);
        assertNotNull(either.getValue());
        assertEquals(EMAIL, either.getValue().getEmail());
        assertEquals(PASSWORD, either.getValue().getPasswd());
        assertEquals(ID, either.getValue().getUser_id());
    }

    @Test
    public void testfFindByEmail_returnsError() {
        // SETUP
        // preparamos el falso dao
        when(userDao.findByMail(EMAIL)).thenReturn(Either.alternative(new Validation(DATABASE_ERROR)));
        // EJERCITAR
        final Either<User, Validation> either = userService.findByMail(EMAIL);
        // ASSERT
        assertNotNull(either);
        assertNotNull(either.getAlternative());
        assertEquals(DATABASE_ERROR, either.getAlternative().getEc());
    }

    @Test
    public void testRegister_returnsUser() {
        // SETUP
        // preparamos el builder
        User.Builder userBuilder = Mockito.mock(User.Builder.class);
        when(userBuilder.getEmail()).thenReturn(EMAIL);
        when(userBuilder.getPasswd()).thenReturn(PASSWORD);
        // preparamos el falso usuario
        User user = Mockito.mock(User.class);
        when(user.getUser_id()).thenReturn(ID);
        when(user.getPasswd()).thenReturn(PASSWORD);
        when(user.getEmail()).thenReturn(EMAIL);
        // preparamos el dao
        when(userDao.findByMail(EMAIL)).thenReturn(Either.alternative(new Validation(NO_SUCH_USER)));
        when(userDao.create(userBuilder)).thenReturn(Either.value(user));
        // EJERCITAR
        final Either<User, Validation> either = userService.register(userBuilder);
        // ASSERT
        assertNotNull(either);
        assertNotNull(either.getValue());
        assertEquals(EMAIL, either.getValue().getEmail());
        assertEquals(PASSWORD, either.getValue().getPasswd());
        assertEquals(ID, either.getValue().getUser_id());
    }

    @Test
    public void testRegister_returnsErrorUSER_ALREADY_EXISTS() {
        // SETUP
        // preparamos el falso usuario
        User user = Mockito.mock(User.class);
        // preparamos el dao
        when(userDao.findByMail(EMAIL)).thenReturn(Either.value(user));
        // EJERCITAR
        final Either<User, Validation> either = userService.register(new User.Builder().withEmail(EMAIL));
        // ASSERT
        assertNotNull(either);
        assertNotNull(either.getAlternative());
        assertEquals(USER_ALREADY_EXISTS, either.getAlternative().getEc());
    }

    @Test
    public void testRegister_returnsErrorDATABASE_ERROR() {
        // SETUP
        // preparamos el falso dao
        when(userDao.findByMail(EMAIL)).thenReturn(Either.alternative(new Validation(DATABASE_ERROR)));
        // EJERCITAR
        final Either<User, Validation> either = userService.register(new User.Builder().withEmail(EMAIL));
        // ASSERT
        assertNotNull(either);
        assertNotNull(either.getAlternative());
        assertEquals(DATABASE_ERROR, either.getAlternative().getEc());
    }

    @Test
    public void testSetUserEnabledStatus_returnsOriginalVal() {
        // SETUP
        Validation mockedVal = Mockito.mock(Validation.class);
        // preparamos el dao
        when(userDao.setUserStatus(ID, true)).thenReturn(mockedVal);
        // EJERCITAR
        Validation val = userService.setUserEnabledStatus(ID, true);
        // ASSERT
        assertNotNull(val);
        assertEquals(val, mockedVal);
    }

    @Test
    public void testConfirmMailVerification_returnsOK() {
        // SETUP
        // preparamos el dao
        when(userDao.setUserStatus(ID, true)).thenReturn(new Validation(OK));
        // EJERCITAR
        Validation val = userService.setUserEnabledStatus(ID, true);
        // ASSERT
        assertNotNull(val);
        assertNotNull(val.getEc());
        assertEquals(val.getEc(), OK);
    }

}

