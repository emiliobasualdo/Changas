package ar.edu.itba.paw.webapp.controllers;

import ar.edu.itba.paw.interfaces.services.AuthenticationService;
import ar.edu.itba.paw.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.Optional;

@ControllerAdvice
public class UserAuthentication {

    @Autowired
    AuthenticationService authenticationService;

    @ModelAttribute("isUserLogged")
    public boolean isUserLogged() {
        return authenticationService.isUserLogged();
    }

    @ModelAttribute("getLoggedUser")
    public Optional<User> getLoggedUser() { //TODO: meter Either y mandarlo a una vista 500 si ocurre un error
        return authenticationService.getLoggedUser();
    }

}
