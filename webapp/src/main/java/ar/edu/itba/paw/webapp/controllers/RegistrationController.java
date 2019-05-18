package ar.edu.itba.paw.webapp.controllers;

import ar.edu.itba.paw.interfaces.services.EmailService;
import ar.edu.itba.paw.interfaces.services.UserService;
import ar.edu.itba.paw.interfaces.util.Validation;
import ar.edu.itba.paw.models.Either;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.models.UserTokenState;
import ar.edu.itba.paw.models.VerificationToken;
import ar.edu.itba.paw.webapp.forms.ResendEmailVerificationForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.configurers.provisioning.UserDetailsManagerConfigurer;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetails.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import static ar.edu.itba.paw.interfaces.util.Validation.*;
import static ar.edu.itba.paw.models.UserTokenState.*;

@Controller
@Component
public class RegistrationController {

    @Autowired
    private UserService userService;

    @Autowired
    private EmailService emailService;

    @RequestMapping(value = "/signup/registration-confirm", method = RequestMethod.GET)
    public ModelAndView confirmRegistration(WebRequest request, Model model, @RequestParam("token") String token) {
        //Se busca en la DB al token pasado en la url
        Either<VerificationToken, Validation> verificationToken = userService.getVerificationToken(token);
        if(!verificationToken.isValuePresent()) { //inexistent token
            System.out.println("inexistent token");
            return new ModelAndView("redirect:/error").addObject("message", verificationToken.getAlternative().getMessage());
        }

        Either<UserTokenState, Validation> userTokenState = userService.getUserTokenState(verificationToken.getValue());
        if(!userTokenState.isValuePresent()) {
            System.out.println("inexistent user");
            return new ModelAndView("redirect:/error").addObject("message", userTokenState.getAlternative().getMessage());
        }
        if(userTokenState.getValue() == USER_DISABLED_EXPIRED_TOKEN) {
            //resend email verification
            System.out.println("user disabled expired token");
            ServletUriComponentsBuilder builder = ServletUriComponentsBuilder.fromCurrentRequestUri();
            builder.scheme("http");
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
