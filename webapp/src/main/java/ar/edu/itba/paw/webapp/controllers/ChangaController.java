package ar.edu.itba.paw.webapp.controllers;

import ar.edu.itba.paw.interfaces.services.ChangaService;
import ar.edu.itba.paw.interfaces.services.InscriptionService;
import ar.edu.itba.paw.interfaces.services.UserService;
import ar.edu.itba.paw.interfaces.util.Validation;
import ar.edu.itba.paw.models.*;
import ar.edu.itba.paw.webapp.forms.ChangaForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.*;

@Controller
public class ChangaController {

    @Autowired
    private ChangaService cs;

    @Autowired
    private UserService us;

    @Autowired
    private InscriptionService is;

    @RequestMapping(value = "/create-changa")
    public ModelAndView createChanga(@ModelAttribute("changaForm") final ChangaForm form) {
        return new ModelAndView("issueChangaForm");
    }

    @RequestMapping(value = "/create-changa", method = RequestMethod.POST )
    public ModelAndView createChanga(@Valid @ModelAttribute("changaForm") final ChangaForm form, final BindingResult errors, @ModelAttribute("getLoggedUser") User loggedUser) {
        System.out.println(form.getTitle() + " " +  form.getDescription() + " " +  form.getPrice() + " " +  form.getNeighborhood());
        cs.create(new Changa.Builder().withUserId(loggedUser.getUser_id())
                .withDescription(form.getDescription())
                .withTitle(form.getTitle())
                .withPrice(form.getPrice())
                .atAddress(form.getStreet(), form.getNeighborhood(), form.getNumber())
                .createdAt(LocalDateTime.now())
        );
        return new ModelAndView("redirect:/");
    }

    @RequestMapping(value = "/edit-changa")
    public ModelAndView editChanga(@RequestParam("id") final long id, @ModelAttribute("changaForm") final ChangaForm form) {
        Either<Changa, Validation> changaEither = cs.getChangaById(id);
        if (!changaEither.isValuePresent()) {
            //todo error
            //nunca estariamos en este caso igual, ver como solucionar
            return new ModelAndView("redirect:/");
        }
        Changa changa = changaEither.getValue();
        form.setTitle(changa.getTitle());
        form.setStreet(changa.getStreet());
        form.setPrice(changa.getPrice());
        form.setNumber(changa.getNumber());
        form.setNeighborhood(changa.getNeighborhood());
        form.setDescription(changa.getDescription());
        return new ModelAndView("editChangaForm")
                .addObject("id", id);
    }

    @RequestMapping(value = "/edit-changa", method = RequestMethod.POST )
    public ModelAndView editChanga(@RequestParam("id") final long id, @Valid @ModelAttribute("changaForm") final ChangaForm form, final BindingResult errors, @ModelAttribute("getLoggedUser") User loggedUser) {
        cs.update(id, new Changa.Builder().withUserId(loggedUser.getUser_id())
                .withDescription(form.getDescription())
                .withTitle(form.getTitle())
                .withPrice(form.getPrice())
                .atAddress(form.getStreet(), form.getNeighborhood(), form.getNumber())
                .createdAt(LocalDateTime.now())
        );
        return new ModelAndView("redirect:/profile");
    }

    @RequestMapping("/changa")
    public ModelAndView showChanga(@RequestParam("id") final long id, @ModelAttribute("getLoggedUser") User loggedUser, @ModelAttribute("isUserLogged") boolean isUserLogged) { //TODO: RE VER
        final ModelAndView mav = new ModelAndView("indexChanga");

        Either<Changa, Validation> maybeChanga = cs.getChangaById(id);
        if (maybeChanga.isValuePresent()){
            mav.addObject("changa", maybeChanga.getValue());

            boolean userOwnsChanga = false;
            if (isUserLogged) {
                if (loggedUser.getUser_id() == maybeChanga.getValue().getUser_id()) {
                    userOwnsChanga = true;
                }
            }
            mav.addObject("userOwnsChanga", userOwnsChanga);

            Either<User, Validation> maybeChangaOwner = us.findById(maybeChanga.getValue().getUser_id());
            if (maybeChangaOwner.isValuePresent()){
                mav.addObject("changaOwner", maybeChangaOwner.getValue());
            }
            else {
                return new ModelAndView("500");
            }
        }
        else {
            return new ModelAndView("500");
        }

        if (isUserLogged) {
            Either<Boolean, Validation> either = is.isUserInscribedInChanga(loggedUser.getUser_id(), id);
            if (either.isValuePresent()) {
                mav.addObject("userAlreadyInscribedInChanga", either.getValue());
            } else {
                return new ModelAndView("500");
            }
            Either<Inscription, Validation> maybeInscription = is.getInscription(loggedUser.getUser_id(), id);
            if (maybeInscription.isValuePresent()){
                mav.addObject("inscriptionState", maybeInscription.getValue().getState());
            }
            else {
                return new ModelAndView("500");
            }
        }
        return mav;
    }

    @RequestMapping("/admin-changa")
    public ModelAndView showAdminChanga(@RequestParam("id") final long id) {
        final ModelAndView mav = new ModelAndView("indexAdminChanga");
        final Changa changa = cs.getChangaById(id).getValue();
        mav.addObject("changa", changa);
        mav.addObject("changaOwner", us.findById(changa.getUser_id()).getValue());
        Either<List<Pair<User, Inscription>>, Validation>  either = is.getInscribedUsers(id);
        if (either.isValuePresent()) {
            either.getValue().removeIf(e -> e.getValue().getState() == InscriptionState.optout);
            mav.addObject("notInscribedUsers", either.getValue().isEmpty());
            mav.addObject("inscribedUsers", either.getValue());
        } else {
            //todo pasarlo al error controller
            return new ModelAndView("500");
        }
        return mav;
    }

    @RequestMapping(value = "/delete-changa", method = RequestMethod.POST)
    public ModelAndView deleteChanga(@RequestParam("changaId") final long changaId, HttpSession session) {
        // TODO JIME popup confirmacion (estás seguro?)
        Either<Changa, Validation> err = cs.changeChangaState(changaId, ChangaState.closed);
        if (err.isValuePresent()){
            // TODO JIME popup salió todo joya
        } else {
            //TODO JIME popup error
        }
        return new ModelAndView("redirect:/profile");
    }

    @RequestMapping(value = "/close-changa", method = RequestMethod.POST)
    public ModelAndView closeChanga(@RequestParam("changaId") final long changaId, HttpSession session) {
        //Validation val = cs.changeState;
        /*if (val.isOk()){
            // TODO JIME popup confirmacion
        } else {
            //TODO JIME popup error
        }*/
        return new ModelAndView("redirect:/profile");
    }
}
