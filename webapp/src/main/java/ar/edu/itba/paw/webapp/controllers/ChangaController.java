package ar.edu.itba.paw.webapp.controllers;

import ar.edu.itba.paw.interfaces.services.ChangaService;
import ar.edu.itba.paw.webapp.forms.ChangaForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
public class ChangaController {

    @Autowired
    private ChangaService cs;

    @RequestMapping(value = "/createChanga")
    public ModelAndView showChangas(@ModelAttribute("changaForm") final ChangaForm form) {
        return new ModelAndView("issueChangaForm");
    }

    @RequestMapping(value = "/createChanga", method = RequestMethod.POST ) // todo no se tendría que poder hacer sin estar logeado
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

}
