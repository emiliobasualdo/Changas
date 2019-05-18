package ar.edu.itba.paw.service;

import ar.edu.itba.paw.interfaces.daos.ChangaDao;
import ar.edu.itba.paw.interfaces.daos.InscriptionDao;
import ar.edu.itba.paw.interfaces.util.Validation;
import ar.edu.itba.paw.models.Changa;
import ar.edu.itba.paw.models.ChangaState;
import ar.edu.itba.paw.models.Either;
import ar.edu.itba.paw.services.ChangaServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static ar.edu.itba.paw.interfaces.util.Validation.USERS_INSCRIBED;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class ChangaServiceImplTest {

    private static final String PASSWORD = "Password";
    private static final String EMAIL = "Email@email.com";
    private static final long CHANGA_ID = 123;
    private static final long USER_ID = 123;

    @Mock
    private ChangaDao chDao;

    @Mock
    private InscriptionDao inDao;

    @InjectMocks
    private ChangaServiceImpl changaService;

    private Either<Changa, Validation> mockedEither;
    private Changa.Builder mockedBuilder;
    private Changa mockedChanga;
    private Validation mockedVal;

    @Before
    public void setUp() {
        mockedEither = Mockito.mock(Either.class);
        mockedBuilder = Mockito.mock(Changa.Builder.class);
        mockedChanga = Mockito.mock(Changa.class);
        mockedVal = Mockito.mock(Validation.class);
    }

    //TODO No puedo usar métodos auxiliares de la clase que quiero probar, solo los métodos que quiero probar
    @Test
    public void testGetAll_returnsList() {
        // SETUP
        // preparamos las falsas changas
        List<Changa> mockedList = new ArrayList<>();
        mockedList.add(Mockito.mock(Changa.class));
        mockedList.add(Mockito.mock(Changa.class));
        mockedList.add(Mockito.mock(Changa.class));
        // preparamos el falso dao
        when(chDao.getAll(ChangaState.emitted, 0)).thenReturn(Either.value(mockedList));
        // EJERCITAR
        List<Changa> list = changaService.getEmittedChangas(0).getValue();
        // ASSERT
        assertEquals(3, list.size());
        assertEquals(mockedList, list);
        assertEquals(mockedList.get(0), list.get(0));
        assertEquals(mockedList.get(1), list.get(1));
        assertEquals(mockedList.get(2), list.get(2));
    }

    @Test
    public void testCreate_returnsChanga() {
        // SETUP
        // preparamos el falso dao
        when(chDao.create(mockedBuilder)).thenReturn(Either.value(mockedChanga));
        // EJERCITAR
        Changa changa = changaService.create(mockedBuilder).getValue();
        // ASSERT
        assertEquals(mockedChanga, changa);
    }

    @Test
    public void testCreate_returnsError() {
        // SETUP
        // preparamos el falso dao
        when(chDao.create(mockedBuilder)).thenReturn(Either.alternative(mockedVal));
        // EJERCITAR
        Validation val = changaService.create(mockedBuilder).getAlternative();
        // ASSERT
        assertEquals(mockedVal, val);
    }

    @Test
    public void testUpdate_returnsChanga() {
        // SETUP
        // falso Either
        when(mockedEither.isValuePresent()).thenReturn(true);
        // falso InscriptionDao
        when(inDao.hasInscribedUsers(CHANGA_ID)).thenReturn(false);
        // falso ChangaDao
        when(chDao.getById(CHANGA_ID)).thenReturn(mockedEither);
        when(chDao.update(CHANGA_ID, mockedBuilder)).thenReturn(Either.value(mockedChanga));
        // EJERCITAR
        Changa changa = changaService.update(CHANGA_ID, mockedBuilder).getValue();
        // ASSERT
        assertEquals(mockedChanga, changa);
    }

    @Test
    public void testUpdate_returnsError() {
        // SETUP
        // preparamos el falso dao
        when(chDao.getById(CHANGA_ID)).thenReturn(Either.alternative(mockedVal));
        // EJERCITAR
        Validation val = changaService.update(CHANGA_ID, mockedBuilder).getAlternative();
        // ASSERT
        assertEquals(mockedVal, val);
    }

    @Test
    public void testUpdate_returnsError2() {
        // SETUP
        // falso ChangaDao
        when(chDao.getById(CHANGA_ID)).thenReturn(Either.value(mockedChanga));
        // falso Inscriptiondao
        when(inDao.hasInscribedUsers(CHANGA_ID)).thenReturn(true);
        // EJERCITAR
        Validation val = changaService.update(CHANGA_ID, mockedBuilder).getAlternative();
        // ASSERT
        assertEquals(USERS_INSCRIBED, val);
    }

    @Test
    public void testUpdate_returnsError3() {
        // SETUP
        // falsa Either
        when(mockedEither.isValuePresent()).thenReturn(true);
        // falso InscriptionDao
        when(inDao.hasInscribedUsers(CHANGA_ID)).thenReturn(false);
        // falso ChangaDao
        when(chDao.getById(CHANGA_ID)).thenReturn(mockedEither);
        when(chDao.update(CHANGA_ID, mockedBuilder)).thenReturn(Either.alternative(mockedVal));
        // EJERCITAR
        Validation val = changaService.update(CHANGA_ID, mockedBuilder).getAlternative();
        // ASSERT
        assertEquals(mockedVal, val);
    }

    @Test
    public void testGetById_returnsChanga() {
        // SETUP
        // preparamos el falso dao
        when(chDao.getById(CHANGA_ID)).thenReturn(Either.value(mockedChanga));
        // EJERCITAR
        Changa changa = changaService.getChangaById(CHANGA_ID).getValue();
        // ASSERT
        assertEquals(changa, mockedChanga);
    }

    @Test
    public void testGetById_returnsError() {
        // SETUP
        // preparamos el falso dao
        when(chDao.getById(CHANGA_ID)).thenReturn(Either.alternative(mockedVal));
        // EJERCITAR
        Validation val = changaService.getChangaById(CHANGA_ID).getAlternative();
        // ASSERT
        assertEquals(mockedVal, val);
    }

    @Test
    public void testGetUserOwnedChangas_returnsList() {
        // SETUp
        List<Changa> mockingList = new ArrayList<>();
        mockingList.add(Mockito.mock(Changa.class));
        mockingList.add(Mockito.mock(Changa.class));
        mockingList.add(Mockito.mock(Changa.class));
        // preparamos el falso dao
        when(chDao.getUserOwnedChangas(USER_ID)).thenReturn(Either.value(mockingList));
        // EJERCITAR
        List<Changa> list = changaService.getUserOwnedChangas(USER_ID).getValue();
        // ASSERT
        assertEquals(mockingList, list);
        assertEquals(3, list.size());
    }

    @Test
    public void testGetUserOwnedChangas_returnsError() {
        // SETUP
        // preparamos el falso dao
        when(chDao.getUserOwnedChangas(USER_ID)).thenReturn(Either.alternative(mockedVal));
        // EJERCITAR
        Validation val = changaService.getUserOwnedChangas(USER_ID).getAlternative();
        // ASSERT
        assertEquals(mockedVal, val);
    }

    @Test
    public void testChangeChangaState1_returnsChanga() {
        // SETUP
        // preparamos la changa
        when(mockedChanga.getState()).thenReturn(ChangaState.settled); // para ChangaState.changeIsPossible
        // preparamos una segunda changa
        Changa mockedChanga2 = Mockito.mock(Changa.class);
        when(mockedChanga2.getState()).thenReturn(ChangaState.closed); // mockedState es el nuevo state al que queiro cambiar
        // preparamos el falso dao
        when(chDao.getById(CHANGA_ID)).thenReturn(Either.value(mockedChanga)); // mocked changa tendría que quedar igual
        when(chDao.changeChangaState(eq(CHANGA_ID), any(ChangaState.class))).thenReturn(Either.value(mockedChanga2));
        // EJERCITAR
        Changa changa = changaService.changeChangaState(CHANGA_ID, ChangaState.closed).getValue();
        // ASSERT
        assertNotEquals(mockedChanga, changa);
        assertEquals(mockedChanga2, changa);
    }

    @Test
    public void testChangeChangaState1_returnsError() {
        // SETUP
        // preparamos el falso dao
        when(chDao.getById(CHANGA_ID)).thenReturn(Either.alternative(mockedVal));
        // EJERCITAR
        Validation val = changaService.changeChangaState(CHANGA_ID, ChangaState.closed).getAlternative();
        // ASSERT
        assertEquals(mockedVal, val);
    }

}

