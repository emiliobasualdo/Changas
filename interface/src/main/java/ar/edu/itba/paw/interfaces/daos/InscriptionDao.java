package ar.edu.itba.paw.interfaces.daos;

import ar.edu.itba.paw.interfaces.util.Validation;
import ar.edu.itba.paw.models.Changa;
import ar.edu.itba.paw.models.Either;
import ar.edu.itba.paw.models.Inscription;

import java.util.Map;

public interface InscriptionDao {

    Either<Map<Changa, Inscription>, Validation> getUserInscriptions(long userId);
    Either getInscribedUsers(long changa_id);

    Validation uninscribeFromChanga(long userId, long changaId, String state);
    Validation inscribeInChanga(long user_id, long changa_id);

    Validation changeUserStateInChanga(long userId, long changaId, String state);
    Either<Boolean, Validation> isUserInscribedInChanga(long userId, long changaId);
    Either<Boolean, Validation> hasInscribedUsers(long changa_id);
}
