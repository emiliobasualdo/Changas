package ar.edu.itba.paw.interfaces.daos;

import ar.edu.itba.paw.models.Changa;
import ar.edu.itba.paw.models.User;
import javafx.util.Pair;

import java.util.List;

public interface InscriptionDao extends Dao {

    Boolean inscribeInChanga(User user, Changa changa);
    Boolean inscribeInChanga(long user_id, long changa_id);

    List<Pair<User, String>> getInscribedInChanga(Changa changa);
    List<Pair<User, String>> getInscribedInChanga(long changa_id);
}
