package com.app.spring.config;

import com.app.spring.util.AppExceptionResolver;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 *
 * @author Gareth
 */
@Configuration
@ComponentScan(basePackages = {"com.app.spring.controller"})
@EnableWebMvc
public class WebConfig extends WebMvcConfigurerAdapter {

    private static final Logger logger = LoggerFactory.getLogger(WebConfig.class);

    // Maps resources path to webapp/resources
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
    }

    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

    @Override
    public void configureHandlerExceptionResolvers(List<HandlerExceptionResolver> exceptionResolvers) {
        super.configureHandlerExceptionResolvers(exceptionResolvers);
        Iterator<HandlerExceptionResolver> it = exceptionResolvers.iterator();
        while (it.hasNext()) {
            logger.info("HandlerExceptionResolver: " + it.next().toString());
        }
    }

    // TODO possibly: http://steveliles.github.io/configuring_global_exception_handling_in_spring_mvc.html
    @Bean(name = "simpleMappingExceptionResolver")
    public AppExceptionResolver exceptionResolver() {
        // Ref: http://spring.io/blog/2013/11/01/exception-handling-in-spring-mvc/
        // This is the fallback after @ExceptionHandler and then @ResponseStatus have not been defined for an error.
        AppExceptionResolver exceptionResolver = new AppExceptionResolver();

        Properties exceptionMappings = new Properties();

        exceptionMappings.put("com.app.spring.util.AppException", "error_404");
        exceptionMappings.put("java.lang.Exception", "error");
        exceptionMappings.put("java.lang.RuntimeException", "error");

        exceptionResolver.setExceptionMappings(exceptionMappings);

        Properties statusCodes = new Properties();

        statusCodes.put("error_404", "404");
        statusCodes.put("error", "500");

        exceptionResolver.setStatusCodes(statusCodes);

        exceptionResolver.setDefaultErrorView("error");

        return exceptionResolver;
    }

    @Bean
    org.springframework.web.servlet.view.InternalResourceViewResolver templateResolver() {
        org.springframework.web.servlet.view.InternalResourceViewResolver tr = new org.springframework.web.servlet.view.InternalResourceViewResolver();

        tr.setPrefix("/WEB-INF/views/");
        tr.setSuffix(".jsp");

        return tr;
    }
}
