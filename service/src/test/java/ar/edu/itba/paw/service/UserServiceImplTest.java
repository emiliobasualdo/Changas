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

import static ar.edu.itba.paw.interfaces.util.Validation.*;
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

    private User mockedUser;
    private Validation mockedVal;
    private User.Builder mockedUserBuilder;
    @Before
    public void setUp() {
        mockedUser = Mockito.mock(User.class);
        mockedVal = Mockito.mock(Validation.class);
        mockedUserBuilder = Mockito.mock(User.Builder.class);
    }

    //TODO No puedo usar métodos auxiliares de la clase que quiero probar, solo los métodos que quiero probar

    @Test
    public void testfFindById_returnsUser() {
        // SETUP
        // preparamos el falso usuario
        when(mockedUser.getUser_id()).thenReturn(ID);
        when(mockedUser.getPasswd()).thenReturn(PASSWORD);
        when(mockedUser.getEmail()).thenReturn(EMAIL);
        // preparamos el falso dao
        when(userDao.getById(ID)).thenReturn(Either.value(mockedUser));
        // EJERCITAR
        User user = userService.findById(ID).getValue();
        // ASSERT
        assertEquals(EMAIL, user.getEmail());
        assertEquals(PASSWORD, user.getPasswd());
        assertEquals(ID, user.getUser_id());
    }

    @Test
    public void testfFindById_returnsError() {
        // SETUP
        // preparamos el falso dao
        when(userDao.getById(ID)).thenReturn(Either.alternative(mockedVal));
        // EJERCITAR
        Validation val = userService.findById(ID).getAlternative();
        // ASSERT
        assertEquals(mockedVal, val);
    }

    @Test
    public void testfFindByEmail_returnsUser() {
        // SETUP
        // preparamos el falso usuario
        when(mockedUser.getUser_id()).thenReturn(ID);
        when(mockedUser.getPasswd()).thenReturn(PASSWORD);
        when(mockedUser.getEmail()).thenReturn(EMAIL);
        // preparamos el falso dao
        when(userDao.findByMail(EMAIL)).thenReturn(Either.value(mockedUser));
        // EJERCITAR
        User user = userService.findByMail(EMAIL).getValue();
        // ASSERT
        assertEquals(EMAIL, user.getEmail());
        assertEquals(PASSWORD, user.getPasswd());
        assertEquals(ID, user.getUser_id());
    }

    @Test
    public void testfFindByEmail_returnsError() {
        // SETUP
        // preparamos el falso dao
        when(userDao.findByMail(EMAIL)).thenReturn(Either.alternative(DATABASE_ERROR));
        // EJERCITAR
        Validation val = userService.findByMail(EMAIL).getAlternative();
        // ASSERT
        assertEquals(DATABASE_ERROR, val);
    }

    @Test
    public void testRegister_returnsUser() {
        // SETUP
        // preparamos el builder
        when(mockedUserBuilder.getEmail()).thenReturn(EMAIL);
        when(mockedUserBuilder.getPasswd()).thenReturn(PASSWORD);
        // preparamos el falso usuario
        when(mockedUser.getUser_id()).thenReturn(ID);
        when(mockedUser.getPasswd()).thenReturn(PASSWORD);
        when(mockedUser.getEmail()).thenReturn(EMAIL);
        // preparamos el dao
        when(userDao.findByMail(EMAIL)).thenReturn(Either.alternative(NO_SUCH_USER));
        when(userDao.create(mockedUserBuilder)).thenReturn(Either.value(mockedUser));
        // EJERCITAR
        User user = userService.register(mockedUserBuilder).getValue();
        // ASSERT
        assertEquals(EMAIL, user.getEmail());
        assertEquals(PASSWORD, user.getPasswd());
        assertEquals(ID, user.getUser_id());
    }

    @Test
    public void testRegister_returnsErrorUSER_ALREADY_EXISTS() {
        // SETUP
        // preparamos el dao
        when(userDao.findByMail(EMAIL)).thenReturn(Either.value(mockedUser));
        // EJERCITAR
        Validation val = userService.register(new User.Builder().withEmail(EMAIL)).getAlternative();
        // ASSERT
        assertEquals(USER_ALREADY_EXISTS, val);
    }

    @Test
    public void testRegister_returnsErrorDATABASE_ERROR() {
        // SETUP
        // preparamos el falso dao
        when(userDao.findByMail(EMAIL)).thenReturn(Either.alternative(DATABASE_ERROR));
        // EJERCITAR
        final Either<User, Validation> either = userService.register(new User.Builder().withEmail(EMAIL));
        // ASSERT
        assertNotNull(either);
        assertNotNull(either.getAlternative());
        assertEquals(DATABASE_ERROR, either.getAlternative());
    }

    @Test
    public void testSetUserEnabledStatus_returnsOriginalVal() {
        // SETUP
        // preparamos el dao
        when(userDao.setUserStatus(ID, true)).thenReturn(mockedVal);
        // EJERCITAR
        Validation val = userService.setUserEnabledStatus(ID, true);
        // ASSERT
        assertEquals(val, mockedVal);
    }

    @Test
    public void testConfirmMailVerification_returnsOK() {
        // SETUP
        // preparamos el dao
        when(userDao.setUserStatus(ID, true)).thenReturn(OK);
        // EJERCITAR
        Validation val = userService.setUserEnabledStatus(ID, true);
        // ASSERT
        assertEquals(val, OK);
    }

}

