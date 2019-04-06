package ar.edu.itba.paw.webapp.controllers;

import ar.edu.itba.paw.interfaces.services.ChangaService;
import ar.edu.itba.paw.interfaces.services.UserService;
import ar.edu.itba.paw.models.Changa;
import ar.edu.itba.paw.webapp.forms.ChangaForm;
import ar.edu.itba.paw.webapp.forms.UserForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import javax.validation.Valid;

@Controller
public class MainPageController {

    @Autowired
    private ChangaService cs;

    @RequestMapping(value = "/")
    public ModelAndView showChangas(@ModelAttribute("changaForm") final ChangaForm form) {
        return new ModelAndView("index").addObject("changaList", cs.getChangas());
    }

    @RequestMapping("/changa")
    public ModelAndView showChanga (@RequestParam("id") final long id) {
        final ModelAndView mav = new ModelAndView("indexChanga");
        mav.addObject("changa", cs.getById(id));
        return mav;
    }

    @RequestMapping("/logIn")
    public ModelAndView showLogIn () {
        return new ModelAndView("indexLogIn");
    }

    @RequestMapping("/findByUserId") // todo borrar, es un mapping de prueba
    public ModelAndView findByUserId (@RequestParam int user_id) {
        return new ModelAndView("index").addObject("changaList", cs.findByUserId(user_id));
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST ) // todo no se tendría que poder hacer sin estar logeado
    public ModelAndView createChanga(@Valid @ModelAttribute("changaForm") final ChangaForm form, final BindingResult errors) {
        System.out.println(form.getTitle() + " " +  form.getDescription() + " " +  form.getPrice() + " " +  form.getNeighborhood());
//        cs.create(new Changa.Builder()
//                .withDescription(form.getDescription())
//                .withTitle(form.getTitle())
//                .withPrice(form.getPrice())
//                .atAddress("Calle", form.getNeighborhood(), 22) // todo falta
//                //.createdAt(LocalDateTime.now())
//                .build()
//        );
        return new ModelAndView("index").addObject("changaList", cs.getChangas());
    }






    @Autowired
    private UserService us;

    @RequestMapping("/signUp")
    public ModelAndView signUp(@ModelAttribute("signUpForm") final UserForm form) {
        return new ModelAndView("indexSignUp");
    }


    @RequestMapping(value = "/createUser", method = { RequestMethod.POST })
    public ModelAndView create(@Valid @ModelAttribute("signUpForm") final UserForm form, final BindingResult errors) {
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

}
