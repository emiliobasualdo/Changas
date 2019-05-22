package ar.edu.itba.paw.interfaces.services;

import ar.edu.itba.paw.models.*;
import ar.edu.itba.paw.interfaces.util.Validation;

import java.util.List;
import java.util.Map;

public interface InscriptionService {

    Validation inscribeInChanga(long userId, long changaId);
    Either<Boolean, Validation> isUserInscribedInChanga(long userId, long changaId);
    Either<List<Pair<User, Inscription>>, Validation> getInscribedUsers(long changaId);
    Either<List<Pair<Changa, Inscription>>, Validation> getOpenUserInscriptions(long userId);
    Validation changeUserStateInChanga(long userId, long changaId, InscriptionState state);
    Validation changeUserStateInChanga(Inscription insc, InscriptionState state);
    Either<Inscription, Validation> getInscription(long userId, long changaId);
    Either<List<Pair<User, Inscription>>, Validation> getAcceptedUsers(long changa_id);
    Either<Boolean, Validation> hasAcceptedUsers(long changa_id);
    Validation setRating(long userId, long changaId, double ratingNum);
}
