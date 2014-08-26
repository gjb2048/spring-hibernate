package com.app.spring.config;

import java.util.Enumeration;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

/**
 * http://docs.spring.io/autorepo/docs/spring-framework/4.0.x/javadoc-api/org/springframework/web/WebApplicationInitializer.html
 * http://kielczewski.eu/2013/11/spring-mvc-without-web-xml-using-webapplicationinitializer/
 * @author Gareth
 */
public class AppInit implements WebApplicationInitializer {
    private static final Logger logger = LoggerFactory.getLogger(AppInit.class);

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        //Load application context
        AnnotationConfigWebApplicationContext rootContext = new AnnotationConfigWebApplicationContext();
        rootContext.register(AppConfig.class);
        rootContext.setDisplayName("Sample Spring MVC Hibernate");

        Enumeration<String> attrs = servletContext.getAttributeNames();
        while(attrs.hasMoreElements()) {
            logger.info("AppInit:servletContext - Attr name: " + attrs.nextElement());
        }
        
        //Context loader listener
        servletContext.addListener(new ContextLoaderListener(rootContext));

        AnnotationConfigWebApplicationContext servContext = new AnnotationConfigWebApplicationContext();
        servContext.register(WebConfig.class);
        servContext.setDisplayName("Sample Spring MVC Hibernate Servlet");

        //Dispatcher servlet
        ServletRegistration.Dynamic dispatcher
                = servletContext.addServlet("dispatcher", new DispatcherServlet(servContext));
        dispatcher.setLoadOnStartup(1);
        dispatcher.addMapping("/");
    }
}
