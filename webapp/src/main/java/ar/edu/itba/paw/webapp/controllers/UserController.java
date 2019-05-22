package ar.edu.itba.paw.webapp.controllers;

import ar.edu.itba.paw.interfaces.services.ChangaService;
import ar.edu.itba.paw.interfaces.services.EmailService;
import ar.edu.itba.paw.interfaces.services.InscriptionService;
import ar.edu.itba.paw.interfaces.services.UserService;
import ar.edu.itba.paw.interfaces.util.Validation;
import ar.edu.itba.paw.models.*;
import ar.edu.itba.paw.webapp.forms.*;
import ar.edu.itba.paw.webapp.forms.EmailForm;
import ar.edu.itba.paw.webapp.forms.ResetPasswordForm;
import ar.edu.itba.paw.webapp.forms.UserRegisterForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.*;
import java.net.URI;

import static ar.edu.itba.paw.interfaces.util.Validation.EMAIL_ERROR;
import static ar.edu.itba.paw.interfaces.util.Validation.EXPIRED_TOKEN;

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

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @RequestMapping("/signup")
    public ModelAndView signUp(@ModelAttribute("signUpForm") final UserRegisterForm form) {
        return new ModelAndView("indexSignUp");
    }

    @RequestMapping(value = "/signup", method = { RequestMethod.POST })
    public ModelAndView create(@Valid @ModelAttribute("signUpForm") final UserRegisterForm form, final BindingResult errors, HttpServletResponse response, HttpServletRequest request) {
        if (errors.hasErrors()) {

            System.out.println("Errores en los campos del formulario sign up");
            return signUp(form);
        }
        final Either<User, Validation> user = us.register(new User.Builder()
                .withName(form.getName())
                .withSurname(form.getSurname())
                .withTel(form.getTelephone())
                .withEmail(form.getEmail())
                .withPasswd(form.getPassword())
                );
        if (!user.isValuePresent()) {
            response.setStatus(user.getAlternative().getHttpStatus().value());
            return new ModelAndView("redirect:/error").addObject("message", messageSource.getMessage(user.getAlternative().name(), null, LocaleContextHolder.getLocale()));
        }
        ServletUriComponentsBuilder builder = ServletUriComponentsBuilder.fromContextPath(request);
        URI uri = builder.build().toUri();
        Validation emailValidation = emailService.sendMailConfirmationEmail(user.getValue(), uri.toString());
        if(emailValidation == EMAIL_ERROR) {
           System.out.println("email error");
           return new ModelAndView("redirect:/500");
        }
        return new ModelAndView("redirect:/login");
    }

    @RequestMapping("/login")
    public ModelAndView logIn(@ModelAttribute("emailForm")EmailForm form) {
        return new ModelAndView("indexLogIn");
    }

    @RequestMapping("/login/error")
    public ModelAndView logInError(@ModelAttribute("emailForm")EmailForm form) {
        return new ModelAndView("indexLogIn");
    }

    @RequestMapping("/profile")
    public ModelAndView profile(@ModelAttribute("getLoggedUser") User loggedUser, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView("indexProfile");
        Either<List<Pair<Changa, Inscription>>, Validation>  maybePendingChangas = is.getOpenUserInscriptions(loggedUser.getUser_id());
        if (!maybePendingChangas.isValuePresent()) {
           return redirectToErrorPage(response, maybePendingChangas.getAlternative());
        }
        maybePendingChangas.getValue().removeIf(e -> e.getValue().getState() == InscriptionState.optout); //TODO poner esto en la query
        mav.addObject("pendingChangas", maybePendingChangas.getValue());
        Either<List<Changa>, Validation> maybePublishedChangas = cs.getUserOpenChangas(loggedUser.getUser_id());
        if (!maybePublishedChangas.isValuePresent()) {
           return redirectToErrorPage(response, maybePublishedChangas.getAlternative());
        }
        mav.addObject("publishedChangas", maybePublishedChangas.getValue());
//        /*Either<String, Validation> urlImage = .... ;*/
        mav.addObject("urlImage", "/img/nieve1.jpg");
        return mav;
    }

    private ModelAndView redirectToErrorPage(HttpServletResponse response, Validation validation) {
        response.setStatus(validation.getHttpStatus().value());
        return new ModelAndView("redirect:/error").addObject("message", messageSource.getMessage(validation.name(), null,LocaleContextHolder.getLocale()));
    }

    @RequestMapping("/login/forgot-password")
    public ModelAndView forgotPassword(@ModelAttribute("forgotPasswordForm") final EmailForm forgotPasswordForm) {
        return new ModelAndView("indexForgotPassword");
    }

    @RequestMapping(value = "/login/forgot-password", method = RequestMethod.POST)
    public ModelAndView forgotPasswordSender(@Valid @ModelAttribute("forgotPasswordForm") final EmailForm forgotPasswordForm, final BindingResult result, HttpServletRequest request) {
        if (result.hasErrors()) {
            return forgotPassword(forgotPasswordForm);
        }
        Either<User, Validation> user = us.findByMail(forgotPasswordForm.getMail());
        if (!user.isValuePresent()) { //TODO: por seguirdad es mejor no mostrar este mensaje de error?
            result.rejectValue("mail", "error.invalidMail", new Object[] {forgotPasswordForm.getMail()}, "");
            return forgotPassword(forgotPasswordForm);
        }
        ServletUriComponentsBuilder builder = ServletUriComponentsBuilder.fromContextPath(request);
        URI uri = builder.build().toUri();
        Validation emailVal = emailService.sendResetPasswordEmail(user.getValue(), uri.toString());
        if(emailVal == EMAIL_ERROR) {
            return new ModelAndView("redirect:/500");
        }
        System.out.println(forgotPasswordForm.getMail());
        return new ModelAndView("redirect:/");
    }


    @RequestMapping("/reset-password/validate")
    public ModelAndView validateResetPassword( @RequestParam("id") long id, @RequestParam("token") String token, HttpServletResponse response) {
        Either<VerificationToken, Validation> verificationToken = us.getVerificationTokenWithRole(id, token);
        System.out.println("Token =" + verificationToken.toString());
        if (!verificationToken.isValuePresent()) {
            Validation errorCode = verificationToken.getAlternative();
            if (errorCode == EXPIRED_TOKEN) {
                //TODO RESEND EMAIL. REDIRECT A PAGINA PARA RESEND EMAIL
                return new ModelAndView("redirect:/login");
            } else {
                response.setStatus(verificationToken.getAlternative().getHttpStatus().value());
                return new ModelAndView("redirect:/error").addObject("message", messageSource.getMessage(verificationToken.getAlternative().name(), null, LocaleContextHolder.getLocale()));
            }
        }
        return new ModelAndView("redirect:/reset-password").addObject("id", id);
    }

    @RequestMapping("/reset-password")
    public ModelAndView resetPassword(@RequestParam("id") long id, @ModelAttribute("resetPasswordForm") final ResetPasswordForm resetPasswordForm) {
        return new ModelAndView("indexResetPassword").addObject("id", id).addObject("actionUrl", "/reset-password");
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
    public ModelAndView editProfile(@ModelAttribute("getLoggedUser") User loggedUser, @ModelAttribute("editUserForm") final EditUserForm form) {
        form.setName(loggedUser.getName());
        form.setSurname(loggedUser.getSurname());
        form.setTelephone(loggedUser.getTel());
        return new ModelAndView("editProfileForm");
    }

    @RequestMapping(value = "/edit-profile", method = RequestMethod.POST )
    public ModelAndView editChanga(@Valid @ModelAttribute("editUserForm") final EditUserForm form, final BindingResult errors, @ModelAttribute("getLoggedUser") User loggedUser) {
        if (errors.hasErrors()) {
            return editProfile(loggedUser, form);
        }
        us.update(loggedUser.getUser_id(), new User.Builder()
                .withName(form.getName())
                .withSurname(form.getSurname())
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

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public ModelAndView upload(@RequestParam("file") MultipartFile file) {
        return new ModelAndView("uploadedImage").addObject("file", file);
    }

}
