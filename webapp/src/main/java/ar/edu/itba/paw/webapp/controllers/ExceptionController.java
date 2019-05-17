package ar.edu.itba.paw.webapp.controllers;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import  java.net.ConnectException;

@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler(ConnectException.class)
    public ModelAndView conectionExceptionHandler() {
        return new ModelAndView("error").addObject("message", "No hay conexión a la bd");
    }
}
