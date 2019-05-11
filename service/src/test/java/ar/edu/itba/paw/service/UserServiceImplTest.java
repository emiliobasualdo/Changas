package ar.edu.itba.paw.service;

import ar.edu.itba.paw.interfaces.daos.UserDao;
import ar.edu.itba.paw.interfaces.daos.VerificationTokenDao;
import ar.edu.itba.paw.interfaces.services.UserService;
import ar.edu.itba.paw.interfaces.util.Validation;
import ar.edu.itba.paw.models.Either;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.persistence.UserJdbcDao;
import ar.edu.itba.paw.services.UserServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

import static ar.edu.itba.paw.constants.DBTableName.users;
import static ar.edu.itba.paw.constants.DBUserFields.*;
import static ar.edu.itba.paw.interfaces.util.Validation.ErrorCodes.*;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.*;
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
    public void testfFindById_returnsUser() throws Exception {
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
        assertNotNull(either.getValue());
        assertEquals(EMAIL, either.getValue().getEmail());
        assertEquals(PASSWORD, either.getValue().getPasswd());
        assertEquals(ID, either.getValue().getUser_id());
    }

}

