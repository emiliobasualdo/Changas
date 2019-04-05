package ar.edu.itba.paw.webapp.controllers;

import ar.edu.itba.paw.interfaces.services.UserService;
import ar.edu.itba.paw.interfaces.util.ValidationError;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.util.Either;
import ar.edu.itba.paw.webapp.form.UserForm;
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
        return new ModelAndView("signUp");
    }


    @RequestMapping(value = "/createUser", method = { RequestMethod.POST })
    public ModelAndView create(@Valid @ModelAttribute("registerForm") final UserForm form, final BindingResult errors) {
        if (errors.hasErrors()) {
            return signUp(form);
        }
        final Either<User, ValidationError> user = us.create(form.getUsername(), form.getPassword(), form.getName(), form.getSurname(), form.getPhone());

        if (!user.isValuePresent()){
            int code = user.getAlternative().getCode();
            if (code == INVALID_USERNAME.getId()){
                //TODO ver q va en el errorCode de abajo
                errors.rejectValue("username","aca no se q va");
                return signUp(form);
            } else if (code == DATABASE_ERROR.getId()) {
                //TODO return de una vista de error en la base de datos
            }
        }


        return new ModelAndView("redirect:/user?userId=" + user.getValue().getId());
    }

}
