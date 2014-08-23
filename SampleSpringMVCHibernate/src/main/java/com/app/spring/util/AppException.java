package com.app.spring.util;

/**
 *
 * @author Gareth
 */
public class AppException extends Exception {

    String message;
    
    public AppException(String message)
    {
        super();
        this.message = message;
    }

    @Override
    public String getMessage() {
        return this.message + " - " + super.getMessage();
    }    
}
