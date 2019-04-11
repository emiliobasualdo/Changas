package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.daos.InscriptionDao;
import ar.edu.itba.paw.interfaces.services.InscriptionService;
import ar.edu.itba.paw.interfaces.util.State;
import ar.edu.itba.paw.interfaces.util.Validation;
import ar.edu.itba.paw.models.Changa;
import ar.edu.itba.paw.models.Either;
import ar.edu.itba.paw.models.Inscription;
import ar.edu.itba.paw.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public class InscriptionServiceImpl implements InscriptionService {

    @Autowired
    private InscriptionDao dao;

    @Override
    public Validation inscribeInChanga(long user_id, long changa_id) {
        return dao.inscribeInChanga(user_id,changa_id);
    }

    @Override
    public Validation inscribeInChanga(User user, Changa changa) {
        return this.inscribeInChanga(user.getUser_id(),changa.getChanga_id());
    }

    @Override
    public Validation uninscribeFromChanga(long userId, long changaId) {
        return dao.uninscribeFromChanga(userId,changaId);
    }

    @Override
    public Validation uninscribeFromChanga(User user, Changa changa) {
        return this.uninscribeFromChanga(user.getUser_id(),changa.getChanga_id());
    }

    @Override
    public Either<Map<User, Inscription>, Validation> getInscribedUsers(long changa_id) {
        return dao.getInscribedUsers(changa_id);
    }

    @Override
    public Either<Map<User, Inscription>, Validation> getInscribedUsers(Changa changa) {
        return this.getInscribedUsers(changa.getChanga_id());
    }

    @Override
    public Validation changeUserStateInChanga(long userId, long changaId, State state) {
        return dao.changeUserStateInChanga(userId,changaId,state.getState());
    }

    @Override
    public Validation changeUserStateInChanga(User user, Changa changa, State state) {
        return this.changeUserStateInChanga(user.getUser_id(), changa.getChanga_id(), state);
    }

    @Override
    public Either<Map<Changa, Inscription>, Validation> getUserInscriptions(long userId) {
        return dao.getUserInscriptions(userId);
    }

    @Override
    public Either<Map<Changa, Inscription>, Validation> getUserInscriptions(User user) {
        return this.getUserInscriptions(user.getUser_id());
    }

    @Override
    public Either<Boolean, Validation> isUserInscribedInChanga(long userId, long changaId) {
        return dao.isUserInscribedInChanga(userId, changaId);
    }

    @Override
    public Either<Boolean, Validation> isUserInscribedInChanga(User user, Changa changa) {
        return this.isUserInscribedInChanga(user.getUser_id(), changa.getChanga_id());
    }

    @Override
    public Either<Boolean, Validation> isUserInscribedInChanga(User user, long changaId) {
        return this.isUserInscribedInChanga(user.getUser_id(), changaId);
    }
}
