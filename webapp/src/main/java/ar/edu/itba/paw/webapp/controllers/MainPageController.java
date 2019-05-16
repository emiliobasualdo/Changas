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
    public ModelAndView showChangas(@ModelAttribute("getLoggedUser") User loggedUser, @ModelAttribute("isUserLogged") boolean isUserLogged) {
        ModelAndView model = new ModelAndView("index");
        Either<List<Changa>, Validation> maybeChangas = cs.getEmittedChangas(0);
        if (!maybeChangas.isValuePresent()) {
            return new ModelAndView("redirect:/error").addObject("message", maybeChangas.getAlternative().getMessage());
        }

        List<String> categories = catService.getCategories();
        model.addObject("categories", categories);

        if (!isUserLogged) {
            return model.addObject("changaList", maybeChangas.getValue());
        }

        Either<List<Pair<Changa, Inscription>>, Validation> maybeInscriptions = is.getOpenUserInscriptions(loggedUser.getUser_id());
        if (!maybeInscriptions.isValuePresent()) {
            return new ModelAndView("redirect:/error").addObject("message", maybeInscriptions.getAlternative().getMessage());
        }

        return new ModelAndView("index")
                .addObject("changaList", getChangas(maybeChangas.getValue(), maybeInscriptions.getValue()))
                .addObject("isFiltered", false);
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
        List<Pair<Changa, Boolean>> changas =  getChangas(maybeChangas.getValue(), maybeInscriptions.getValue());
        return new ModelAndView("page")
                .addObject("changaPage", changas);
    }

    private List<Pair<Changa, Boolean>> getChangas(List<Changa> changas, List<Pair<Changa, Inscription>> inscriptions) {
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
        return changaList;
    }

    @RequestMapping(value = "/filter")
    public ModelAndView filterChangas(@ModelAttribute("getLoggedUser") User loggedUser, @ModelAttribute("isUserLogged") boolean isUserLogged,
                                      @RequestParam(value = "cfilter", defaultValue = "") String categoryFilter, @RequestParam(value = "tfilter", defaultValue = "") String titleFilter) {
        ModelAndView model = new ModelAndView("index");
        Either<List<Changa>, Validation> changas = cs.getEmittedChangasFiltered(0, categoryFilter, titleFilter);
        if (!changas.isValuePresent()) {
            return new ModelAndView("redirect:/error").addObject("message", changas.getAlternative().getMessage());
        }

        model.addObject("isFiltered", true);
        if (!isUserLogged) {
            return model.addObject("changaList", changas.getValue());
        }
        Either<List<Pair<Changa, Inscription>>, Validation> maybeInscriptions = is.getOpenUserInscriptions(loggedUser.getUser_id());
        if (!maybeInscriptions.isValuePresent()) {
            return new ModelAndView("redirect:/error").addObject("message", maybeInscriptions.getAlternative().getMessage());
        }
        return model.addObject("changaList", getChangas(changas.getValue(), maybeInscriptions.getValue()));
    }

}
