package ar.edu.itba.paw.webapp.controllers;

import ar.edu.itba.paw.interfaces.services.ChangaService;
import ar.edu.itba.paw.interfaces.services.EmailService;
import ar.edu.itba.paw.interfaces.services.InscriptionService;
import ar.edu.itba.paw.interfaces.services.UserService;
import ar.edu.itba.paw.interfaces.util.Validation;
import ar.edu.itba.paw.models.*;
import ar.edu.itba.paw.webapp.forms.*;
import ar.edu.itba.paw.webapp.forms.ForgotPasswordForm;
import ar.edu.itba.paw.webapp.forms.ResetPasswordForm;
import ar.edu.itba.paw.models.*;
import ar.edu.itba.paw.webapp.forms.UserLoginForm;
import ar.edu.itba.paw.webapp.forms.UserRegisterForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.*;
import java.net.URI;

import static ar.edu.itba.paw.interfaces.util.Validation.EXPIRED_TOKEN;
import static ar.edu.itba.paw.interfaces.util.Validation.USER_ALREADY_EXISTS;

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

    @Autowired
    private MessageSource messageSource;

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
        if (!either.isValuePresent()) {
            if (either.getAlternative().compareTo(USER_ALREADY_EXISTS) == 0) {
                errors.rejectValue("email", "error.mailInUse", new Object[] {form.getEmail()}, "");
                return signUp(form);
            }
            // si cae aca seria muy extraño mas bien excepcional, que onda??
            return new ModelAndView("redirect:/error").addObject("message", either.getAlternative().getMessage());
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
            System.out.println("No se pudo inscribir en la changa pq:"+ val.getMessage());
            return new ModelAndView("redirect:/error").addObject("message", val.getMessage());
        }
        return new ModelAndView(new StringBuilder("redirect:/changa?id=").append(changaId).toString());
    }

    @RequestMapping(value = "/unjoin-changa", method = RequestMethod.POST)
    public ModelAndView unjoinChanga(@RequestParam("changaId") final long changaId, @ModelAttribute("getLoggedUser") User loggedUser) {
        Validation val = is.changeUserStateInChanga(loggedUser.getUser_id(), changaId, InscriptionState.optout);
        System.out.println(loggedUser.getEmail() + " desanotado de " + changaId);
        if (val.isOk()){
            System.out.println("user "+ loggedUser.getUser_id()+ " successfully desinscripto en changa "+ changaId);
        } else {
            //TODO JIME un popup de error
            System.out.println("No se pudo desinscribir en la changa pq:"+ val.getMessage());
            return new ModelAndView("redirect:/error").addObject("message", val.getMessage());
        }
        return new ModelAndView("redirect:/profile");
    }

    @RequestMapping(value = "/accept-user", method = RequestMethod.POST)
    public ModelAndView acceptUser(@RequestParam("changaId") final long changaId, @RequestParam("userId") final long userId) {
        Validation val = is.changeUserStateInChanga(userId, changaId, InscriptionState.accepted);
        if (val.isOk()){
            //TODO JIME popup preguntando
        } else {
            //TODO JIME un popup de error
            return new ModelAndView("redirect:/error").addObject("message", val.getMessage());
        }
        return new ModelAndView("redirect:/admin-changa?id=" + changaId);
    }

    @RequestMapping(value = "/reject-user", method = RequestMethod.POST)
    public ModelAndView rejectUser(@RequestParam("changaId") final long changaId, @RequestParam("userId") final long userId) {

        Validation val = is.changeUserStateInChanga(userId, changaId, InscriptionState.declined);
        if (val.isOk()){
            //TODO JIME popup preguntando
        } else {
            //TODO JIME un popup de error
            return new ModelAndView("redirect:/error").addObject("message", val.getMessage());
        }
        return new ModelAndView("redirect:/admin-changa?id=" + changaId);
    }

    @RequestMapping("/profile")
    public ModelAndView profile(@ModelAttribute("getLoggedUser") User loggedUser) {

        ModelAndView mav = new ModelAndView("indexProfile");
        Either<List<Pair<Changa, Inscription>>, Validation>  maybePendingChangas = is.getOpenUserInscriptions(loggedUser.getUser_id());
        if (maybePendingChangas.isValuePresent()){
            maybePendingChangas.getValue().removeIf(e -> e.getValue().getState() == InscriptionState.optout);
            mav.addObject("pendingChangas", maybePendingChangas.getValue());
        }
        else{
            return new ModelAndView("redirect:/error").addObject("message", maybePendingChangas.getAlternative().getMessage());
        }

        Either<List<Changa>, Validation> maybePublishedChangas = cs.getUserEmittedChangas(loggedUser.getUser_id());
        if (maybePublishedChangas.isValuePresent()){
            maybePublishedChangas.getValue().removeIf(e -> e.getState() == ChangaState.settled || e.getState() == ChangaState.closed || e.getState() == ChangaState.done);
            mav.addObject("publishedChangas", maybePublishedChangas.getValue());
        }
        else{
            return new ModelAndView("redirect:/error").addObject("message", maybePublishedChangas.getAlternative().getMessage());
        }

        return mav;
    }

    @RequestMapping("/login/forgot-password")
    public ModelAndView forgotPassword(@ModelAttribute("forgotPasswordForm") final ForgotPasswordForm forgotPasswordForm) {
        return new ModelAndView("indexForgotPassword");
    }

    @RequestMapping(value = "/login/forgot-password", method = RequestMethod.POST)
    public ModelAndView forgotPasswordSender(@Valid @ModelAttribute("forgotPasswordForm") final ForgotPasswordForm forgotPasswordForm, final BindingResult result, HttpServletRequest request) {
        if (result.hasErrors()) {
            return new ModelAndView("indexForgotPassword");
        }
        Either<User, Validation> user = us.findByMail(forgotPasswordForm.getMail());
        if (!user.isValuePresent()) {
            result.rejectValue("mail", "error.invalidMail", new Object[] {forgotPasswordForm.getMail()}, "");
            return forgotPassword(forgotPasswordForm);
        }
        try {
            ServletUriComponentsBuilder builder = ServletUriComponentsBuilder.fromContextPath(request);
            URI uri = builder.build().toUri();
            emailService.sendResetPasswordEmail(user.getValue(), uri.toString());
        } catch (MessagingException e) {
            return new ModelAndView("redirect:/500");
        }
        System.out.println(forgotPasswordForm.getMail());
        return new ModelAndView("redirect:/");
    }

    @RequestMapping("/reset-password/validate")
    public ModelAndView validateResetPassword( @RequestParam("id") long id, @RequestParam("token") String token) {
        Either<VerificationToken, Validation> verificationToken = us.getVerificationTokenWithRole(id, token);
        System.out.println("Token =" + verificationToken.toString());
        if (!verificationToken.isValuePresent()) {
            Validation errorCode = verificationToken.getAlternative();
            if (errorCode == EXPIRED_TOKEN) {
                //TODO RESEND EMAIL. REDIRECT A PAGINA PARA RESEND EMAIL
                return new ModelAndView("redirect:/login");
            } else {
                return new ModelAndView("redirect:/error").addObject("message", verificationToken.getAlternative().getMessage());
            }
        }
        return new ModelAndView("redirect:/reset-password").addObject("id", id);
    }

    @RequestMapping("/reset-password")
    public ModelAndView resetPassword(@RequestParam("id") long id, @ModelAttribute("resetPasswordForm") final ResetPasswordForm resetPasswordForm) {
        return new ModelAndView("indexResetPassword").addObject("id",id).addObject("actionUrl", "/reset-password");
    }

    @RequestMapping(value = "/reset-password", method = RequestMethod.POST)
    public ModelAndView doResetPassword(@Valid @ModelAttribute("resetPasswordForm") final ResetPasswordForm resetPasswordForm, final BindingResult result, @RequestParam("id") long id) {
        if (result.hasErrors()) {
            return resetPassword(id, resetPasswordForm);
        }
        us.resetPassword(id, resetPasswordForm.getNewPassword());
        System.out.println("Contraseña restablecida");
        SecurityContextHolder.getContext().setAuthentication(null);
        return new ModelAndView("redirect:/");
    }

    @RequestMapping(value = "/edit-profile")
    public ModelAndView editProfile(@ModelAttribute("getLoggedUser") User loggedUser, @ModelAttribute("userForm") final EditUserForm form) {
        form.setName(loggedUser.getName());
        form.setEmail(loggedUser.getEmail());
        form.setSurname(loggedUser.getSurname());
        form.setTelephone(loggedUser.getTel());
        return new ModelAndView("editProfileForm");
    }

    @RequestMapping(value = "/edit-profile", method = RequestMethod.POST )
    public ModelAndView editChanga(@Valid @ModelAttribute("userForm") final EditUserForm form, final BindingResult errors, @ModelAttribute("getLoggedUser") User loggedUser) {
        if (errors.hasErrors()) {
            return editProfile(loggedUser, form);
        }
        us.update(loggedUser.getUser_id(), new User.Builder()
                .withName(form.getName())
                .withSurname(form.getSurname())
                .withEmail(form.getEmail())
                .withTel(form.getTelephone()));
        return new ModelAndView("redirect:/profile");
    }

    @RequestMapping("/edit-password")
    public ModelAndView editPassword(@ModelAttribute("getLoggedUser") User loggedUser, @ModelAttribute("resetPasswordForm") final ResetPasswordForm resetPasswordForm) {
        return new ModelAndView("indexResetPassword").addObject("id", loggedUser.getUser_id()).addObject("actionUrl", "/edit-password");
    }

    @RequestMapping(value = "/edit-password", method = RequestMethod.POST)
    public ModelAndView editPassword(@Valid @ModelAttribute("resetPasswordForm") final ResetPasswordForm resetPasswordForm, final BindingResult result, @ModelAttribute("getLoggedUser") User loggedUser) {
        if (result.hasErrors()) {
            return editPassword(loggedUser, resetPasswordForm);
        }
        us.resetPassword(loggedUser.getUser_id(), resetPasswordForm.getNewPassword());
        System.out.println("Contraseña restablecida");
        return new ModelAndView("redirect:/");
    }
}
