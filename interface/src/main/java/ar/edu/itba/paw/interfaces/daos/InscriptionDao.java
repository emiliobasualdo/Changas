package ar.edu.itba.paw.interfaces.daos;

import ar.edu.itba.paw.interfaces.util.Validation;
import ar.edu.itba.paw.models.*;

import java.util.List;
import java.util.Map;

public interface InscriptionDao {

    Either<List<Pair<Changa, Inscription>>, Validation> getUserInscriptions(long userId);
    Either<List<Pair<User, Inscription>>, Validation> getInscribedUsers(long changa_id);
    Validation inscribeInChanga(long user_id, long changa_id);
    Validation changeUserStateInChanga(Inscription insc, InscriptionState newState);
    Validation changeUserStateInChanga(long userId, long changaId, InscriptionState state);
    Either<Boolean, Validation> isUserInscribedInChanga(long userId, long changaId);
    boolean hasInscribedUsers(long changa_id);
    Either<Inscription, Validation> getInscription(long userId, long changaId);
}
