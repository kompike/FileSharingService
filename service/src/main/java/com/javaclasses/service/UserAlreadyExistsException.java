package com.javaclasses.service;

import com.javaclasses.dao.tinytype.Email;

/**
 * Custom exception for preventing registration of already existing users
 */
public class UserAlreadyExistsException extends Exception {

    private final Email email;

    public UserAlreadyExistsException(String message, Email email) {
        super(message + " : " + email.getEmail());
        this.email = email;
    }

    public Email getEmail() {
        return email;
    }
}
