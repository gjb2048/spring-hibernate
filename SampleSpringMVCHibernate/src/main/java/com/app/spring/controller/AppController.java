package com.app.spring.controller;

import com.app.spring.util.AppException;
import java.text.MessageFormat;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author Gareth
 */
@Controller
public class AppController {

    private static final Logger logger = LoggerFactory.getLogger(AppController.class);

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index(Model model, HttpServletRequest req) {
        model.addAttribute("datetime", new Date());

        logger.info("Request: " + req.getRequestURI());

        return "index";
    }

    @RequestMapping(value = "/references", method = RequestMethod.GET)
    public String references(Model model) {
        return "refs";
    }

    @RequestMapping({"error", "/error",/* "/**" */})
    @ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "The page was not found, oops!")
    public ModelAndView customError(HttpServletRequest request, HttpServletResponse response, ModelAndView mav) {
        // http://www.javacodegeeks.com/2013/11/how-to-custom-error-pages-in-tomcat-with-spring-mvc.html
        // http://blog.codeleak.pl/2013/04/how-to-custom-error-pages-in-tomcat.html
        // retrieve some useful information from the request
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        Throwable throwable = (Throwable) request.getAttribute("javax.servlet.error.exception");
        // String servletName = (String) request.getAttribute("javax.servlet.error.servlet_name");
        String exceptionMessage = getExceptionMessage(throwable, statusCode);

        String requestUri = (String) request.getAttribute("javax.servlet.error.request_uri");
        if (requestUri == null) {
            requestUri = "Unknown";
        }

        String message = MessageFormat.format("{0} returned for {1} with message {2}",
                statusCode, requestUri, exceptionMessage
        );

        AppException exception = new AppException(message);

        mav.addObject("exception", exception);
        mav.addObject("exceptionMessage", message);
        mav.addObject("url", request.getRequestURI());
        mav.setViewName("error");
        return mav;

    }

    private String getExceptionMessage(Throwable throwable, Integer statusCode) {
        if (throwable != null) {
            return throwable.getMessage();
        }
        HttpStatus httpStatus = HttpStatus.valueOf(statusCode);
        return httpStatus.getReasonPhrase();
    }
}
