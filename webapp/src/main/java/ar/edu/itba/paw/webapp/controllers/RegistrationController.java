package ar.edu.itba.paw.webapp.controllers;

import ar.edu.itba.paw.interfaces.services.UserService;
import ar.edu.itba.paw.interfaces.util.Validation;
import ar.edu.itba.paw.models.Either;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.models.VerificationToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.WebRequest;

import java.util.Calendar;
import java.util.Locale;

@Controller
@Component
public class RegistrationController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/signup/registration-confirm", method = RequestMethod.GET)
    public String confirmRegistration(WebRequest request, Model model, @RequestParam("token") String token) {
        Locale locale = request.getLocale();
        Either<VerificationToken, Validation> verificationToken = userService.getVerificationToken(token);
        System.out.println(verificationToken.toString());
        if (!verificationToken.isValuePresent()) {
            Validation.ErrorCodes errorCode = verificationToken.getAlternative().getEc();
            if ( errorCode == Validation.ErrorCodes.INEXISTENT_TOKEN)   {
                System.out.println("Inexistent token");
                // String message = messages.getMessage("auth.message.invalidToken", null, locale);
                // model.addAttribute("message", message);
                // return "redirect:/badUser.html?lang=" + locale.getLanguage();
                return "redirect:/login";
            }
            if (errorCode == Validation.ErrorCodes.EXPIRED_TOKEN) {
                // String messageValue = messages.getMessage("auth.message.expired", null, locale)
                // model.addAttribute("message", messageValue);
                //TODO RESEND EMAIL. REDIRECT A PAGINA PARA RESEND EMAIL
                //return "redirect:/badUser.html?lang=" + locale.getLanguage();
                System.out.println("Token expired. Falta implementar el resend email");
                return "redirect:/login";
            }
        }
        Either<User, Validation> user = userService.findById(verificationToken.getValue().getUserId());
        if(user.isValuePresent() && user.getValue().isEnabled()){
                System.out.println("El mail esta verificado. Puede ingresar directamente");
                return "redirect:/login";
        }

        System.out.println("se confirmó el mail");
        userService.setUserEnabledStatus(verificationToken.getValue().getUserId(), true);
        return "redirect:/login";
    }

}
