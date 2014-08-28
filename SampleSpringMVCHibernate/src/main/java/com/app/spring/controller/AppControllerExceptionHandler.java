package com.app.spring.controller;

import com.app.spring.util.AppException;
import com.app.spring.util.Utils;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
    public ModelAndView handleError(HttpServletRequest req, HttpServletResponse resp, AppException exception) {
        logger.error("Request: " + req.getRequestURL() + " raised " + exception);

        resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        
        return Utils.getErrorMAV(req, exception, "error");
    }
}
