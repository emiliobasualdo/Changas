package ar.edu.itba.paw.webapp.controllers;

import ar.edu.itba.paw.interfaces.services.UserService;
import ar.edu.itba.paw.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.awt.*;
import java.util.Collection;
import java.util.Optional;

@ControllerAdvice
public class UserAuthentication {

    @Autowired
    private UserService us;

    @ModelAttribute("isUserLogged")
    public boolean isUserLogged() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @ModelAttribute("getLoggedUser")
    public Optional<User> getLoggedUser() { //TODO: meter Either y mandarlo a una vista 500 si ocurre un error
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = authentication.getName();
        if (isUserLogged()) {
            return Optional.of(us.findByMail(currentUserName).getValue());
        }
        return Optional.empty();
    }
}
