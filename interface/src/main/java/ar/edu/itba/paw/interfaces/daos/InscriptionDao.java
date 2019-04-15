package ar.edu.itba.paw.interfaces.daos;

import ar.edu.itba.paw.interfaces.util.Validation;
import ar.edu.itba.paw.models.*;

import java.util.Map;

public interface InscriptionDao {

    Either<Map<Changa, Inscription>, Validation> getUserInscriptions(long userId);
    Either<Map<User, Inscription>, Validation> getInscribedUsers(long changa_id);
    Validation uninscribeFromChanga(long userId, long changaId);
    Validation inscribeInChanga(long user_id, long changa_id);
    Validation changeUserStateInChanga(Inscription insc, InscriptionState newState);
    Validation changeUserStateInChanga(long userId, long changaId, InscriptionState state);
    Either<Boolean, Validation> isUserInscribedInChanga(long userId, long changaId);
    Either<Boolean, Validation > hasInscribedUsers(long changa_id);
    public Either<Inscription, Validation> getInscription(long userId, long changaId);
}
