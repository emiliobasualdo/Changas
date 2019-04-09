package ar.edu.itba.paw.interfaces.daos;

import ar.edu.itba.paw.interfaces.util.ValidationError;
import ar.edu.itba.paw.models.Changa;
import ar.edu.itba.paw.models.Either;
import ar.edu.itba.paw.models.User;
import javafx.util.Pair;

import java.util.List;

public interface InscriptionDao extends Dao {

    Either<Boolean, ValidationError> inscribeInChanga(User user, Changa changa);
    Either<Boolean, ValidationError> inscribeInChanga(long user_id, long changa_id);
    List<Pair<User, String>> getInscribedInChanga(Changa changa);
    List<Pair<User, String>> getInscribedInChanga(long changa_id);
    boolean isUserInscribedInChanga(long userId, long changaId);
}
