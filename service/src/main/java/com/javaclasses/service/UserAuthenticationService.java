package com.javaclasses.service;

import com.javaclasses.dao.tinytype.Email;
import com.javaclasses.dao.tinytype.Password;
import com.javaclasses.dao.tinytype.SecurityToken;

/**
 * Basic interface for new user authentication
 */
public interface UserAuthenticationService {

    /**
     * Log user with given credentials to the service
     * @param email Email of current user
     * @param password Password of current user
     * @return Security token for logged user
     */
    SecurityToken login(Email email, Password password);
}
