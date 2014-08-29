package com.app.spring.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

/**
 *
 * @author Gareth
 */
public class AppExceptionResolver extends SimpleMappingExceptionResolver {

    // Ref: http://spring.io/blog/2013/11/01/exception-handling-in-spring-mvc/

    public AppExceptionResolver() {
        // Enable logging by providing the name of the logger to use
        setWarnLogCategory(AppExceptionResolver.class.getName());
    }

    @Override
    public String buildLogMessage(Exception e, HttpServletRequest req) {
        return "MVC exception: " + e.getLocalizedMessage();
    }

    @Override
    protected ModelAndView doResolveException(HttpServletRequest request,
            HttpServletResponse response, Object handler, Exception exception) {
        // Call super method to get the ModelAndView
        ModelAndView mav = super.doResolveException(request, response, handler, exception);

        // Make the full URL available to the view - note ModelAndView uses addObject()
        // but Model uses addAttribute(). They work the same. 
        mav.addObject("url", request.getRequestURL());
        mav.addObject("exception", exception);
        mav.addObject("exceptionMessage", exception.getMessage());
        mav.addObject("cause", exception.getCause());
        //mav.setViewName("error");

        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);

        return mav;
    }
}
