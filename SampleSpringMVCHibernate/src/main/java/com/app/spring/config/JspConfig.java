package com.app.spring.config;

import java.util.Properties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
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

    // Maps resources path to webapp/resources
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
    }

    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }
 
    @Bean(name="simpleMappingExceptionResolver")
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
