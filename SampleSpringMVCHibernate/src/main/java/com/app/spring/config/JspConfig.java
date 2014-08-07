package com.app.spring.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

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

    @Bean
    org.springframework.web.servlet.view.InternalResourceViewResolver templateResolver() {
        org.springframework.web.servlet.view.InternalResourceViewResolver tr = new org.springframework.web.servlet.view.InternalResourceViewResolver();
        
        tr.setPrefix("/WEB-INF/views/");
        tr.setSuffix(".jsp");
        
        return tr;
    }    
}
