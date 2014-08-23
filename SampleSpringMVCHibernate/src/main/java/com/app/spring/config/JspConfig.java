package com.app.spring.config;

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
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

/**
 *
 * @author Gareth
 */
@Configuration
@ComponentScan(basePackages = {"com.app.spring"})
@EnableWebMvc
public class JspConfig extends WebMvcConfigurerAdapter {

    private static final Logger logger = LoggerFactory.getLogger(JspConfig.class);

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
    
    /*
    @Bean(name = "simpleMappingExceptionResolver")
    public SimpleMappingExceptionResolver exceptionResolver() {
        // http://spring.io/blog/2013/11/01/exception-handling-in-spring-mvc/
        SimpleMappingExceptionResolver exceptionResolver = new SimpleMappingExceptionResolver();
        
        Properties exceptionMappings = new Properties();
        
        exceptionMappings.put("com.app.spring.util.AppException", "error/404");
        exceptionMappings.put("java.lang.Exception", "error/error");
        exceptionMappings.put("java.lang.RuntimeException", "error/error");
        
        exceptionResolver.setExceptionMappings(exceptionMappings);
        
        Properties statusCodes = new Properties();
        
        statusCodes.put("error/404", "404");
        statusCodes.put("error/error", "500");
        
        exceptionResolver.setStatusCodes(statusCodes);
        
        exceptionResolver.setDefaultErrorView("error");
        
        return exceptionResolver;
    }
    */
    
    @Bean
    org.springframework.web.servlet.view.InternalResourceViewResolver templateResolver() {
        org.springframework.web.servlet.view.InternalResourceViewResolver tr = new org.springframework.web.servlet.view.InternalResourceViewResolver();
        
        tr.setPrefix("/WEB-INF/views/");
        tr.setSuffix(".jsp");
        
        return tr;
    }
}
