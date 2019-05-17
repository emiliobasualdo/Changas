package ar.edu.itba.paw.webapp.controllers;

import ar.edu.itba.paw.interfaces.services.UserService;
import ar.edu.itba.paw.interfaces.util.Validation;
import ar.edu.itba.paw.models.Either;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.models.VerificationToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Controller
@Component
public class RegistrationController {

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/signup/registration-confirm", method = RequestMethod.GET)
    public ModelAndView confirmRegistration(WebRequest request, Model model, @RequestParam("token") String token, HttpServletResponse response) {
        Either<VerificationToken, Validation> verificationToken = userService.getVerificationToken(token);
        System.out.println(verificationToken.toString());
        if (!verificationToken.isValuePresent()) {
            Validation errorCode = verificationToken.getAlternative();
            if (errorCode == Validation.EXPIRED_TOKEN) {
                // String messageValue = messages.getMessage("auth.message.expired", null, locale)
                // model.addAttribute("message", messageValue);
                //TODO RESEND EMAIL. REDIRECT A PAGINA PARA RESEND EMAIL
                //return "redirect:/badUser.html?lang=" + locale.getLanguage();
                System.out.println("Token expired. Falta implementar el resend email");
                return new ModelAndView("redirect:/login");
            } else {
                response.setStatus(verificationToken.getAlternative().getHttpStatus().value());
                return new ModelAndView("redirect:/error").addObject("message", messageSource.getMessage(verificationToken.getAlternative().name(), null, LocaleContextHolder.getLocale()));
            }
        }
        Either<User, Validation> user = userService.findById(verificationToken.getValue().getUserId());
        if(user.isValuePresent() && user.getValue().isEnabled()){
                System.out.println("El mail esta verificado. Puede ingresar directamente");
                authWithoutPassword(user.getValue());
                return new ModelAndView("redirect:/");
        }
        System.out.println("se confirmó el mail");
        userService.setUserEnabledStatus(verificationToken.getValue().getUserId(), true);
        authWithoutPassword(user.getValue());
        return new ModelAndView("redirect:/");
    }

    private void authWithoutPassword(User user){
        List<GrantedAuthority> authorities = Arrays.asList( new SimpleGrantedAuthority("ROLE_USER"));
        Authentication auth = new UsernamePasswordAuthenticationToken(new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPasswd(), authorities) ,null, authorities);
        SecurityContextHolder.getContext().setAuthentication(auth);
    }
}
