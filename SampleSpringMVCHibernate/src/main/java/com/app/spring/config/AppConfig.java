package com.app.spring.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 *
 * @author Gareth
 */
@Configuration
@Import({WebConfig.class, DataConfig.class})
@ComponentScan(basePackages = {"com.app.spring.service"})
public class AppConfig {
                
}
