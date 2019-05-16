package ar.edu.itba.paw.webapp.controllers;

import ar.edu.itba.paw.interfaces.services.CategoryService;
import ar.edu.itba.paw.interfaces.services.ChangaService;
import ar.edu.itba.paw.interfaces.services.InscriptionService;
import ar.edu.itba.paw.interfaces.util.Validation;
import ar.edu.itba.paw.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Controller
public class MainPageController {

    @Autowired
    private ChangaService cs;

    @Autowired
    private CategoryService catService;

    @Autowired
    private InscriptionService is;

    @RequestMapping(value = "/")
    public ModelAndView showChangas(@ModelAttribute("getLoggedUser") User loggedUser,
                                    @ModelAttribute("isUserLogged") boolean isUserLogged) {

        Either<List<Changa>, Validation> maybeChangas = cs.getEmittedChangas(0);
        if (!maybeChangas.isValuePresent()) {
            return new ModelAndView("redirect:/error").addObject("message", maybeChangas.getAlternative().getMessage());
        }

        // if the user logged in we show raw data
        List<Changa> changas = maybeChangas.getValue();
        if (!isUserLogged) {
            return showChangas(changas).addObject("isFiltered", false);
        }

        // else we mark the inscribbed changas
        Either<List<Pair<Changa, Boolean>>, Validation> maybeMarkedInscriptions = markedInscriptions(loggedUser, changas);
        if(!maybeMarkedInscriptions.isValuePresent()){
            return new ModelAndView("redirect:/error").addObject("message", maybeMarkedInscriptions.getAlternative().getMessage());
        }
        return showChangas(maybeMarkedInscriptions.getValue()).addObject("isFiltered", false);
    }

    private ModelAndView showChangas(List<?> changasToShow) {

        ModelAndView indexModel = new ModelAndView("index");

        // we add the list of changa categories
        List<String> categories = catService.getCategories();
        indexModel.addObject("categories", categories);

        indexModel.addObject("changaList", changasToShow);

        return indexModel;
    }

    @RequestMapping(value = "/filter")
    public ModelAndView filterChangas(@ModelAttribute("getLoggedUser") User loggedUser,
                                      @ModelAttribute("isUserLogged") boolean isUserLogged,
                                      @RequestParam(value = "cfilter", defaultValue = "") String categoryFilter,
                                      @RequestParam(value = "tfilter", defaultValue = "") String titleFilter) {

        Either<List<Changa>, Validation> changas = cs.getEmittedChangasFiltered(0, categoryFilter, titleFilter);
        if (!changas.isValuePresent()) {
            return new ModelAndView("redirect:/error").addObject("message", changas.getAlternative().getMessage());
        }

        if (isUserLogged){
            Either<List<Pair<Changa, Boolean>>, Validation> maybeMarkedInscriptions = markedInscriptions(loggedUser, changas.getValue());
            if(!maybeMarkedInscriptions.isValuePresent()){
                return new ModelAndView("redirect:/error").addObject("message", maybeMarkedInscriptions.getAlternative().getMessage());
            }
            return showChangas(maybeMarkedInscriptions.getValue())
                    .addObject("isFiltered", true)
                    .addObject("cfilter", categoryFilter)
                    .addObject("tfilter", titleFilter);
        }

        return showChangas(changas.getValue())
                .addObject("isFiltered", true)
                .addObject("cfilter", categoryFilter)
                .addObject("tfilter", titleFilter);
    }

    private Either<List<Pair<Changa, Boolean>>, Validation> markedInscriptions(User loggedUser, List<Changa> changas) {
        Either<List<Pair<Changa, Inscription>>, Validation> maybeInscriptions = is.getOpenUserInscriptions(loggedUser.getUser_id());
        if (!maybeInscriptions.isValuePresent()) {
            return Either.alternative(maybeInscriptions.getAlternative());
        }

        // for each changa, if the user is isncribbed mark it as true
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

        return Either.value(changaList);
    }

    @RequestMapping(value = "/page")
    public ModelAndView showMoreChangas(@RequestParam("page") int page, @ModelAttribute("getLoggedUser") User loggedUser, @ModelAttribute("isUserLogged") boolean isUserLogged) {
        Either<List<Changa>, Validation> maybeChangas = cs.getEmittedChangas(page);
        if (!maybeChangas.isValuePresent()) {
            return new ModelAndView();
        }
        if (!isUserLogged) {
            return new ModelAndView("page").addObject("changaPage", maybeChangas.getValue())
                    .addObject("page", page);
        }
        Either<List<Pair<Changa, Inscription>>, Validation> maybeInscriptions = is.getOpenUserInscriptions(loggedUser.getUser_id());
        if (!maybeInscriptions.isValuePresent()) {
            return new ModelAndView();
        }
        Either<List<Pair<Changa, Boolean>>, Validation> changas =  markedInscriptions(loggedUser, maybeChangas.getValue());
        if (!changas.isValuePresent()){
            return new ModelAndView("redirect:/error").addObject("message", changas.getAlternative().getMessage());
        }
        return new ModelAndView("page")
                .addObject("changaPage", changas.getValue());
    }
}
