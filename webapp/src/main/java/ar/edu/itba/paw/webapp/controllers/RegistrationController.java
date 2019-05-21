package ar.edu.itba.paw.webapp.controllers;

import ar.edu.itba.paw.interfaces.services.EmailService;
import ar.edu.itba.paw.interfaces.services.UserService;
import ar.edu.itba.paw.interfaces.util.Validation;
import ar.edu.itba.paw.models.Either;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.models.UserTokenState;
import ar.edu.itba.paw.models.VerificationToken;
import ar.edu.itba.paw.webapp.forms.EmailForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URI;
import java.util.Arrays;
import java.util.List;

import static ar.edu.itba.paw.interfaces.util.Validation.*;
import static ar.edu.itba.paw.models.UserTokenState.*;

@Controller
@Component
public class RegistrationController {

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private UserService userService;

    @Autowired
    private EmailService emailService;

    @RequestMapping(value = "/registration-confirm", method = RequestMethod.GET)
    public ModelAndView confirmRegistration(HttpServletRequest request, Model model, @RequestParam("token") String token, HttpServletResponse response) {
        //Se busca en la DB al token pasado en la url
        Either<VerificationToken, Validation> verificationToken = userService.getVerificationToken(token);
        if(!verificationToken.isValuePresent()) { //inexistent token
            System.out.println("inexistent token");
            return new ModelAndView("redirect:/error").addObject("message", verificationToken.getAlternative().getMessage());
        }

        Either<UserTokenState, Validation> userTokenState = userService.getUserTokenState(verificationToken.getValue());
        if(!userTokenState.isValuePresent()) {
            System.out.println("inexistent user");
            response.setStatus(verificationToken.getAlternative().getHttpStatus().value());
            return new ModelAndView("redirect:/error").addObject("message", messageSource.getMessage(verificationToken.getAlternative().name(), null, LocaleContextHolder.getLocale()));
        }
        if(userTokenState.getValue() == USER_DISABLED_EXPIRED_TOKEN) {
            //resend email verification
            System.out.println("user disabled expired token");
            ServletUriComponentsBuilder builder = ServletUriComponentsBuilder.fromContextPath(request);
            URI uri = builder.build().toUri();
            return new ModelAndView("indexResendEmailVerification").addObject("token", token).addObject("uri", uri);
        }
        if(userTokenState.getValue() == USER_ENABLED_EXPIRED_TOKEN) {
            //El usuario no pude entrar directo a su cuenta usando el token
            System.out.println("user enabled expired token");
            return new ModelAndView("redirect:/login");
        }
        Either<User, Validation> user = userService.findById(verificationToken.getValue().getUserId());
        if(userTokenState.getValue() == USER_ENABLED_VALID_TOKEN) {
            System.out.println("user enabled, valid token. Puede ingresar directo con el token");
            authWithoutPassword(user.getValue());
            return new ModelAndView("redirect:/");
        }
        System.out.println("user disabled valid token");

        //En este punto, el usuario no está enabled y el token es válido. Se activa entonces al usuario y se lo redirije a su cuenta
        userService.setUserEnabledStatus(verificationToken.getValue().getUserId(), true);
        authWithoutPassword(user.getValue());
        return new ModelAndView("redirect:/");
    }

    private void authWithoutPassword(User user){
        List<GrantedAuthority> authorities = Arrays.asList( new SimpleGrantedAuthority("ROLE_USER"));
        Authentication auth = new UsernamePasswordAuthenticationToken(new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPasswd(), authorities) ,null, authorities);
        SecurityContextHolder.getContext().setAuthentication(auth);
    }

    @RequestMapping(value = "/login/resend-email-verification/")
    public ModelAndView resendEmailVerification(@ModelAttribute("emailForm")EmailForm form) {
        return new ModelAndView("indexLogIn");
    }

    @RequestMapping(value = "/login/resend-email-verification", method = RequestMethod.POST)
    public ModelAndView doResendEmailVerification(@ModelAttribute("emailForm")EmailForm form, final BindingResult result, HttpServletRequest request) {
        if (result.hasErrors()) {
            return resendEmailVerification(form);
        }
        Either<User, Validation> user = userService.findByMail(form.getMail());
        if (!user.isValuePresent()) { //TODO: por seguirdad es mejor no mostrar este mensaje de error?
            result.rejectValue("mail", "error.invalidMail", new Object[] {form.getMail()}, "");
            return resendEmailVerification(form);
        } else if (user.getValue().isEnabled()) {
            result.rejectValue("mail", "error.enabledMail", new Object[] {form.getMail()}, "");
            return resendEmailVerification(form);
        }
        ServletUriComponentsBuilder builder = ServletUriComponentsBuilder.fromContextPath(request);
        URI uri = builder.build().toUri();
        Validation emailValidation = emailService.sendMailConfirmationEmail(user.getValue(), uri.toString());
        return new ModelAndView("redirect:/");
    }

    @RequestMapping(value = "/signup/resend-email-verification", method = RequestMethod.POST)
    public ModelAndView resendEmailVerification(HttpServletRequest request, @RequestParam("token") String existingToken, @RequestParam("uri") String uri) {
        Validation emailValidation = emailService.resendMailConfirmationEmail(existingToken, uri);
        if(emailValidation == EMAIL_ERROR) {
            System.out.println("email error");
            return new ModelAndView("redirect:/500");
        }
        return new ModelAndView("redirect:/login");
    }

}
