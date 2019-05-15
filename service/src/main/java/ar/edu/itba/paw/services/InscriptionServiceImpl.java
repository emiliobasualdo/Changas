package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.services.AuthenticationService;
import ar.edu.itba.paw.interfaces.daos.ChangaDao;
import ar.edu.itba.paw.interfaces.daos.InscriptionDao;
import ar.edu.itba.paw.interfaces.daos.UserDao;
import ar.edu.itba.paw.interfaces.services.InscriptionService;
import ar.edu.itba.paw.models.*;
import ar.edu.itba.paw.interfaces.util.Validation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


import static ar.edu.itba.paw.interfaces.util.Validation.*;
import static ar.edu.itba.paw.models.ChangaState.*;
import static ar.edu.itba.paw.models.InscriptionState.optout;
import static ar.edu.itba.paw.models.InscriptionState.requested;
import static ar.edu.itba.paw.interfaces.util.Validation.CHANGE_NOT_POSSIBLE;

@Service
@Transactional
public class InscriptionServiceImpl implements InscriptionService {

    @Autowired
    private InscriptionDao inscriptionDao;

    @Autowired
    private ChangaDao changaDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private AuthenticationService authenticationService;

    @Override
    /* An Inscription implies that the user is inscribed OR he was inscribed before and opted out */
    public Validation inscribeInChanga(long userId, long changaId) {
        System.out.println(isLoggedUserAuthorizedToInscribeUserInChanga(userId));
        //We check that the logged user is the one who is getting inscribed
        if(!isLoggedUserAuthorizedToInscribeUserInChanga(userId)) {
            return UNAUTHORIZED;
        }
        // We check if the user is the owner of the changa. Changa owners can't inscribe to their own changas
        Either<Changa, Validation> changa = changaDao.getById(changaId);
        if (!changa.isValuePresent())  {
            return changa.getAlternative();
        }
        if (changa.getValue().getUser_id() == userId){
            return USER_OWNS_THE_CHANGA;
        }
        //We check that the user exists effectively and that the user is enabled. A disabled user can't inscribe in a changa
        Either<User, Validation> user = userDao.getById(userId);
        if (!user.isValuePresent())  {
            return user.getAlternative();
        }
        if(!user.getValue().isEnabled()) {
            return DISABLED_USER;
        }
        //We check that the changa state isn't or settled or done or closed. Only emitted changas can get inscriptions
        ChangaState changaState = changa.getValue().getState();
        if(changaState != emitted) {
            return ILLEGAL_ACTION.withMessage("Can not inscribe to non emitted changa.");
        }
        // We check if the user is already inscribed
        Either<Inscription, Validation> insc = getInscription(userId, changaId);
        //If the user had previously been inscribed and opted out, we change the state to requested. Else, we return user already inscribed.
        if (insc.isValuePresent()){
            return insc.getValue().getState() == optout ? changeUserStateInChanga(insc.getValue(), requested) : USER_ALREADY_INSCRIBED;
        }

        //At this point, the user needs to be inscribed.
        return insc.getAlternative() == USER_NOT_INSCRIBED ? inscriptionDao.inscribeInChanga(userId, changaId) : insc.getAlternative();
    }

    private boolean isLoggedUserAuthorizedToInscribeUserInChanga(long userId) {
        return authenticationService.getLoggedUser().isPresent() && authenticationService.getLoggedUser().get().getUser_id() == userId;
    }

    @Override
    public Either<List<Pair<User, Inscription>>, Validation> getInscribedUsers(long changa_id) {
        return inscriptionDao.getInscribedUsers(changa_id);
    }

    @Override
    public Validation changeUserStateInChanga(long userId, long changaId, InscriptionState newState) {
        Either<Inscription, Validation> insc = inscriptionDao.getInscription(userId, changaId);
        if (insc.isValuePresent())
            return this.changeUserStateInChanga(insc.getValue(), newState);
        else
            return insc.getAlternative();
    }

    @Override
    public Validation changeUserStateInChanga(Inscription insc, InscriptionState newState) {
        //We check that the changa in the inscription exists.
        Either<Changa, Validation> inscriptionChanga = changaDao.getById(insc.getChanga_id());
        if (!inscriptionChanga.isValuePresent()) {
            return NO_SUCH_CHANGA;
        }
        //We check that the logged user is authorized to change the changa's state.
        if(!isLoggedUserAuthorizedToChangeInscriptionState(insc.getUser_id(), inscriptionChanga.getValue().getUser_id(), newState)){
            return UNAUTHORIZED;
        }
        //Inscriptions where the changa is closed or done can't have their states changed.
        ChangaState changaState = inscriptionChanga.getValue().getState();
        if(changaState == closed || changaState == done || !InscriptionState.changeIsPossible(insc.getState(), newState) ) {
           return CHANGE_NOT_POSSIBLE;
        }

        return inscriptionDao.changeUserStateInChanga(insc, newState);
    }

    private boolean isLoggedUserAuthorizedToChangeInscriptionState(long inscriptedUserId, long changaOwnerId, InscriptionState newState){
        if(!authenticationService.getLoggedUser().isPresent()){
            return false;
        }
        long userMakingTheChange = authenticationService.getLoggedUser().get().getUser_id();
        //The inscribed user can only change from requested to optout and viceversa.
        if(userMakingTheChange == inscriptedUserId && (newState == optout || newState == requested)) {
            return true;
        }
        //The changa owner can only change state from: requested to accepted, requeted to declined or accepted to declined. He can't change the state of an inscription to optout.
       return userMakingTheChange == changaOwnerId && (newState != optout);
    }

    @Override
    public Either<List<Pair<Changa, Inscription>>, Validation> getOpenUserInscriptions(long userId) {
        return inscriptionDao.getUserInscriptions(false, InscriptionState.optout, userId);
    }

    @Override
    public Either<Boolean, Validation> isUserInscribedInChanga(long userId, long changaId) {
        return inscriptionDao.isUserInscribedInChanga(userId, changaId);
    }

    @Override
    public Either<Inscription, Validation> getInscription(long userId, long changaId){
        return inscriptionDao.getInscription(userId, changaId);
    }

}
