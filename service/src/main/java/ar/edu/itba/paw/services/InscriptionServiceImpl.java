package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.daos.InscriptionDao;
import ar.edu.itba.paw.interfaces.services.InscriptionService;
import ar.edu.itba.paw.models.Changa;
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
    public Boolean inscribeInChanga(User user, Changa changa) {
        return dao.inscribeInChanga(user,changa);
    }

    @Override
    public Boolean inscribeInChanga(long user_id, long changa_id) {
        return dao.inscribeInChanga(user_id,changa_id);
    }

    @Override
    public List<Pair<User, String>> getInscribedUsers(Changa changa) {
        return dao.getInscribeInChanga(changa);
    }

    @Override
    public List<Pair<User, String>> getInscribedUsers(long changa_id) {
        return dao.getInscribeInChanga(changa_id);
    }
}
