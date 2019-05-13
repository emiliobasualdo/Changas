package ar.edu.itba.paw.webapp.controllers;

import ar.edu.itba.paw.interfaces.services.ChangaService;
import ar.edu.itba.paw.interfaces.services.InscriptionService;
import ar.edu.itba.paw.interfaces.services.UserService;
import ar.edu.itba.paw.interfaces.util.Validation;
import ar.edu.itba.paw.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

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

        Either<List<Changa>, Validation> maybeChangas = cs.getAllEmittedChangas(0);
        if (!maybeChangas.isValuePresent()) {
            return new ModelAndView("redirect:/error").addObject("message", maybeChangas.getAlternative().getMessage());
        }

        if (!isUserLogged) {
            return new ModelAndView("index")
                    .addObject("changaList", maybeChangas.getValue());
        }

        Either<List<Pair<Changa, Inscription>>, Validation> maybeInscriptions = is.getOpenUserInscriptions(loggedUser.getUser_id());
        if (!maybeInscriptions.isValuePresent()) {
            return new ModelAndView("redirect:/error").addObject("message", maybeInscriptions.getAlternative().getMessage());
        }

        List<Changa> changas = maybeChangas.getValue();
        List<Pair<Changa, Inscription>> inscriptions = maybeInscriptions.getValue();
        List<Pair<Changa, Boolean>> changaList = new ArrayList<>();
        for (Changa c : changas){
            boolean notFound = true;
            for (int i = 0; i < inscriptions.size() && notFound; i++){
                if (inscriptions.get(i).getKey().getChanga_id() == c.getChanga_id()){
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

    }
}
