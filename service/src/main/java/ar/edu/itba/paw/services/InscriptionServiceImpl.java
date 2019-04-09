package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.daos.InscriptionDao;
import ar.edu.itba.paw.interfaces.services.InscriptionService;
import ar.edu.itba.paw.interfaces.util.ValidationError;
import ar.edu.itba.paw.models.Changa;
import ar.edu.itba.paw.models.Either;
import ar.edu.itba.paw.models.User;
import javafx.util.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class InscriptionServiceImpl implements InscriptionService {

    @Autowired
    private InscriptionDao dao;

    @Override
    public Either<Boolean, ValidationError> inscribeInChanga(User user, Changa changa) {
        return dao.inscribeInChanga(user,changa);
    }

    @Override
    public Either<Boolean, ValidationError> inscribeInChanga(long user_id, long changa_id) {
        return dao.inscribeInChanga(user_id,changa_id);
    }

    @Override
    public List<Pair<User, String>> getInscribedUsers(Changa changa) {
        return dao.getInscribedInChanga(changa);
    }

    @Override
    public List<Pair<User, String>> getInscribedUsers(long changa_id) {
        return dao.getInscribedInChanga(changa_id);
    }

    @Override
    public boolean isUserInscribedInChanga(long userId, long changaId) {
        return dao.isUserInscribedInChanga(userId, changaId);
    }
}
