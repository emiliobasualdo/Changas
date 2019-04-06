package ar.edu.itba.paw.webapp.controllers;

import ar.edu.itba.paw.interfaces.services.UserService;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.webapp.forms.UserLoginForm;
import ar.edu.itba.paw.webapp.forms.UserRegisterForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
public class UserController {

    @Autowired
    private UserService us;

    public static User currentUser;

    @RequestMapping("/signUp")
    public ModelAndView signUp(@ModelAttribute("signUpForm") final UserRegisterForm form) {
        return new ModelAndView("indexSignUp");
    }

    @RequestMapping(value = "/createUser", method = { RequestMethod.POST })
    public ModelAndView create(@Valid @ModelAttribute("signUpForm") final UserRegisterForm form, final BindingResult errors) {
        System.out.println(form.toString());
//        if (errors.hasErrors()) {
//            return signUp(form);
//        }
        //final Either<User, ValidationError> either = us.create(form.getUsername(), form.getPassword(), form.getName(), form.getSurname(), form.getPhone());

//        if (!either.isValuePresent()){
//            int code = either.getAlternative().getCode();
//            if (code == INVALID_USERNAME.getId()){
//                //TODO ver q va en el errorCode de abajo
//                errors.rejectValue("username","aca no se q va");
//                return signUp(form);
//            } else if (code == DATABASE_ERROR.getId()) {
//                //TODO return de una vista de error en la base de datos
//            }
//        }
       // return new ModelAndView("redirect:/user?userId=" + either.getValue().getId());
        return new ModelAndView("indexLogIn");
    }

    @RequestMapping("/logIn")
    public ModelAndView logIn(@ModelAttribute("UserLoginForm") final UserLoginForm form) {
        return new ModelAndView("indexLogIn");
    }

    @RequestMapping(value = "/logIn", method = RequestMethod.POST)
    public ModelAndView logIn(@ModelAttribute("UserLoginForm") final UserLoginForm form, final BindingResult errors) {
        System.out.println(form.toString());
        currentUser = us.logIn(new User.Builder()
                .withEmail(form.getUsername())
                .withPasswd(form.getPassword())
                .build());
        return new ModelAndView("redirect:/");
    }
}
