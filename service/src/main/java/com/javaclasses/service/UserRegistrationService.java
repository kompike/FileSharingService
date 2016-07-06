package com.javaclasses.service;

import com.javaclasses.dao.tinytype.Email;
import com.javaclasses.dao.tinytype.FirstName;
import com.javaclasses.dao.tinytype.LastName;
import com.javaclasses.dao.tinytype.Password;

/**
 * Basic interface for new user registration
 */
public interface UserRegistrationService {

    /**
     * Perform registration of new user
     * @param email Email of new user
     * @param password Password of new user
     * @param firstName First name of new user
     * @param lastName Last name of new user
     * @throws UserAlreadyExistsException When user with current email
     * already exists in the system
     */
    void registerNewUser(Email email, Password password, FirstName firstName, LastName lastName)
            throws UserAlreadyExistsException;
}
