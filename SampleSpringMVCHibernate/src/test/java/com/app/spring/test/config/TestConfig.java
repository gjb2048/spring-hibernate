package com.app.spring.test.config;

import com.app.spring.service.CustomerService;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Ref:
 * http://www.petrikainulainen.net/programming/spring-framework/unit-testing-of-spring-mvc-controllers-configuration/
 *
 * @author Gareth
 */
@Configuration
public class TestConfig {

    @Bean
    CustomerService customerService() {
        return Mockito.mock(CustomerService.class);
    }
}
