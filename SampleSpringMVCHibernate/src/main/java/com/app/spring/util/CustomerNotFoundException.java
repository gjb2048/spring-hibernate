package com.app.spring.util;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 *
 * @author Gareth
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class CustomerNotFoundException extends AppException {
    // http://www.javabeat.net/spring-mvc-404-error-page/

    private final int customerId;

    public CustomerNotFoundException(int cid, Exception cause) {
        super("Customer " + cid + " not found.", cause);
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
