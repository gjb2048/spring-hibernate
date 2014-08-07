package com.app.spring.config;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

/**
 *
 * @author Gareth
 */
public class AppInit implements WebApplicationInitializer {

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        //Load application context
        AnnotationConfigWebApplicationContext rootContext = new AnnotationConfigWebApplicationContext();
        rootContext.register(AppConfig.class);
        rootContext.setDisplayName("Sample Spring MVC Hibernate");

        //Context loader listener
        servletContext.addListener(new ContextLoaderListener(rootContext));

        AnnotationConfigWebApplicationContext servContext = new AnnotationConfigWebApplicationContext();
        servContext.register(JspConfig.class);
        servContext.setDisplayName("Sample Spring MVC Hibernate Servlet");

        //Dispatcher servlet
        ServletRegistration.Dynamic dispatcher
                = servletContext.addServlet("dispatcher", new DispatcherServlet(servContext));
        dispatcher.setLoadOnStartup(1);
        dispatcher.addMapping("/");
    }
}
