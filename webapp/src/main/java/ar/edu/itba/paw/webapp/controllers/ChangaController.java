package ar.edu.itba.paw.webapp.controllers;

import ar.edu.itba.paw.interfaces.services.ChangaService;
import ar.edu.itba.paw.interfaces.services.InscriptionService;
import ar.edu.itba.paw.interfaces.services.UserService;
import ar.edu.itba.paw.interfaces.util.Validation;
import ar.edu.itba.paw.models.Changa;
import ar.edu.itba.paw.models.Inscription;
import ar.edu.itba.paw.models.Either;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.webapp.forms.ChangaForm;
import javafx.util.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

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

    @ModelAttribute("currentUser")
    public User getCurrentUser() {
        return UserController.currentUser;
    }

    @RequestMapping(value = "/createChanga")
    public ModelAndView createChanga(@ModelAttribute("changaForm") final ChangaForm form) {
        return new ModelAndView("issueChangaForm");
    }

    @RequestMapping(value = "/createChanga", method = RequestMethod.POST ) // todo no se tendría que poder hacer sin estar logeado
    public ModelAndView createChanga(@Valid @ModelAttribute("changaForm") final ChangaForm form, final BindingResult errors) {
        if (UserController.currentUser != null) {
            cs.create(new Changa.Builder().withUserId(UserController.currentUser.getUser_id())
                    .withDescription(form.getDescription())
                    .withTitle(form.getTitle())
                    .withPrice(form.getPrice())
                    .atAddress(form.getStreet(), form.getNeighborhood(), form.getNumber())
                    .createdAt(LocalDateTime.now())
                    .build()
            );
            return new ModelAndView("redirect:/");
        }
        return new ModelAndView("redirect:/signUp");
    }

    /*@RequestMapping("/editChanga")
    public ModelAndView editChanga(@ModelAttribute("changaForm") final ChangaForm form){
        //final Changa currentChanga = cs.getChangaById()
    }*/

    @RequestMapping(value = "/editChanga", method = RequestMethod.POST ) // todo no se tendría que poder hacer sin estar logeado
    public ModelAndView editChanga(@Valid @ModelAttribute("changaForm") final ChangaForm form, final BindingResult errors) {
        if (UserController.currentUser != null) {
            cs.update(new Changa.Builder().withUserId(UserController.currentUser.getUser_id())
                    .withDescription(form.getDescription())
                    .withTitle(form.getTitle())
                    .withPrice(form.getPrice())
                    .atAddress(form.getStreet(), form.getNeighborhood(), form.getNumber())
                    .createdAt(LocalDateTime.now())
                    .build()
            );
            return new ModelAndView("redirect:/profile?id="+ getCurrentUser().getUser_id());
        }

        //no deberiamos nunca llegar acá, pero por las dudas de que esten jugando con la URL
        return new ModelAndView("redirect:/signUp");
    }

    @RequestMapping("/changa")
    public ModelAndView showChanga(@RequestParam("id") final long id) {
        final ModelAndView mav = new ModelAndView("indexChanga");
        final Changa changa = cs.getChangaById(id).getValue();
        mav.addObject("changa", changa);
        boolean userAlreadyInscribedInChanga = false;
        if(UserController.currentUser == null) {
            return new ModelAndView("redirect:/logIn");
        }
        Either<Boolean, Validation> either = is.isUserInscribedInChanga(UserController.currentUser, id);
        if (either.isValuePresent()) {
            userAlreadyInscribedInChanga = either.getValue();
        } else {
            // todo que carajo pasa aca?
            // el usuario podría no esxistir
            // La changa podría no existir
        }
        mav.addObject("userAlreadyInscribedInChanga", userAlreadyInscribedInChanga);
        mav.addObject("changaOwner", us.findById(changa.getUser_id()).getValue());
        return mav;
    }

    @RequestMapping("/admin-changa")
    public ModelAndView showAdminChanga(@RequestParam("id") final long id) {
        final ModelAndView mav = new ModelAndView("indexAdminChanga");
        final Changa changa = cs.getChangaById(id).getValue();
        mav.addObject("changa", changa);
        mav.addObject("changaOwner", us.findById(changa.getUser_id()).getValue());
        Either<Map<User, Inscription>, Validation> either = is.getInscribedUsers(id);
        Set<User> inscribedUsers = new HashSet<>();
        if (either.isValuePresent()) {
            Map<User, Inscription> map = either.getValue();
            inscribedUsers = map.keySet();
        } else {
            //todo
        }
        mav.addObject("notInscribedUsers", inscribedUsers.isEmpty());
        mav.addObject("inscribedUsers", inscribedUsers);
        return mav;
    }

}
