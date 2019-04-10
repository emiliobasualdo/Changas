package ar.edu.itba.paw.webapp.controllers;

import ar.edu.itba.paw.interfaces.services.InscriptionService;
import ar.edu.itba.paw.interfaces.services.ChangaService;
import ar.edu.itba.paw.interfaces.services.UserService;
import ar.edu.itba.paw.interfaces.util.Validation;
import ar.edu.itba.paw.models.Either;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.webapp.forms.UserLoginForm;
import ar.edu.itba.paw.webapp.forms.UserRegisterForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

import static ar.edu.itba.paw.interfaces.util.Validation.ErrorCodes.INVALID_MAIL;

@Controller
public class UserController {

    @Autowired
    private UserService us;

    @Autowired
    private InscriptionService is;

    // todo: esto es muy villero. ya lo voy a borrar. es solo para probar mostrar las changas en el profile
    @Autowired
    private ChangaService cs;

    public static User currentUser;

    @ModelAttribute("currentUser")
    public User getCurrentUser() {
        return currentUser;
    }

    @RequestMapping("/signUp")
    public ModelAndView signUp(@ModelAttribute("signUpForm") final UserRegisterForm form) {
        return new ModelAndView("indexSignUp");
    }

    @RequestMapping(value = "/createUser", method = { RequestMethod.POST })
    public ModelAndView create(@Valid @ModelAttribute("signUpForm") final UserRegisterForm form, final BindingResult errors) {
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
                .build());

        if (!either.isValuePresent()){
            Validation err = either.getAlternative();
            //no me dejo hacer un switch pq blabla pero ver como hacerlo mas lindo
            errors.rejectValue("email","aca no se q va");
            return signUp(form);

                //TODO MAITE ver q va en el errorCode de abajo
                //TODO MAITE sacarle la E a email en todos lados
//            else if (code == DATABASE_ERROR.getId()) {
//                //TODO MAITE que hacemo aca. preguntarle a Juan lo de las exceptions de la base de datos cuando violas un unique
//            }
        }

        /* TODO redirect sin pasar por el login
         return new ModelAndView("redirect:/user?userId=" + either.getValue().getId());
        */
        return new ModelAndView("redirect:/logIn");
    }

    @RequestMapping("/logIn")
    public ModelAndView logIn(@ModelAttribute("UserLoginForm") final UserLoginForm form) {
        return new ModelAndView("indexLogIn");
    }

    @RequestMapping(value = "/logIn", method = RequestMethod.POST)
    public ModelAndView logIn(@ModelAttribute("UserLoginForm") final UserLoginForm form, final BindingResult errors) {
        System.out.println(form.toString());
        Either<User, Validation> either = us.logIn(new User.Builder()
                .withEmail(form.getUsername())
                .withPasswd(form.getPassword())
                .build());
        if (!either.isValuePresent()) {
            System.out.println(either.getAlternative());
            return new ModelAndView("500");
        }
        currentUser = either.getValue();
        return new ModelAndView("redirect:/");
    }

    @RequestMapping(value = "/joinChanga", method = RequestMethod.POST)
    public ModelAndView showChanga(@RequestParam("changaId") final long changaId) {
        if (currentUser == null){ // todo maite, current user tiene que ser un Either
            //TODO hacer que se loggee y que despues se redirija a la changa que estaba viendo.
            return  new ModelAndView("redirect:/logIn");
        }

        System.out.println("current user id: "+currentUser.getUser_id());
        Validation val = is.inscribeInChanga(currentUser.getUser_id(), changaId);
        //TODO hacer que se deshabilite el boton Anotarme en changa cuando ya está inscripto
        if (val.isOk()){
            System.out.println("user "+ currentUser.getUser_id()+ " successfully inscripto en changa "+ changaId);
        } else {
            //TODO JIME un popup de error
            System.out.println("No se pudo inscribir en la changa pq:"+ val.getMessage());
        }



        return new ModelAndView("redirect:/");
    }


    @RequestMapping("/profile")
    public ModelAndView profile(@RequestParam int id){
        return new ModelAndView("indexProfile")
                .addObject("profile", us.findById(id).getValue())
                .addObject("publishedChangas", cs.getUserOwnedChangas(id).getValue())
                .addObject("pendingChangas", is.getUserInscriptions(id).getValue().keySet());
    }
}
