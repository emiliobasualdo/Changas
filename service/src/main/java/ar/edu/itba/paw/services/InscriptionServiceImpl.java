package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.daos.InscriptionDao;
import ar.edu.itba.paw.interfaces.services.InscriptionService;
import ar.edu.itba.paw.models.InscriptionState;
import ar.edu.itba.paw.interfaces.util.Validation;
import ar.edu.itba.paw.models.Changa;
import ar.edu.itba.paw.models.Either;
import ar.edu.itba.paw.models.Inscription;
import ar.edu.itba.paw.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Map;

import static ar.edu.itba.paw.interfaces.util.Validation.ErrorCodes.CHANGE_NOT_POSSIBLE;

@Repository
public class InscriptionServiceImpl implements InscriptionService {

    @Autowired
    private InscriptionDao dao;

    @Override
    public Validation inscribeInChanga(long user_id, long changa_id) {
        return dao.inscribeInChanga(user_id,changa_id);
    }

    @Override
    public Validation uninscribeFromChanga(long userId, long changaId) {
        return dao.uninscribeFromChanga(userId,changaId);
    }

    @Override
    public Either<Map<User, Inscription>, Validation> getInscribedUsers(long changa_id) {
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
    public Either<Map<Changa, Inscription>, Validation> getUserInscriptions(long userId) {
        return dao.getUserInscriptions(userId);
    }

    @Override
    public Either<Boolean, Validation> isUserInscribedInChanga(long userId, long changaId) {
        return dao.isUserInscribedInChanga(userId, changaId);
    }

}
