package ar.edu.itba.paw.service;

import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class InscriptionServiceImplTest {
//
//    //pasar este test a service
//    @Test
//    public void testInscribeInChanga_returnsCorrectValidation() {
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
