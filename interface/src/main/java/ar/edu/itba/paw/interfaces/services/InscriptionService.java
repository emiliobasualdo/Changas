package ar.edu.itba.paw.interfaces.services;

import ar.edu.itba.paw.interfaces.util.State;
import ar.edu.itba.paw.interfaces.util.Validation;
import ar.edu.itba.paw.models.Changa;
import ar.edu.itba.paw.models.Either;
import ar.edu.itba.paw.models.Inscription;
import ar.edu.itba.paw.models.User;

import java.util.Map;

public interface InscriptionService {

    Validation inscribeInChanga(User user, Changa changa);
    Validation inscribeInChanga(long userId, long changaId);

    Validation uninscribeFromChanga(long userId, long changaId, State state);
    Validation uninscribeFromChanga(User user, Changa changa, State state);

    Either<Boolean, Validation> isUserInscribedInChanga(long userId, long changaId);
    Either<Boolean, Validation> isUserInscribedInChanga(User user, Changa changa);
    Either<Boolean, Validation> isUserInscribedInChanga(User user, long changaId);

    Either<Map<User, Inscription>, Validation> getInscribedUsers(Changa changa);
    Either<Map<User, Inscription>, Validation> getInscribedUsers(long changaId);

    Validation changeUserStateInChanga(long userId, long changaId, State state);
    Validation changeUserStateInChanga(User user, Changa changa, State state);

    Either<Map<Changa, Inscription>, Validation> getUserInscriptions(User user);
    Either<Map<Changa, Inscription>, Validation> getUserInscriptions(long userId);
}
