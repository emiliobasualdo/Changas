package ar.edu.itba.paw.webapp.controllers;

import ar.edu.itba.paw.interfaces.services.ChangaService;
import ar.edu.itba.paw.interfaces.services.InscriptionService;
import ar.edu.itba.paw.interfaces.services.UserService;
import ar.edu.itba.paw.interfaces.util.Validation;
import ar.edu.itba.paw.models.Changa;
import ar.edu.itba.paw.models.ChangaState;
import ar.edu.itba.paw.models.Either;
import ar.edu.itba.paw.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class AdminController {

    @Autowired
    private ChangaService cs;

    @Autowired
    private UserService us;

    @Autowired
    private InscriptionService is;

    @RequestMapping("/administrator")
    public ModelAndView showAdminChanga(@ModelAttribute("getLoggedUser") User loggedUser) {
        // aca obtenemos todas las changas reportadas
        // para probar:
        Either<List<Changa>, Validation> maybeReportedChangas = cs.getAllChangas();
        if (maybeReportedChangas.isValuePresent()){
            maybeReportedChangas.getValue().removeIf(e -> e.getState() == ChangaState.settled || e.getState() == ChangaState.done || e.getState() == ChangaState.closed);
            return new ModelAndView("indexAdministrator").addObject("reportedChangas", maybeReportedChangas.getValue());
        }
        return new ModelAndView("redirect:/error").addObject("message", maybeReportedChangas.getAlternative().getMessage());
    }
}
