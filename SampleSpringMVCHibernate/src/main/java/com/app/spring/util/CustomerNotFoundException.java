package com.app.spring.util;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 *
 * @author Gareth
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class CustomerNotFoundException extends RuntimeException {
    // http://www.javabeat.net/spring-mvc-404-error-page/

    private final int customerId;

    public CustomerNotFoundException(int cid) {
        super();
        this.customerId = cid;
    }

    public int getCustomerId() {
        return this.customerId;
    }

    @Override
    public String getMessage() {
        return "CustomerNotFoundException: Customer " + this.customerId + " not found.";
    }
}
