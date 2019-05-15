package ar.edu.itba.paw.webapp.controllers;

import ar.edu.itba.paw.interfaces.services.CategoryService;
import ar.edu.itba.paw.interfaces.services.ChangaService;
import ar.edu.itba.paw.interfaces.services.InscriptionService;
import ar.edu.itba.paw.interfaces.services.UserService;
import ar.edu.itba.paw.interfaces.util.Validation;
import ar.edu.itba.paw.models.*;
import ar.edu.itba.paw.webapp.forms.ChangaForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
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

    @Autowired
    private CategoryService catService;

    @RequestMapping(value = "/create-changa")
    public ModelAndView createChanga(@ModelAttribute("changaForm") final ChangaForm form) {
        return new ModelAndView("issueChangaForm")
                .addObject("categories", catService.getCategories(LocaleContextHolder.getLocale()));
    }

    @RequestMapping(value = "/create-changa", method = RequestMethod.POST )
    public ModelAndView createChanga(@Valid @ModelAttribute("changaForm") final ChangaForm form, final BindingResult errors, @ModelAttribute("getLoggedUser") User loggedUser) {
        System.out.println(form.getTitle() + " " +  form.getDescription() + " " +  form.getPrice() + " " +  form.getNeighborhood());
        if (errors.hasErrors()) {
            return createChanga(form);
        }
        Either<Changa, Validation> changa = cs.create(new Changa.Builder().withUserId(loggedUser.getUser_id())
                .withDescription(form.getDescription())
                .withTitle(form.getTitle())
                .withPrice(form.getPrice())
                .atAddress(form.getStreet(), form.getNeighborhood(), form.getNumber())
                .createdAt(LocalDateTime.now())
        );
        if (!changa.isValuePresent()) return new ModelAndView("redirect:/error").addObject("message", changa.getAlternative().getMessage());
        return new ModelAndView("redirect:/changa").addObject("id", changa.getValue().getChanga_id());
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
        form.setCategory(changa.getCategory());
        return new ModelAndView("editChangaForm")
                .addObject("id", id)
                .addObject("categories", catService.getCategories(LocaleContextHolder.getLocale()));
    }

    @RequestMapping(value = "/edit-changa", method = RequestMethod.POST )
    public ModelAndView editChanga(@RequestParam("id") final long id, @Valid @ModelAttribute("changaForm") final ChangaForm form, final BindingResult errors, @ModelAttribute("getLoggedUser") User loggedUser) {
       if (errors.hasErrors()) {
           return editChanga(id, form);
       }
        Either<Changa, Validation> eitherUpdatedChanga = cs.update(id, new Changa.Builder().withUserId(loggedUser.getUser_id())
                .withDescription(form.getDescription())
                .withTitle(form.getTitle())
                .withPrice(form.getPrice())
                .atAddress(form.getStreet(), form.getNeighborhood(), form.getNumber())
        );

       //if(eitherUpdatedChanga.isValuePresent()) {
           return new ModelAndView("redirect:/profile");
      // }
       //if(eitherUpdatedChanga.getAlternative().getMessage() == UNAUTHORIZED)
    }

    @RequestMapping("/changa")
    public ModelAndView showChanga(@RequestParam("id") long id, @ModelAttribute("getLoggedUser") User loggedUser, @ModelAttribute("isUserLogged") boolean isUserLogged) {
        ModelAndView mav = new ModelAndView("indexChanga");
        Either<Changa, Validation> changa = this.cs.getChangaById(id);
        if (!changa.isValuePresent()) return new ModelAndView("redirect:/error").addObject("message", changa.getAlternative().getMessage());
        mav.addObject("changa", changa.getValue());
        boolean userAlreadyInscribedInChanga = false;
        if (isUserLogged) {
            if (loggedUser.getUser_id() == changa.getValue().getUser_id()) {
//                mav.addObject("changaOwner", loggedUser);
//                mav.addObject("userOwnsChanga", true);
//                mav.addObject("userAlreadyInscribedInChanga", false);
//                return mav;
                return new ModelAndView("forward:/admin-changa").addObject("id", id);
            } else {
                Either<Boolean, Validation> isUserInscribedInChanga = this.is.isUserInscribedInChanga(loggedUser.getUser_id(), id);
                if (!isUserInscribedInChanga.isValuePresent()) return new ModelAndView("redirect:/error").addObject("message", isUserInscribedInChanga.getAlternative().getMessage());
                if (isUserInscribedInChanga.getValue()) {
                    userAlreadyInscribedInChanga = true;
                    Either<Inscription, Validation> inscriptionState = is.getInscription(loggedUser.getUser_id(), id);
                    if (!inscriptionState.isValuePresent()) return new ModelAndView("redirect:/error").addObject("message", inscriptionState.getAlternative().getMessage());
                    mav.addObject("inscriptionState", inscriptionState.getValue().getState());
                }
            }
        }
        Either<User, Validation> changaOwner = us.findById(changa.getValue().getUser_id());
        if (!changaOwner.isValuePresent()) return new ModelAndView("redirect:/error").addObject("message", changaOwner.getAlternative().getMessage());
        mav.addObject("changaOwner", changaOwner.getValue());
        mav.addObject("userAlreadyInscribedInChanga", userAlreadyInscribedInChanga);
        mav.addObject("userOwnsChanga", false);
        return mav;
    }

    @RequestMapping("/admin-changa")
    public ModelAndView showAdminChanga(@RequestParam("id") final long id, @ModelAttribute("getLoggedUser") User loggedUser) {
        final ModelAndView mav = new ModelAndView("indexAdminChanga");
        final Either<Changa, Validation> changa = cs.getChangaById(id);
        if (!changa.isValuePresent())  return new ModelAndView("redirect:/error").addObject("message", changa.getAlternative().getMessage());
        mav.addObject("changa", changa.getValue());
        mav.addObject("changaOwner", us.findById(changa.getValue().getUser_id()).getValue());
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
