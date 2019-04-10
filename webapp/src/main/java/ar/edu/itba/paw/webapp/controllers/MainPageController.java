package ar.edu.itba.paw.webapp.controllers;

import ar.edu.itba.paw.interfaces.services.ChangaService;
import ar.edu.itba.paw.interfaces.util.Validation;
import ar.edu.itba.paw.models.Changa;
import ar.edu.itba.paw.models.Either;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class MainPageController {

    @Autowired
    private ChangaService cs;

    @RequestMapping(value = "/")
    public ModelAndView showChangas() {
        Either<List<Changa>, Validation> either = cs.getAllChangas();
        if (either.isValuePresent()) {
            return new ModelAndView("index")
                    .addObject("changaList", either.getValue());
        }
        else
            return new ModelAndView("500");
    }

}
