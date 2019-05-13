package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.daos.InscriptionDao;
import ar.edu.itba.paw.interfaces.services.InscriptionService;
import ar.edu.itba.paw.models.*;
import ar.edu.itba.paw.interfaces.util.Validation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

import static ar.edu.itba.paw.interfaces.util.Validation.ErrorCodes.*;

@Repository
public class InscriptionServiceImpl implements InscriptionService {

    @Autowired
    private InscriptionDao dao;

    @Override
    public Validation inscribeInChanga(long user_id, long changa_id) {
        return dao.inscribeInChanga(user_id,changa_id);
    }

    @Override
    public Either<List<Pair<User, Inscription>>, Validation> getInscribedUsers(long changa_id) {
        return dao.getInscribedUsers(changa_id);
    }

    @Override
    public Validation changeUserStateInChanga(long userId, long changaId, InscriptionState newState) {
        Either<Inscription, Validation> insc = dao.getInscription(userId, changaId);
        if (insc.isValuePresent())
            return this.changeUserStateInChanga(insc.getValue(), newState);
        else
            return insc.getAlternative();
    }

    @Override
    public Validation changeUserStateInChanga(Inscription insc, InscriptionState newState) {
        if (InscriptionState.changeIsPossible(insc.getState(), newState))
            return dao.changeUserStateInChanga(insc, newState);
        else
            return new Validation(CHANGE_NOT_POSSIBLE);
    }

    @Override
    public Either<List<Pair<Changa, Inscription>>, Validation> getOpenUserInscriptions(long userId) {
        return dao.getUserInscriptions(false, InscriptionState.optout, userId);
    }

    @Override
    public Either<Boolean, Validation> isUserInscribedInChanga(long userId, long changaId) {
        return dao.isUserInscribedInChanga(userId, changaId);
    }

    @Override
    public Either<Inscription, Validation> getInscription(long userId, long changaId){
        return dao.getInscription(userId, changaId);
    }

}
