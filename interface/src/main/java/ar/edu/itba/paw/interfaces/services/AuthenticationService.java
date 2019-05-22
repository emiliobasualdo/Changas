package ar.edu.itba.paw.interfaces.services;

import ar.edu.itba.paw.models.User;
import org.springframework.security.core.Authentication;

import java.util.Optional;

public interface AuthenticationService {
    Authentication getAuthentication();
    boolean isUserLogged();
    Optional<User> getLoggedUser();
    boolean isLoggedUserAuthorizedToUpdateUser(long userId);

    boolean isLoggedUserAuthorizedToUpdateChanga(long changaOwnerId);
}
