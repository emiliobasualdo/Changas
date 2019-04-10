package ar.edu.itba.paw.webapp.controllers;

import ar.edu.itba.paw.interfaces.services.ChangaService;
import ar.edu.itba.paw.interfaces.services.InscriptionService;
import ar.edu.itba.paw.interfaces.services.UserService;
import ar.edu.itba.paw.models.Changa;
import ar.edu.itba.paw.models.DBChangaState;
import ar.edu.itba.paw.models.Inscription;
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
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

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
            System.out.println(form.getTitle() + " " +  form.getDescription() + " " +  form.getPrice() + " " +  form.getNeighborhood());
            cs.create(new Changa.Builder().withUserId(UserController.currentUser.getUser_id())
                    .withDescription(form.getDescription())
                    .withTitle(form.getTitle())
                    .withPrice(form.getPrice())
                    .atAddress(form.getStreet(), form.getNeighborhood(), form.getNumber())
                    .withState(DBChangaState.emitted.name())
                    .createdAt(LocalDateTime.now())
                    .build()
            );
            return new ModelAndView("redirect:/");
        }
        return new ModelAndView("redirect:/signUp");
    }

    @RequestMapping("/changa")
    public ModelAndView showChanga(@RequestParam("id") final long id) {
        final ModelAndView mav = new ModelAndView("indexChanga");
        final Changa changa = cs.getById(id).getValue();
        mav.addObject("changa", changa);
        if(UserController.currentUser == null) {
            return new ModelAndView("redirect:/logIn");
        }
        Boolean alreadyInscribed = is.isUserInscribedInChanga(UserController.currentUser.getUser_id(), id);
        mav.addObject("userAlreadyInscribedInChanga", alreadyInscribed);
        mav.addObject("changaOwner", us.findById(changa.getUser_id()).getValue());
        return mav;
    }

    @RequestMapping("/admin-changa")
    public ModelAndView showAdminChanga(@RequestParam("id") final long id) {
        final ModelAndView mav = new ModelAndView("indexAdminChanga");
        final Changa changa = cs.getById(id).getValue();
        mav.addObject("changa", changa);
        mav.addObject("changaOwner", us.findById(changa.getUser_id()).getValue());
        List<Pair<User, Inscription>> inscribedUsersPair = is.getInscribedUsers(id);
        List<User> inscribedUsers = new ArrayList<>();
        /*for (Pair<User, Inscription> pair : inscribedUsersPair ){
            inscribedUsers.add(pair.getKey());
            System.out.println(pair.getKey().getName());
        }*/
        inscribedUsers.add(new User.Builder().withName("p").withSurname("p").withEmail("p").withPasswd("p").withTel("1").build());
        inscribedUsers.add(new User.Builder().withName("pp").withSurname("pp").withEmail("pp").withPasswd("pp").withTel("11").build());
        inscribedUsers.add(new User.Builder().withName("ppp").withSurname("ppp").withEmail("ppp").withPasswd("ppp").withTel("111").build());
        inscribedUsers.add(new User.Builder().withName("pppp").withSurname("pppp").withEmail("pppp").withPasswd("pppp").withTel("1111").build());

        mav.addObject("inscribedUsers", inscribedUsers);
        mav.addObject("alreadyInscribedUsers", inscribedUsers.isEmpty());
        return mav;
    }

}
