package ar.edu.itba.paw.webapp.controllers;

import ar.edu.itba.paw.interfaces.services.ChangaService;
import ar.edu.itba.paw.interfaces.services.EmailService;
import ar.edu.itba.paw.interfaces.services.InscriptionService;
import ar.edu.itba.paw.interfaces.services.UserService;
import ar.edu.itba.paw.interfaces.util.Validation;
import ar.edu.itba.paw.models.*;
import ar.edu.itba.paw.webapp.forms.ForgotPasswordForm;
import ar.edu.itba.paw.webapp.forms.ResetPasswordForm;
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

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
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
        return new ModelAndView(new StringBuilder("redirect:/changa?id=").append(changaId).toString());
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

    @RequestMapping("/profile")
    public ModelAndView profile(@ModelAttribute("getLoggedUser") User loggedUser) {
        return new ModelAndView("indexProfile")
                .addObject("publishedChangas", cs.getUserOwnedChangas(loggedUser.getUser_id()).getValue())
                .addObject("pendingChangas", is.getUserInscriptions(loggedUser.getUser_id()).getValue().keySet());
    }

    @RequestMapping("/login/forgot-password")
    public ModelAndView forgotPassword(@ModelAttribute("forgotPasswordForm") final ForgotPasswordForm forgotPasswordForm) {
        return new ModelAndView("indexForgotPassword");
    }

    @RequestMapping(value = "/login/forgot-password", method = RequestMethod.POST)
    public ModelAndView forgotPasswordSender(@Valid @ModelAttribute("forgotPasswordForm") final ForgotPasswordForm forgotPasswordForm, final BindingResult result, final WebRequest request) {
        if (result.hasErrors()) {
            return new ModelAndView("indexForgotPassword");
        }
        Either<User, Validation> user = us.findByMail(forgotPasswordForm.getMail());
        if (!user.isValuePresent()) {
            System.out.println("User" + forgotPasswordForm.getMail() + "does not exists");
            return new ModelAndView("indexForgotPassword");
        }
        try {
            String appUrl = request.getContextPath();
            ServletUriComponentsBuilder builder = ServletUriComponentsBuilder.fromCurrentRequestUri();
            builder.scheme("http");
            URI uri = builder.build().toUri();
            emailService.sendResetPasswordEmail(user.getValue(), uri.toString());
        } catch (MessagingException e) {
            return new ModelAndView("redirect:/500");
        }
        System.out.println(forgotPasswordForm.getMail());
        return new ModelAndView("redirect:/");
    }

    @RequestMapping("/login/forgot-password/reset-password")
    public ModelAndView resetPassword( @RequestParam("id") long id, @RequestParam("token") String token, @ModelAttribute("resetPasswordForm") final ResetPasswordForm resetPasswordForm) {
        Either<VerificationToken, Validation> verificationToken = us.getVerificationToken(token);
        System.out.println("Token =" + verificationToken.toString());
        if (!verificationToken.isValuePresent()) {
            Validation.ErrorCodes errorCode = verificationToken.getAlternative().getEc();
            if ( errorCode == Validation.ErrorCodes.INEXISTENT_TOKEN)   {
                System.out.println("Inexistent token");
                // String message = messages.getMessage("auth.message.invalidToken", null, locale);
                // model.addAttribute("message", message);
                // return "redirect:/badUser.html?lang=" + locale.getLanguage();
                return new ModelAndView("redirect:/login");
            }
            if (errorCode == Validation.ErrorCodes.EXPIRED_TOKEN) {
                // String messageValue = messages.getMessage("auth.message.expired", null, locale)
                // model.addAttribute("message", messageValue);
                //TODO RESEND EMAIL. REDIRECT A PAGINA PARA RESEND EMAIL
                //return "redirect:/badUser.html?lang=" + locale.getLanguage();
                System.out.println("Token expired. Falta implementar el resend email");
                return new ModelAndView("redirect:/login");
            }
        }
        return new ModelAndView("indexResetPassword");
    }

    @RequestMapping(value = "/login/forgot-password/reset-password", method = RequestMethod.POST)
    public ModelAndView doResetPassword(@Valid @ModelAttribute("resetPasswordForm") final ResetPasswordForm resetPasswordForm, HttpServletRequest request) {
        System.out.println("Contraseña restablecida");
        return new ModelAndView("redirect:/");
    }

}
