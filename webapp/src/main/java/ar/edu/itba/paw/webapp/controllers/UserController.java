package ar.edu.itba.paw.webapp.controllers;

import ar.edu.itba.paw.interfaces.services.ChangaService;
import ar.edu.itba.paw.interfaces.services.EmailService;
import ar.edu.itba.paw.interfaces.services.InscriptionService;
import ar.edu.itba.paw.interfaces.services.UserService;
import ar.edu.itba.paw.interfaces.util.Validation;
import ar.edu.itba.paw.models.*;
import ar.edu.itba.paw.webapp.forms.UserLoginForm;
import ar.edu.itba.paw.webapp.forms.UserRegisterForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.net.URI;

@Controller
public class UserController {

    @Autowired
    EmailService emailService;

    @Autowired
    private UserService us;

    @Autowired
    private InscriptionService is;

    @Autowired
    private ChangaService cs;

    @Autowired
    ApplicationEventPublisher eventPublisher;



    @RequestMapping("/signup")
    public ModelAndView signUp(@ModelAttribute("signUpForm") final UserRegisterForm form) {
        return new ModelAndView("indexSignUp");
    }

    @RequestMapping(value = "/signup", method = { RequestMethod.POST })
    public ModelAndView create(@Valid @ModelAttribute("signUpForm") final UserRegisterForm form, final BindingResult errors, final WebRequest request) {
        if (errors.hasErrors()) {
            System.out.println("Errores en los campos del formulario sign up");
            return signUp(form);
        }

        final Either<User, Validation> either = us.register(new User.Builder()
                .withName(form.getName())
                .withSurname(form.getSurname())
                .withTel(form.getTelephone())
                .withEmail(form.getEmail())
                .withPasswd(form.getPassword())
                );

        if (!either.isValuePresent()){
            Validation err = either.getAlternative();
            errors.rejectValue("email","aca no se q va");
            return signUp(form);
        }

        try {
            String appUrl = request.getContextPath();
            ServletUriComponentsBuilder builder = ServletUriComponentsBuilder.fromCurrentRequestUri();
            builder.scheme("http");
            URI uri = builder.build().toUri();
            emailService.sendMailConfirmationEmail(either.getValue(), uri.toString());
        } catch (javax.mail.MessagingException ex) {
            System.out.println("email error");
            return new ModelAndView("emailError", "user", form);
        }


        /* TODO redirect sin pasar por el login
         return new ModelAndView("redirect:/user?userId=" + either.getValue().getId());
        */
        return new ModelAndView("redirect:/login");
    }

    @RequestMapping("/login")
    public ModelAndView logIn(@ModelAttribute("UserLoginForm") final UserLoginForm form) {
        return new ModelAndView("indexLogIn");
    }

    @RequestMapping(value = "/join-changa", method = RequestMethod.POST)  //TODO: DEBERIA ESSTAR EN CHANGACONTROLLER NO?
    public ModelAndView showChanga(@RequestParam("changaId") final long changaId, @ModelAttribute("getLoggedUser") User loggedUser) {
        System.out.println("current user id: " + loggedUser.getUser_id());
        Validation val = is.inscribeInChanga(loggedUser.getUser_id(), changaId);
        if (val.isOk()){
            System.out.println("user "+ loggedUser.getUser_id()+ " successfully inscripto en changa "+ changaId);
            //TODO hacer validaciones
            Changa changa = cs.getChangaById(changaId).getValue();
            User changaOwner = us.findById(changa.getUser_id()).getValue();
            emailService.sendJoinRequestEmail(changa, changaOwner, loggedUser);
        } else {
            //TODO JIME un popup de error
            System.out.println("No se pudo inscribir en la changa pq:"+ val.getMessage());
        }
        return new ModelAndView("redirect:/");
    }

    @RequestMapping(value = "/unjoin-changa", method = RequestMethod.POST)
    public ModelAndView unjoinChanga(@RequestParam("changaId") final long changaId, HttpSession session) {
        User loggedUser = ((User)session.getAttribute("getLoggedUser"));
        Validation val = is.changeUserStateInChanga(loggedUser.getUser_id(), changaId, InscriptionState.optout);
        System.out.println(loggedUser.getEmail() + " desanotado de " + changaId);
        if (val.isOk()){
            System.out.println("user "+ loggedUser.getUser_id()+ " successfully desinscripto en changa "+ changaId);
        } else {
            //TODO JIME un popup de error
            System.out.println("No se pudo desinscribir en la changa pq:"+ val.getMessage());
        }
        return new ModelAndView("redirect:/profile");
    }

    @RequestMapping(value = "/accept-user", method = RequestMethod.POST)
    public ModelAndView acceptUser(@RequestParam("changaId") final long changaId, @RequestParam("userId") final long userId, HttpSession session) {

        Validation val = is.changeUserStateInChanga(userId, changaId, InscriptionState.accepted);
        if (val.isOk()){
            //TODO JIME popup preguntando
        } else {
            //TODO JIME un popup de error
        }
        return new ModelAndView("redirect:/admin-changa?id=" + changaId);
    }

    @RequestMapping(value = "/reject-user", method = RequestMethod.POST)
    public ModelAndView rejectUser(@RequestParam("changaId") final long changaId, @RequestParam("userId") final long userId, HttpSession session) {

        Validation val = is.changeUserStateInChanga(userId, changaId, InscriptionState.declined);
        if (val.isOk()){
            //TODO JIME popup preguntando
        } else {
            //TODO JIME un popup de error
        }
        return new ModelAndView("redirect:/admin-changa?id=" + changaId);
    }

    @RequestMapping("/profile")
    public ModelAndView profile(@ModelAttribute("getLoggedUser") User loggedUser) {

        Either<List<Pair<Changa, Inscription>>, Validation>  maybePendingChangas = is.getUserInscriptions(loggedUser.getUser_id());
        List<Pair<Changa, Inscription>> pendingChangas = new LinkedList<>();
        if (maybePendingChangas.isValuePresent()){
            pendingChangas = maybePendingChangas.getValue();
            //todo: deberiamos chequear q el mapa tenga todos valores validos? osea q ningun key/value sea null. porq el jsp se puede romper si le paso algo null
        }
        else{
            //TODO error
        }

        Either<List<Changa>, Validation> maybePublishedChangas = cs.getUserOwnedChangas(loggedUser.getUser_id());
        List<Changa> publishedChangas = null;
        if (maybePublishedChangas.isValuePresent()){
            publishedChangas = maybePublishedChangas.getValue();
        }
        else{
            //TODO error
        }

        return new ModelAndView("indexProfile")
                .addObject("publishedChangas", publishedChangas)
                .addObject("pendingChangas", pendingChangas);
    }

}
