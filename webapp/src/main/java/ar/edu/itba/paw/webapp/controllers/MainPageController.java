package ar.edu.itba.paw.webapp.controllers;

import ar.edu.itba.paw.interfaces.services.ChangaService;
import ar.edu.itba.paw.interfaces.services.UserService;
import ar.edu.itba.paw.interfaces.util.Validation;
import ar.edu.itba.paw.models.Changa;
import ar.edu.itba.paw.models.Either;
import ar.edu.itba.paw.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class MainPageController {

    @Autowired
    private ChangaService cs;

    @Autowired
    private UserService us;

    @ModelAttribute("currentUser")
    public User getCurrentUser() {
        if (isUserLoggedIn()) {
            return getLoggedUser();
        }
        return null; // nefasto
    }

    public boolean isUserLoggedIn() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return !(authentication instanceof AnonymousAuthenticationToken);
    }

    public User getLoggedUser() { //TODO: meter Either y mandarlo a una vista 500 si ocurre un error
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = authentication.getName();
        return us.findByMail(currentUserName).getValue();
    }

    @RequestMapping(value = "/")
    public ModelAndView showChangas() {
        Either<List<Changa>, Validation> either = cs.getAllChangas();
        if (either.isValuePresent()) {
            return new ModelAndView("index")
                    .addObject("changaList", either.getValue());
        }
        else
            return new ModelAndView("500");
    }

}
