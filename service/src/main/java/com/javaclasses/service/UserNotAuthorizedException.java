package com.javaclasses.service;

/**
 * Custom exception for restricting access without security token
 */
public class UserNotAuthorizedException extends Exception {

    public UserNotAuthorizedException(String message) {
        super(message);
    }
}
