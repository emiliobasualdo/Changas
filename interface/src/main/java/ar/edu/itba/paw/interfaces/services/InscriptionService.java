package ar.edu.itba.paw.interfaces.services;

import ar.edu.itba.paw.models.InscriptionState;
import ar.edu.itba.paw.interfaces.util.Validation;
import ar.edu.itba.paw.models.Changa;
import ar.edu.itba.paw.models.Either;
import ar.edu.itba.paw.models.Inscription;
import ar.edu.itba.paw.models.User;

import java.util.Map;

public interface InscriptionService {

    Validation inscribeInChanga(long userId, long changaId);
    Validation uninscribeFromChanga(long userId, long changaId);
    Either<Boolean, Validation> isUserInscribedInChanga(long userId, long changaId);
    Either<Map<User, Inscription>, Validation> getInscribedUsers(long changaId);
    Either<Map<Changa, Inscription>, Validation> getUserInscriptions(long userId);
    Validation changeUserStateInChanga(long userId, long changaId, InscriptionState state);
    Validation changeUserStateInChanga(Inscription insc, InscriptionState state);
}
