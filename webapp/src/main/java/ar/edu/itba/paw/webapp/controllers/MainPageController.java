package ar.edu.itba.paw.webapp.controllers;

import ar.edu.itba.paw.interfaces.ChangaService;
import ar.edu.itba.paw.models.Changa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

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

    @RequestMapping("/create")
    public ModelAndView create(@RequestParam(value = "name") final String username) {
        final Changa u = cs.create(
                new Changa(
                        1232321,
                        "Limpiar el gato",
                        "Description",
                        123122.312,
                        "Palermo Hollywood"
                )
        );
        return new ModelAndView("redirect:/?changaId=" + u.getId());
    }

}
