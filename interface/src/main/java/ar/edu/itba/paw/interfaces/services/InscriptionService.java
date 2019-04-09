package ar.edu.itba.paw.interfaces.services;

import ar.edu.itba.paw.interfaces.util.ValidationError;
import ar.edu.itba.paw.models.Changa;
import ar.edu.itba.paw.models.Either;
import ar.edu.itba.paw.models.Inscription;
import ar.edu.itba.paw.models.User;
import javafx.util.Pair;
import java.util.List;

public interface InscriptionService {

    Either<Boolean, ValidationError> inscribeInChanga(User user, Changa changa);
    Either<Boolean, ValidationError> inscribeInChanga(long user_id, long changa_id);
    boolean isUserInscribedInChanga(long userId, long changaId);
    List<Pair<User, Inscription>> getInscribedUsers(Changa changa);
    List<Pair<User, Inscription>> getInscribedUsers(long changa_id);
}
