package com.javaclasses.service;

import com.javaclasses.dao.tinytype.SecurityToken;

/**
 * Custom exception for restricting access without security token
 */
public class IllegalSecurityTokenException extends Exception {

    private final SecurityToken token;

    public IllegalSecurityTokenException(String message, SecurityToken token) {
        super(message + " " + token);
        this.token = token;
    }

    public SecurityToken getToken() {
        return token;
    }
}
