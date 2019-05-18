package ar.edu.itba.paw.webapp.controllers;

import ar.edu.itba.paw.interfaces.services.*;
import ar.edu.itba.paw.interfaces.util.Validation;
import ar.edu.itba.paw.models.*;
import ar.edu.itba.paw.webapp.forms.ChangaForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.*;

@Controller
public class ChangaController {

    @Autowired
    EmailService emailService;

    @Autowired
    private ChangaService cs;

    @Autowired
    private UserService us;

    @Autowired
    private InscriptionService is;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private filtersService filtersService;

    @RequestMapping(value = "/create-changa")
    public ModelAndView createChanga(@ModelAttribute("changaForm") final ChangaForm form) {
        return new ModelAndView("issueChangaForm")
                .addObject("categories", filtersService.getCategories())
                .addObject("neighborhoods", filtersService.getNeighborhoods());
    }

    @RequestMapping(value = "/create-changa", method = RequestMethod.POST )
    public ModelAndView createChanga(@Valid @ModelAttribute("changaForm") final ChangaForm form, final BindingResult errors, @ModelAttribute("getLoggedUser") User loggedUser, HttpServletResponse response) {
        System.out.println(form.getTitle() + " " +  form.getDescription() + " " +  form.getPrice() + " " +  form.getNeighborhood());
        if (errors.hasErrors()) {
            return createChanga(form);
        }
        Either<Changa, Validation> changa = cs.create(new Changa.Builder()
                .withUserId(loggedUser.getUser_id())
                .withDescription(form.getDescription())
                .withTitle(form.getTitle())
                .withPrice(form.getPrice())
                .atAddress(form.getStreet(), form.getNeighborhood(), form.getNumber())
                .inCategory(form.getCategory())
        );

        if (!changa.isValuePresent()) {
            response.setStatus(changa.getAlternative().getHttpStatus().value());
            return new ModelAndView("redirect:/error").addObject("message", messageSource.getMessage(changa.getAlternative().name(), null, LocaleContextHolder.getLocale()));
        }
        response.setStatus(HttpServletResponse.SC_CREATED);
        return new ModelAndView("redirect:/changa").addObject("id", changa.getValue().getChanga_id());
    }

    @RequestMapping(value = "/edit-changa")
    public ModelAndView editChanga(@RequestParam("id") final long id, @ModelAttribute("changaForm") final ChangaForm form, @ModelAttribute("getLoggedUser") User loggedUser, HttpServletResponse response) {
        Either<Changa, Validation> changa = cs.getChangaById(id);
        if (!changa.isValuePresent()) {
            response.setStatus(changa.getAlternative().getHttpStatus().value());
            return new ModelAndView("redirect:/error").addObject("message", messageSource.getMessage(changa.getAlternative().name(), null,LocaleContextHolder.getLocale()));
        }
        if (changa.getValue().getUser_id() != loggedUser.getUser_id()) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return new ModelAndView("403");
        }
        form.setTitle(changa.getValue().getTitle());
        form.setStreet(changa.getValue().getStreet());
        form.setPrice(changa.getValue().getPrice());
        form.setNumber(changa.getValue().getNumber());
        form.setNeighborhood(changa.getValue().getNeighborhood());
        form.setDescription(changa.getValue().getDescription());
        form.setCategory(changa.getValue().getCategory());
        form.setCategory(changa.getValue().getCategory());
        return new ModelAndView("editChangaForm")
                .addObject("id", id)
                .addObject("neighborhoods", filtersService.getNeighborhoods())
                .addObject("categories", filtersService.getCategories());
    }

    @RequestMapping(value = "/edit-changa", method = RequestMethod.POST )
    public ModelAndView editChanga(@RequestParam("id") final long id, @Valid @ModelAttribute("changaForm") final ChangaForm form, final BindingResult errors, @ModelAttribute("getLoggedUser") User loggedUser, HttpServletResponse response) {
       if (errors.hasErrors()) {
           return editChanga(id, form, loggedUser, response);
       }
        Either<Changa, Validation> changa = cs.getChangaById(id);
        if (!changa.isValuePresent()) {
            response.setStatus(changa.getAlternative().getHttpStatus().value());
            return new ModelAndView("redirect:/error").addObject("message", messageSource.getMessage(changa.getAlternative().name(), null,LocaleContextHolder.getLocale()));
        }
        if (changa.getValue().getUser_id() != loggedUser.getUser_id()) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return new ModelAndView("403");
        }
        cs.update(id, new Changa.Builder().withUserId(loggedUser.getUser_id())
                .withDescription(form.getDescription())
                .withTitle(form.getTitle())
                .withPrice(form.getPrice())
                .withState(changa.getValue().getState())
                .inCategory(changa.getValue().getCategory())
                .atAddress(form.getStreet(), form.getNeighborhood(), form.getNumber())
        );
        return new ModelAndView("redirect:/profile");
    }

    @RequestMapping("/changa")
    public ModelAndView showChanga(@RequestParam("id") long id, @ModelAttribute("getLoggedUser") User loggedUser, @ModelAttribute("isUserLogged") boolean isUserLogged, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView("indexChanga");
        Either<Changa, Validation> changa = this.cs.getChangaById(id);
        if (!changa.isValuePresent()) {
            response.setStatus(changa.getAlternative().getHttpStatus().value());
            return new ModelAndView("redirect:/error").addObject("message", messageSource.getMessage(changa.getAlternative().name(), null,LocaleContextHolder.getLocale()));
        }
        mav.addObject("changa", changa.getValue());
        boolean userAlreadyInscribedInChanga = false;
        if (isUserLogged) {
            if (loggedUser.getUser_id() == changa.getValue().getUser_id()) {
                return new ModelAndView("forward:/admin-changa").addObject("id", id);
            } else {
                Either<Boolean, Validation> isUserInscribedInChanga = this.is.isUserInscribedInChanga(loggedUser.getUser_id(), id);
                if (!isUserInscribedInChanga.isValuePresent()) {
                    response.setStatus(isUserInscribedInChanga.getAlternative().getHttpStatus().value());
                    return new ModelAndView("redirect:/error").addObject("message", messageSource.getMessage(isUserInscribedInChanga.getAlternative().name(), null, LocaleContextHolder.getLocale()));
                }
                if (isUserInscribedInChanga.getValue()) {
                    userAlreadyInscribedInChanga = true;
                    Either<Inscription, Validation> inscriptionState = is.getInscription(loggedUser.getUser_id(), id);
                    if (!inscriptionState.isValuePresent()) {
                        response.setStatus(inscriptionState.getAlternative().getHttpStatus().value());
                        return new ModelAndView("redirect:/error").addObject("message", messageSource.getMessage(inscriptionState.getAlternative().name(), null, LocaleContextHolder.getLocale()));
                    }
                    mav.addObject("inscriptionState", inscriptionState.getValue().getState());
                }
            }
        }
        Either<User, Validation> changaOwner = us.findById(changa.getValue().getUser_id());
        if (!changaOwner.isValuePresent()) {
            response.setStatus(changaOwner.getAlternative().getHttpStatus().value());
            return new ModelAndView("redirect:/error").addObject("message", messageSource.getMessage(changaOwner.getAlternative().name(), null, LocaleContextHolder.getLocale()));
        }
        mav.addObject("changaOwner", changaOwner.getValue());
        mav.addObject("userAlreadyInscribedInChanga", userAlreadyInscribedInChanga);
        mav.addObject("userOwnsChanga", false);
        return mav;
    }

    @RequestMapping("/admin-changa")
    public ModelAndView showAdminChanga(@RequestParam("id") final long id, @ModelAttribute("getLoggedUser") User loggedUser, HttpServletResponse response) {
        final ModelAndView mav = new ModelAndView("indexAdminChanga");
        final Either<Changa, Validation> changa = cs.getChangaById(id);
        if (!changa.isValuePresent()) {
            response.setStatus(changa.getAlternative().getHttpStatus().value());
            return new ModelAndView("redirect:/error").addObject("message", messageSource.getMessage(changa.getAlternative().name(), null,LocaleContextHolder.getLocale()));
        }
        if (changa.getValue().getUser_id() != loggedUser.getUser_id()) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return new ModelAndView("403");
        }
        mav.addObject("changa", changa.getValue());
        mav.addObject("changaOwner", us.findById(changa.getValue().getUser_id()).getValue());
        Either<List<Pair<User, Inscription>>, Validation> inscribedUsers = is.getInscribedUsers(id);
        if (!inscribedUsers.isValuePresent()) {
            response.setStatus(inscribedUsers.getAlternative().getHttpStatus().value());
            return new ModelAndView("redirect:/error").addObject("message", messageSource.getMessage(inscribedUsers.getAlternative().name(), null, LocaleContextHolder.getLocale()));
        }
        mav.addObject("notInscribedUsers", inscribedUsers.getValue().isEmpty());
        inscribedUsers.getValue().removeIf(e -> e.getValue().getState() == InscriptionState.optout);
        mav.addObject("inscribedUsers", inscribedUsers.getValue());
        return mav;
    }

    @RequestMapping(value = "/delete-changa", method = RequestMethod.POST)
    public ModelAndView deleteChanga(@RequestParam("changaId") final long changaId, @ModelAttribute("getLoggedUser") User loggedUser, HttpServletResponse response) {
        Either<Changa, Validation> changa = cs.getChangaById(changaId);
        if (!changa.isValuePresent()) {
            response.setStatus(changa.getAlternative().getHttpStatus().value());
            new ModelAndView("redirect:/error").addObject("message", messageSource.getMessage(changa.getAlternative().name(), null,LocaleContextHolder.getLocale()));
        }
        if (changa.getValue().getUser_id() != loggedUser.getUser_id()) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return new ModelAndView("403");
        }
        // TODO JIME popup confirmacion (estás seguro?)
        Either<Changa, Validation> err = cs.changeChangaState(changaId, ChangaState.closed);
        if (!err.isValuePresent()) {
            response.setStatus(err.getAlternative().getHttpStatus().value());
            return new ModelAndView("redirect:/error").addObject("message", messageSource.getMessage(err.getAlternative().name(), null,LocaleContextHolder.getLocale()));
        }
        return new ModelAndView("redirect:/profile");
    }

    @RequestMapping(value = "/close-changa", method = RequestMethod.POST)
    public ModelAndView closeChanga(@RequestParam("changaId") final long changaId, @ModelAttribute("getLoggedUser") User loggedUser, HttpServletResponse response) {
        Either<Changa, Validation> changa = cs.getChangaById(changaId);
        if (!changa.isValuePresent()) {
            response.setStatus(changa.getAlternative().getHttpStatus().value());
            new ModelAndView("redirect:/error").addObject("message", messageSource.getMessage(changa.getAlternative().name(), null,LocaleContextHolder.getLocale()));
        }
        if (changa.getValue().getUser_id() != loggedUser.getUser_id()) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return new ModelAndView("403");
        }
        Either<Changa, Validation> err = cs.changeChangaState(changaId, ChangaState.done);
        if (!err.isValuePresent()) {
            response.setStatus(err.getAlternative().getHttpStatus().value());
            return new ModelAndView("redirect:/error").addObject("message", messageSource.getMessage(err.getAlternative().name(), null, LocaleContextHolder.getLocale()));
        }
        return new ModelAndView("redirect:/profile");
    }

    @RequestMapping(value = "/accept-user", method = RequestMethod.POST)
    public ModelAndView acceptUser(@RequestParam("changaId") final long changaId, @RequestParam("userId") final long userId, @ModelAttribute("getLoggedUser") User loggedUser, HttpServletResponse response) {
        Either<Changa, Validation> changa = cs.getChangaById(changaId);
        if (!changa.isValuePresent()) {
            response.setStatus(changa.getAlternative().getHttpStatus().value());
            new ModelAndView("redirect:/error").addObject("message", messageSource.getMessage(changa.getAlternative().name(), null, LocaleContextHolder.getLocale()));
        }
        if (changa.getValue().getUser_id() != loggedUser.getUser_id()) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return new ModelAndView("403");
        }
        Validation val = is.changeUserStateInChanga(userId, changaId, InscriptionState.accepted);
        if (val.isOk()){
            //TODO este mail en verdad es para cuando hagan el boton changa settled. mientras lo pongo cuando aceptan al changuero
            //hacer validaciones
            //TODO JIME popup preguntando
        } else {
            //TODO JIME un popup de error
            response.setStatus(val.getHttpStatus().value());
            return new ModelAndView("redirect:/error").addObject("message", messageSource.getMessage(val.name(), null, LocaleContextHolder.getLocale()));
        }
        return new ModelAndView("redirect:/admin-changa").addObject("id", changaId);
    }

    @RequestMapping(value = "/reject-user", method = RequestMethod.POST)
    public ModelAndView rejectUser(@RequestParam("changaId") final long changaId, @RequestParam("userId") final long userId, @ModelAttribute("getLoggedUser") User loggedUser, HttpServletResponse response) {
        Either<Changa, Validation> changa = cs.getChangaById(changaId);
        if (!changa.isValuePresent()) {
            response.setStatus(changa.getAlternative().getHttpStatus().value());
            new ModelAndView("redirect:/error").addObject("message", messageSource.getMessage(changa.getAlternative().name(), null, LocaleContextHolder.getLocale()));
        }
        if (changa.getValue().getUser_id() != loggedUser.getUser_id()) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return new ModelAndView("403");
        }
        Validation val = is.changeUserStateInChanga(userId, changaId, InscriptionState.declined);
        if (val.isOk()){
            //TODO JIME popup preguntando
        } else {
            //TODO JIME un popup de error
            response.setStatus(val.getHttpStatus().value());
            return new ModelAndView("redirect:/error").addObject("message", messageSource.getMessage(val.name(), null, LocaleContextHolder.getLocale()));
        }
        return new ModelAndView("redirect:/admin-changa").addObject("id", changaId);
    }


    @RequestMapping(value = "/settle-changa", method = RequestMethod.POST)
    public ModelAndView settleChanga(@RequestParam("id") long changaId, @ModelAttribute("getLoggedUser") User loggedUser, HttpServletResponse response) {
        Either<Changa, Validation> either = cs.changeChangaState(changaId, ChangaState.settled);
        if(!either.isValuePresent()) {
            response.setStatus(either.getAlternative().getHttpStatus().value());
            return new ModelAndView("redirect:/error").addObject("message", messageSource.getMessage(either.getAlternative().name(), null, LocaleContextHolder.getLocale()));
        }
        emailService.sendChangaSettledEmails(either.getValue().getChanga_id());
        return new ModelAndView("redirect:/admin-changa?id=" + changaId);
    }

    @RequestMapping(value = "/join-changa", method = RequestMethod.POST)
    public ModelAndView showChanga(@RequestParam("changaId") final long changaId, @ModelAttribute("getLoggedUser") User loggedUser, HttpServletResponse response) {
        System.out.println("current user id: " + loggedUser.getUser_id());
        Validation val = is.inscribeInChanga(loggedUser.getUser_id(), changaId);
        if (val.isOk()){
            System.out.println("user "+ loggedUser.getUser_id()+ " successfully inscripto en changa "+ changaId);
            //TODO hacer validaciones
            Changa changa = cs.getChangaById(changaId).getValue();
            User changaOwner = us.findById(changa.getUser_id()).getValue();
            emailService.sendJoinRequestEmail(changa, changaOwner, loggedUser);
        } else {
            System.out.println("No se pudo inscribir en la changa pq:"+ val.getMessage());
            response.setStatus(val.getHttpStatus().value());
            return new ModelAndView("redirect:/error").addObject("message", messageSource.getMessage(val.name(), null, LocaleContextHolder.getLocale()));
        }
        return new ModelAndView("redirect:/changa").addObject("id", changaId);
    }

    @RequestMapping(value = "/unjoin-changa", method = RequestMethod.POST)
    public ModelAndView unjoinChanga(@RequestParam("changaId") final long changaId, @ModelAttribute("getLoggedUser") User loggedUser, HttpServletResponse response) {
        Validation val = is.changeUserStateInChanga(loggedUser.getUser_id(), changaId, InscriptionState.optout);
        System.out.println(loggedUser.getEmail() + " desanotado de " + changaId);
        if (val.isOk()){
            System.out.println("user "+ loggedUser.getUser_id()+ " successfully desinscripto en changa "+ changaId);
        } else {
            //TODO JIME un popup de error
            response.setStatus(val.getHttpStatus().value());
            System.out.println("No se pudo desinscribir en la changa pq:"+ val.getMessage());
            return new ModelAndView("redirect:/error").addObject("message", messageSource.getMessage(val.name(), null, LocaleContextHolder.getLocale()));
        }
        return new ModelAndView("redirect:/profile");
    }
}
