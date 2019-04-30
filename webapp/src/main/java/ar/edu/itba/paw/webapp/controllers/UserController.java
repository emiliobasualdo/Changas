package ar.edu.itba.paw.webapp.controllers;

import ar.edu.itba.paw.interfaces.services.ChangaService;
import ar.edu.itba.paw.interfaces.services.InscriptionService;
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

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
public class UserController {

    @Autowired
    private UserService us;

    @Autowired
    private InscriptionService is;

    // todo: esto es muy villero. ya lo voy a borrar. es solo para probar mostrar las changas en el profile
    @Autowired
    private ChangaService cs;

    @RequestMapping("/signup")
    public ModelAndView signUp(@ModelAttribute("signUpForm") final UserRegisterForm form) {
        return new ModelAndView("indexSignUp");
    }

    @RequestMapping(value = "/signup", method = { RequestMethod.POST })
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
                );

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
        return new ModelAndView("redirect:/login");
    }

    @RequestMapping("/login")
    public ModelAndView logIn(@ModelAttribute("UserLoginForm") final UserLoginForm form) {
        return new ModelAndView("indexLogIn");
    }

    @RequestMapping(value = "/join-changa", method = RequestMethod.POST)  //TODO: DEBERIA ESSTAR EN CHANGACONTROLLER NO?
    public ModelAndView showChanga(@RequestParam("changaId") final long changaId, @ModelAttribute("getLoggedUser") User loggedUser) {
        System.out.println("current user id: " + loggedUser.getUser_id());
        Validation val = is.inscribeInChanga(loggedUser.getUser_id(), changaId);
        if (val.isOk()){
            System.out.println("user "+ loggedUser.getUser_id()+ " successfully inscripto en changa "+ changaId);
        } else {
            //TODO JIME un popup de error
            System.out.println("No se pudo inscribir en la changa pq:"+ val.getMessage());
        }
        return new ModelAndView("redirect:/");
    }

    @RequestMapping(value = "/unjoin-changa", method = RequestMethod.POST)
    public ModelAndView unjoinChanga(@RequestParam("changaId") final long changaId, @ModelAttribute("getLoggedUser") User loggedUser) {
        Validation val = is.unsubscribeFromChanga(loggedUser.getUser_id(), changaId);
        System.out.println(loggedUser.getEmail() + " desanotado de " + changaId);
        if (val.isOk()){
            System.out.println("user "+ loggedUser.getUser_id()+ " successfully desinscripto en changa "+ changaId);
        } else {
            //TODO JIME un popup de error
            System.out.println("No se pudo desinscribir en la changa pq:"+ val.getMessage());
        }
        return new ModelAndView("redirect:/profile");
    }


    @RequestMapping("/profile")
    public ModelAndView profile(@ModelAttribute("getLoggedUser") User loggedUser) {
        return new ModelAndView("indexProfile")
                .addObject("publishedChangas", cs.getUserOwnedChangas(loggedUser.getUser_id()).getValue())
                .addObject("pendingChangas", is.getUserInscriptions(loggedUser.getUser_id()).getValue().keySet());
    }
}
