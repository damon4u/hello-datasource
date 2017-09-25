package com.damon4u.demo.exception;

/**
 * Description:
 *
 * @author damon4u
 * @version 2017-09-22 17:16
 */
public class ServiceException extends Exception {

    private String message;

    public ServiceException(String message) {
        super(message);
        this.message = message;
    }
}
