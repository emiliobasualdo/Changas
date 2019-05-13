package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.daos.ChangaDao;
import ar.edu.itba.paw.interfaces.daos.InscriptionDao;
import ar.edu.itba.paw.interfaces.services.InscriptionService;
import ar.edu.itba.paw.models.*;
import ar.edu.itba.paw.interfaces.util.Validation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

import static ar.edu.itba.paw.interfaces.util.Validation.ErrorCodes.*;
import static ar.edu.itba.paw.models.ChangaState.*;
import static ar.edu.itba.paw.models.InscriptionState.optout;
import static ar.edu.itba.paw.models.InscriptionState.requested;

@Repository
public class InscriptionServiceImpl implements InscriptionService {

    @Autowired
    private InscriptionDao inscriptionDao;

    @Autowired
    private ChangaDao changaDao;

    @Override
    /* An Inscription implies that the user is inscribed OR he had inscribed himself before and optout */
    public Validation inscribeInChanga(long userId, long changaId) {
        // We check if the user is the owner of the changa
        Either<Changa, Validation> changa = changaDao.getById(changaId);
        if (changa.isValuePresent()) {
            ChangaState changaState = changa.getValue().getState();
            if(changaState != emitted) {
                if (changaState == closed) {
                    return new Validation(CHANGA_CLOSED);
                }
                if (changaState == done) {
                    return new Validation(CHANGA_DONE);
                }
                if (changaState == settled) {
                    return new Validation(CHANGA_SETTLED);
                }
            }
            if (changa.getValue().getUser_id() == userId){
                return new Validation(USER_OWNS_THE_CHANGA);
            }
        } else {
            return changa.getAlternative();
        }

        // We check if the user is already inscribed
        Either<Inscription, Validation> insc = getInscription(userId, changaId);
        if (insc.isValuePresent()){
            //if the user had previously been inscribed and opted out, we change the state to requested. Else, we return user already inscribed.
            return insc.getValue().getState() == optout ? changeUserStateInChanga(insc.getValue(), requested) : new Validation(USER_ALREADY_INSCRIBED);
        } else { // user needs to be inscribbed
            if (insc.getAlternative().getEc() == USER_NOT_INSCRIBED){
                return inscriptionDao.inscribeInChanga(userId, changaId);
            } else {
                return insc.getAlternative();
            }
        }
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
        if (InscriptionState.changeIsPossible(insc.getState(), newState))
            return inscriptionDao.changeUserStateInChanga(insc, newState);
        else
            return new Validation(CHANGE_NOT_POSSIBLE);
    }

    @Override
    public Either<List<Pair<Changa, Inscription>>, Validation> getUserInscriptions(long userId) {
        return inscriptionDao.getUserInscriptions(userId);
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
