package ar.edu.itba.paw.webapp.controllers;

import ar.edu.itba.paw.interfaces.services.ChangaService;
import ar.edu.itba.paw.interfaces.services.InscriptionService;
import ar.edu.itba.paw.interfaces.services.UserService;
import ar.edu.itba.paw.interfaces.util.Validation;
import ar.edu.itba.paw.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@Controller
public class MainPageController { //TODO: hacer que los jsp sea HTML safe

    @Autowired
    private ChangaService cs;

    @Autowired
    private UserService us;

    @Autowired
    private InscriptionService is;

    @RequestMapping(value = "/")
    public ModelAndView showChangas(@ModelAttribute("getLoggedUser") User loggedUser, @ModelAttribute("isUserLogged") boolean isUserLogged) {
        Either<List<Changa>, Validation> either = cs.getAllChangas();
        if (isUserLogged) {
            Either<List<Pair<Changa, Inscription>>, Validation> eitherMap = is.getUserInscriptions(loggedUser.getUser_id());
            if (either.isValuePresent()) {
                if (eitherMap.isValuePresent()) {
                    return new ModelAndView("index")
                            .addObject("changaList", either.getValue())
                            .addObject("userInscriptions", eitherMap.getValue());
                } else {
                    return new ModelAndView("500"); //todo: esta bien esto?
                }
            } else {
                return new ModelAndView("500");
            }
        }
        else {
            if (either.isValuePresent()) {
                    return new ModelAndView("index")
                            .addObject("changaList", either.getValue());
            } else {
                return new ModelAndView("500");
            }
        }
    }

}
