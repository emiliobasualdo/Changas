package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.daos.ChangaDao;
import ar.edu.itba.paw.interfaces.daos.InscriptionDao;
import ar.edu.itba.paw.interfaces.daos.UserDao;
import ar.edu.itba.paw.interfaces.services.InscriptionService;
import ar.edu.itba.paw.models.*;
import ar.edu.itba.paw.interfaces.util.Validation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;


import static ar.edu.itba.paw.interfaces.util.Validation.*;
import static ar.edu.itba.paw.models.ChangaState.*;
import static ar.edu.itba.paw.models.InscriptionState.optout;
import static ar.edu.itba.paw.models.InscriptionState.requested;
import static ar.edu.itba.paw.interfaces.util.Validation.CHANGE_NOT_POSSIBLE;

@Repository
public class InscriptionServiceImpl implements InscriptionService {

    @Autowired
    private InscriptionDao inscriptionDao;

    @Autowired
    private ChangaDao changaDao;

    @Autowired
    private UserDao userDao;

    @Override
    /* An Inscription implies that the user is inscribed OR he had inscribed himself before and optout */
    public Validation inscribeInChanga(long userId, long changaId) {
        // We check if the user is the owner of the changa
        Either<Changa, Validation> changa = changaDao.getById(changaId);
        if (!changa.isValuePresent())  {
            return changa.getAlternative();
        }
        Either<User, Validation> user = userDao.getById(userId);
        if (!user.isValuePresent())  {
            return user.getAlternative();
        }

        ChangaState changaState = changa.getValue().getState();
        if(changaState != emitted) {
            return ILLEGAL_ACTION.withMessage("Can not inscribe to non emitted changa.");
        }
        if (changa.getValue().getUser_id() == userId){
            return USER_OWNS_THE_CHANGA;
        }

        // We check if the user is already inscribed
        Either<Inscription, Validation> insc = getInscription(userId, changaId);
        if (insc.isValuePresent()){
            //if the user had previously been inscribed and opted out, we change the state to requested. Else, we return user already inscribed.
            return insc.getValue().getState() == optout ? changeUserStateInChanga(insc.getValue(), requested) : USER_ALREADY_INSCRIBED;
        } else { // user needs to be inscribbed
            if (insc.getAlternative() == USER_NOT_INSCRIBED){
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
            return CHANGE_NOT_POSSIBLE;
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
