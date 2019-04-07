package ar.edu.itba.paw.interfaces.services;

import ar.edu.itba.paw.models.Changa;
import ar.edu.itba.paw.models.User;
import javafx.util.Pair;

import java.util.List;

public interface InscriptionService {

    Boolean inscribeInChanga(User user, Changa changa);
    Boolean inscribeInChanga(long user_id, long changa_id);
    List<Pair<User, String>> getInscribedUsers(Changa changa);
    List<Pair<User, String>> getInscribedUsers(long changa_id);
}
