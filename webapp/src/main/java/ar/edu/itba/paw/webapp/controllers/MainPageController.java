package ar.edu.itba.paw.webapp.controllers;

import ar.edu.itba.paw.interfaces.services.ChangaService;
import ar.edu.itba.paw.models.Changa;
import ar.edu.itba.paw.webapp.forms.ChangaForm;
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
        final ModelAndView mav = new ModelAndView("indexLogIn");
        return mav;
    }

    @RequestMapping("/signUp")
    public ModelAndView showSignUp () {
        final ModelAndView mav = new ModelAndView("indexSignUp");
        return mav;
    }


    @RequestMapping(value = "/create", method = RequestMethod.POST )
    public ModelAndView createChanga(@Valid @ModelAttribute("changaForm") final ChangaForm form, final BindingResult errors) {
        System.out.println(form.getTitle() + " " +  form.getDescription() + " " +  form.getPrice() + " " +  form.getNeighborhood());
        cs.create(new Changa.Builder()
                .withUserId(1)
                .withDescription(form.getDescription())
                .withState("done")
                .withTitle(form.getTitle())
                .withPrice(form.getPrice())
                .atAddress("Calle", form.getNeighborhood(), 22)
                //.createdAt(LocalDateTime.now())
                .build()
        );
        return new ModelAndView("index").addObject("changaList", cs.getChangas());
    }

}
