package ar.edu.itba.paw.webapp.controllers;

import ar.edu.itba.paw.Builder;
import ar.edu.itba.paw.interfaces.services.ChangaService;
import ar.edu.itba.paw.interfaces.services.InscriptionService;
import ar.edu.itba.paw.interfaces.services.UserService;
import ar.edu.itba.paw.interfaces.util.Validation;
import ar.edu.itba.paw.models.*;
import ar.edu.itba.paw.webapp.forms.SearchForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
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
        ArrayList<String> categorias = new ArrayList<>();
        categorias.add("categoria 1");
        categorias.add("categoria 2");
        categorias.add("categoria 3");
        if (maybeChangas.isValuePresent()) {
            maybeChangas.getValue().removeIf(e -> e.getState() == ChangaState.settled || e.getState() == ChangaState.done || e.getState() == ChangaState.closed); //TODO: hacer esto en una query
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
                            .addObject("changaList", changaList)
                            .addObject("categories", categorias);
                } else {
                    return new ModelAndView("redirect:/error").addObject("message", maybeInscriptions.getAlternative().getMessage());
                }
            } else {
                return new ModelAndView("index")
                        .addObject("changaList", maybeChangas.getValue())
                        .addObject("categories", categorias);
            }
        } else {
            return new ModelAndView("redirect:/error").addObject("message", maybeChangas.getAlternative().getMessage());
        }
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public ModelAndView showSearch(@Valid @ModelAttribute("signUpForm") final SearchForm form, @ModelAttribute("getLoggedUser") User loggedUser, @ModelAttribute("isUserLogged") boolean isUserLogged) {
        /* aca obtenemos las changas segun la busqueda */
        System.out.println("Search= " + form.getSearch() + " and category= " + form.getCategory());
        return showChangas(/*changas buscadas, */loggedUser, isUserLogged);     /* todo: metodo q muestre changas dado una lista*/
    }
}
