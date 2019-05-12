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
        if (errors.hasErrors()) {
            return createChanga(form);
        }
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
        if (!changaEither.isValuePresent()) return new ModelAndView("redirect:/error").addObject("message", changaEither.getAlternative().getMessage());
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
       if (errors.hasErrors()) {
           return editChanga(id, form);
       }
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
    public ModelAndView showChanga(@RequestParam("id") final long id, @ModelAttribute("getLoggedUser") User loggedUser, @ModelAttribute("isUserLogged") boolean isUserLogged) {
        final ModelAndView mav = new ModelAndView("indexChanga");
        final Changa changa = cs.getChangaById(id).getValue();
        mav.addObject("changa", changa);
        boolean userAlreadyInscribedInChanga = false;
        boolean userOwnsChanga = false;
        if (isUserLogged) {
            if (loggedUser.getUser_id() == changa.getUser_id()) {
                userOwnsChanga = true;
            } else {
                Either<Boolean, Validation> either = is.isUserInscribedInChanga(loggedUser.getUser_id(), id);
                if (!either.isValuePresent()) return new ModelAndView("redirect:/error").addObject("message", either.getAlternative().getMessage());
                userAlreadyInscribedInChanga = either.getValue();
            }
        }
        mav.addObject("userAlreadyInscribedInChanga", userAlreadyInscribedInChanga);
        mav.addObject("changaOwner", us.findById(changa.getUser_id()).getValue());
        mav.addObject("userOwnsChanga", userOwnsChanga);
        return mav;
    }

    @RequestMapping("/admin-changa")
    public ModelAndView showAdminChanga(@RequestParam("id") final long id, @ModelAttribute("getLoggedUser") User loggedUser) {
        final ModelAndView mav = new ModelAndView("indexAdminChanga");
        final Changa changa = cs.getChangaById(id).getValue();
        mav.addObject("changa", changa);
        mav.addObject("changaOwner", us.findById(changa.getUser_id()).getValue());
        Either<List<Pair<User, Inscription>>, Validation> inscribedUsers = is.getInscribedUsers(id);
        if (!inscribedUsers.isValuePresent()) return new ModelAndView("redirect:/error").addObject("message", inscribedUsers.getAlternative().getMessage());
        mav.addObject("notInscribedUsers", inscribedUsers.getValue().isEmpty());
        mav.addObject("inscribedUsers", inscribedUsers.getValue());
        return mav;
    }

    @RequestMapping(value = "/delete-changa", method = RequestMethod.POST)
    public ModelAndView deleteChanga(@RequestParam("changaId") final long changaId, HttpSession session) {
        // TODO JIME popup confirmacion (estás seguro?)
        Either<Changa, Validation> err = cs.changeChangaState(changaId, ChangaState.closed);
        if (!err.isValuePresent()) return new ModelAndView("redirect:/error").addObject("message", err.getAlternative().getMessage());
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
