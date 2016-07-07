package com.javaclasses.service;

/**
 * Custom exception for preventing not registered users to login
 */
public class UserNotFoundException extends Exception {

    public UserNotFoundException(String message) {
        super(message);
    }
}
