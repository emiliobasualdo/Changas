package ar.edu.itba.paw.webapp.controllers;

import ar.edu.itba.paw.interfaces.services.UserService;
import ar.edu.itba.paw.interfaces.util.ValidationError;
import ar.edu.itba.paw.models.Either;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.webapp.forms.UserForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

import static ar.edu.itba.paw.interfaces.util.ErrorCodes.*;

@Controller
public class SignUpController {

    @Autowired
    private UserService us;

    @RequestMapping("/signUp")
    public ModelAndView signUp(@ModelAttribute("signUpForm") final UserForm form) {
        return new ModelAndView("indexSignUp");
    }


    @RequestMapping(value = "/createUser", method = { RequestMethod.POST })
    public ModelAndView create(@Valid @ModelAttribute("signUpForm") final UserForm form, final BindingResult errors) {

        if (errors.hasErrors()) {
            System.out.println("ERRORS");
            return signUp(form);
        }


        final Either<User, ValidationError> either = us.register(new User.Builder()
                .withName(form.getName())
                .withSurname(form.getSurname())
                .withTel(form.getTelephone())
                .withMail(form.getEmail())
                .withPasswd(form.getPassword())
                .build());

        if (!either.isValuePresent()){
            int code = either.getAlternative().getCode();
            //no me dejo hacer un switch pq blabla pero ver como hacerlo mas lindo
            if (code == INVALID_MAIL.getId()){
                //TODO ver q va en el errorCode de abajo
                //TODO sacarle la E a email en todos lados
                errors.rejectValue("email","aca no se q va");
                return signUp(form);
            } else if (code == DATABASE_ERROR.getId()) {
                //TODO que hacemo aca
            }
        }

        /* TODO redirect sin pasar por login
        return new ModelAndView("redirect:/user?userId=" + either.getValue().getId());
        */

        return new ModelAndView("indexLogIn");
    }

}
