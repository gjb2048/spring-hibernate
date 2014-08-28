package com.app.spring.util;

import javax.servlet.http.HttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author Gareth
 */
public class Utils {

    public static ModelAndView getErrorMAV(HttpServletRequest req, Exception exception, String view) {
        ModelAndView mav = new ModelAndView();
        mav.addObject("exception", exception);
        mav.addObject("exceptionMessage", exception.getMessage());
        mav.addObject("cause", exception.getCause());
        mav.addObject("url", req.getRequestURL());
        mav.setViewName(view);
        return mav;
    }
}
