package ar.edu.itba.paw.webapp.controllers;

import ar.edu.itba.paw.interfaces.services.ChangaService;
import ar.edu.itba.paw.interfaces.services.InscriptionService;
import ar.edu.itba.paw.interfaces.services.UserService;
import ar.edu.itba.paw.interfaces.util.Validation;
import ar.edu.itba.paw.models.Changa;
import ar.edu.itba.paw.models.Either;
import ar.edu.itba.paw.models.Inscription;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.webapp.forms.ChangaForm;
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
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Controller
public class ChangaController {

    @Autowired
    private ChangaService cs;

    @Autowired
    private UserService us;

    @Autowired
    private InscriptionService is;

    @RequestMapping(value = "/createChanga")
    public ModelAndView createChanga(@ModelAttribute("changaForm") final ChangaForm form) {
        return new ModelAndView("issueChangaForm");
    }

    @RequestMapping(value = "/createChanga", method = RequestMethod.POST )
    public ModelAndView createChanga(@Valid @ModelAttribute("changaForm") final ChangaForm form, final BindingResult errors, HttpSession session) {
        System.out.println(form.getTitle() + " " +  form.getDescription() + " " +  form.getPrice() + " " +  form.getNeighborhood());
        cs.create(new Changa.Builder().withUserId(((User)session.getAttribute("getLoggedUser")).getUser_id())
                .withDescription(form.getDescription())
                .withTitle(form.getTitle())
                .withPrice(form.getPrice())
                .atAddress(form.getStreet(), form.getNeighborhood(), form.getNumber())
                .createdAt(LocalDateTime.now())
        );
        return new ModelAndView("redirect:/");
    }

    @RequestMapping(value = "/editChanga")
    public ModelAndView editChanga(@RequestParam("id") final long id, @ModelAttribute("changaForm") final ChangaForm form) {
        Either<Changa, Validation> changaEither = cs.getChangaById(id);
        if (!changaEither.isValuePresent()) {
            //todo error
            //nunca estariamos en este caso igual, ver como solucionar
            return new ModelAndView("redirect:/");
        }
        Changa changa = changaEither.getValue();
        form.setTitle(changa.getTitle());
        form.setStreet(changa.getStreet());
        form.setPrice(changa.getPrice());
        form.setNumber(changa.getNumber());
        form.setNeighborhood(changa.getNeighborhood());
        form.setDescription(changa.getDescription());
        return new ModelAndView("editChangaForm")
                .addObject("id", id);
    }

    @RequestMapping(value = "/editChanga", method = RequestMethod.POST )
    public ModelAndView editChanga(@RequestParam("id") final long id, @Valid @ModelAttribute("changaForm") final ChangaForm form, final BindingResult errors, HttpSession session) {
        cs.update(id, new Changa.Builder().withUserId(((User)session.getAttribute("getLoggedUser")).getUser_id())
                .withDescription(form.getDescription())
                .withTitle(form.getTitle())
                .withPrice(form.getPrice())
                .atAddress(form.getStreet(), form.getNeighborhood(), form.getNumber())
                .createdAt(LocalDateTime.now())
        );
        return new ModelAndView("redirect:/profile");
    }

    @RequestMapping("/changa")
    public ModelAndView showChanga(@RequestParam("id") final long id, HttpSession session) { //TODO: RE VER
        final ModelAndView mav = new ModelAndView("indexChanga");
        final Changa changa = cs.getChangaById(id).getValue();
        mav.addObject("changa", changa);
        boolean userAlreadyInscribedInChanga = false;
        Either<Boolean, Validation> either = is.isUserInscribedInChanga(((User)session.getAttribute("getLoggedUser")).getUser_id(), id);
        if (either.isValuePresent()) {
            userAlreadyInscribedInChanga = either.getValue();
        } else {
            // todo que carajo pasa aca?
            // el usuario podría no esxistir
            // La changa podría no existir
        }
        mav.addObject("userAlreadyInscribedInChanga", userAlreadyInscribedInChanga);
        mav.addObject("changaOwner", us.findById(changa.getUser_id()).getValue());
        return mav;
    }

    @RequestMapping("/admin-changa")
    public ModelAndView showAdminChanga(@RequestParam("id") final long id) {
        final ModelAndView mav = new ModelAndView("indexAdminChanga");
        final Changa changa = cs.getChangaById(id).getValue();
        mav.addObject("changa", changa);
        mav.addObject("changaOwner", us.findById(changa.getUser_id()).getValue());
        Either<Map<User, Inscription>, Validation> either = is.getInscribedUsers(id);
        Set<User> inscribedUsers = new HashSet<>();
        if (either.isValuePresent()) {
            Map<User, Inscription> map = either.getValue();
            inscribedUsers = map.keySet();
        } else {
            //todo
        }
        mav.addObject("notInscribedUsers", inscribedUsers.isEmpty());
        mav.addObject("inscribedUsers", inscribedUsers);
        return mav;
    }

}
