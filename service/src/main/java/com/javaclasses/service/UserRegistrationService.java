package com.javaclasses.service;

import com.javaclasses.dao.entity.User;

/**
 * Basic interface for new user registration
 */
public interface UserRegistrationService {

    /**
     * Perform registration of new user
     * @param user User to be registered
     * @throws UserAlreadyExistsException When user with current email
     * already exists in the system
     */
    void registerNewUser(User user)
            throws UserAlreadyExistsException;
}
