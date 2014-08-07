package com.app.spring.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 *
 * @author Gareth
 */
@Configuration
@Import({DataConfig.class})
public class AppConfig {
                
}
