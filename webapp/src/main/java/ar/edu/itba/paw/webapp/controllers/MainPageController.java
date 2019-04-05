package ar.edu.itba.paw.webapp.controllers;

import ar.edu.itba.paw.interfaces.services.ChangaService;
import ar.edu.itba.paw.models.Address;
import ar.edu.itba.paw.models.Changa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import java.time.LocalDateTime;

@Controller
public class MainPageController {

    @Autowired
    private ChangaService cs;

    @RequestMapping("/")
    public ModelAndView showChangas() {
        final ModelAndView mav = new ModelAndView("index");
        mav.addObject("changaList", cs.getChangas());
        return mav;
    }

    @RequestMapping("/changa")
    public ModelAndView showChanga (@RequestParam("id") final long id) {
        final ModelAndView mav = new ModelAndView("indexChanga");
        mav.addObject("changa", cs.getById(id));
        return mav;
    }

    @RequestMapping("/logIn")
    public ModelAndView showLogIn () {
        final ModelAndView mav = new ModelAndView("indexLogIn");
        return mav;
    }

    @RequestMapping("/signUp")
    public ModelAndView showSignUp () {
        final ModelAndView mav = new ModelAndView("indexSignUp");
        return mav;
    }


    @RequestMapping("/create")
    public ModelAndView create() {
        final Changa u = cs.create(
                new Changa.Builder()
                        .withUserId(1)
                        .withDescription("Limpiar el gato")
                        .withState("done")
                        .withTitle("en casa")
                        .withPrice(1231)
                        .atAddress(new Address("Calle", "San telmo", 22))
                        .createdAt(LocalDateTime.now())
                        .build()
        );
        return new ModelAndView("redirect:/?changaId=" + u.getChanga_id());
    }

}
