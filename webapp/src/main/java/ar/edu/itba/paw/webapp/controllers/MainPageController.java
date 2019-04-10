package ar.edu.itba.paw.webapp.controllers;

import ar.edu.itba.paw.interfaces.services.ChangaService;
import ar.edu.itba.paw.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MainPageController {

    @Autowired
    private ChangaService cs;


    @ModelAttribute("currentUser")
    public User getCurrentUser() {
        return UserController.currentUser;
    }

    @RequestMapping(value = "/")
    public ModelAndView showChangas() {
        return new ModelAndView("index")
                .addObject("changaList", cs.getChangas());
    }

    /*@RequestMapping("/findByUserId") // todo borrar, es un mapping de prueba
    public ModelAndView findByUserId (@RequestParam int user_id) {
        return new ModelAndView("index").addObject("changaList", cs.findByUserId(user_id));
    }*/

}
