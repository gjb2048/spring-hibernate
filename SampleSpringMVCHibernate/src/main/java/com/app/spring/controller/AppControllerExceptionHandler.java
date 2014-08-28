package com.app.spring.controller;

import com.app.spring.util.AppException;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author Gareth
 */
@ControllerAdvice
public class AppControllerExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(AppControllerExceptionHandler.class);

    // Total control - setup a model and return the view name yourself. Or consider
    // subclassing ExceptionHandlerExceptionResolver (see below).

    @ExceptionHandler(AppException.class)
    public ModelAndView handleError(HttpServletRequest req, AppException exception) {
        logger.error("Request: " + req.getRequestURL() + " raised " + exception);

        ModelAndView mav = new ModelAndView();
        mav.addObject("exception", exception);
        mav.addObject("exceptionMessage", exception.getMessage());
        mav.addObject("cause", exception.getCause());
        mav.addObject("url", req.getRequestURL());
        mav.setViewName("error");
        return mav;
    }
}
