package ar.edu.itba.paw.webapp.controllers;

import ar.edu.itba.paw.interfaces.ChangaService;
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
    @RequestMapping("/changa")
    public ModelAndView showChanga (@RequestParam("id") final long id) {
        final ModelAndView mav = new ModelAndView("indexChanga");
        mav.addObject("changa", cs.getById(id));
        return mav;
    }

}
