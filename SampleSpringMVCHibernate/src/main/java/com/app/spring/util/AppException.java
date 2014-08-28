package com.app.spring.util;

/**
 *
 * @author Gareth
 */
public class AppException extends Exception {

    String message;
    Exception cause;
    
    public AppException(String message, Exception cause)
    {
        super();
        this.message = message;
        this.cause = cause;
    }

    @Override
    public String getMessage() {
        return this.message + " - " + super.getMessage();
    }
    
    @Override
    public Exception getCause() {
        return this.cause;
    }
}
