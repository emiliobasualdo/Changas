package ar.edu.itba.paw.webapp.controllers;

import ar.edu.itba.paw.interfaces.services.UserService;
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
        System.out.println("entro a registration-confirm");
        Locale locale = request.getLocale();

        VerificationToken verificationToken = userService.getVerificationToken(token);
        System.out.println(verificationToken.toString());
        if (verificationToken == null) {
           // String message = messages.getMessage("auth.message.invalidToken", null, locale);
           // model.addAttribute("message", message);\

            System.out.println("no encontro al token");
            //TODO ERROR PAGE
           // return "redirect:/badUser.html?lang=" + locale.getLanguage();

        }

        User user = userService.findById(verificationToken.getUserId()).getValue();
        Calendar cal = Calendar.getInstance();
        if ((verificationToken.getExpiryDate().getTime() - cal.getTime().getTime()) <= 0) {
           // String messageValue = messages.getMessage("auth.message.expired", null, locale)
           // model.addAttribute("message", messageValue);
            //return "redirect:/badUser.html?lang=" + locale.getLanguage();
        }

        userService.setUserEnabledStatus(user, true);
//        return "redirect:/login.html?lang=" + request.getLocale().getLanguage();
    return "redirect:/login";
    }
}
