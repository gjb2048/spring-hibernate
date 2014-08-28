package com.app.spring.test.config;

import com.app.spring.model.CustomerInterface;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Qualifier;
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
    @Qualifier(value = "customerService")
    CustomerInterface customerInterface() {
        return Mockito.mock(CustomerInterface.class);
    }
}
