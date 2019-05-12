package ar.edu.itba.paw.webapp.controllers;

import ar.edu.itba.paw.Builder;
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
import java.util.ArrayList;
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

        Either<List<Changa>, Validation> maybeChangas = cs.getAllChangas();
        if (maybeChangas.isValuePresent()) {
            if (isUserLogged) {
                Either<List<Pair<Changa, Inscription>>, Validation> maybeInscriptions = is.getUserInscriptions(loggedUser.getUser_id());
                if (maybeInscriptions.isValuePresent()) {
                    maybeInscriptions.getValue().removeIf(e -> e.getValue().getState() == InscriptionState.optout); //TODO: hacer esto en una query
                    List<Pair<Changa, Boolean>> changaList = new ArrayList<>();
                    for (Changa c : maybeChangas.getValue()){
                        boolean notFound = true;
                        for (int i=0; i<maybeInscriptions.getValue().size() && notFound; i++){
                            if (maybeInscriptions.getValue().get(i).getKey().getChanga_id() == c.getChanga_id()){
                                changaList.add(Pair.buildPair(c, true));
                                notFound = false;
                            }
                        }
                        if (notFound){
                            changaList.add(Pair.buildPair(c, false));
                        }
                    }
                    return new ModelAndView("index")
                            .addObject("changaList", changaList);
                } else {
                    return new ModelAndView("redirect:/error").addObject("message", maybeInscriptions.getAlternative().getMessage());
                }
            } else {
                return new ModelAndView("index")
                        .addObject("changaList", maybeChangas.getValue());
            }
        } else {
            return new ModelAndView("redirect:/error").addObject("message", maybeChangas.getAlternative().getMessage());
        }
    }
}
