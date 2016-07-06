package com.javaclasses.service.impl;

import com.javaclasses.dao.repository.UserRepository;
import com.javaclasses.dao.tinytype.Email;
import com.javaclasses.dao.tinytype.FirstName;
import com.javaclasses.dao.tinytype.LastName;
import com.javaclasses.dao.tinytype.Password;
import com.javaclasses.service.UserAlreadyExistsException;
import com.javaclasses.service.UserRegistrationService;

/**
 * Implementation of {@link UserRegistrationService} interface
 */
public class UserRegistrationServiceImpl implements UserRegistrationService {

    private final UserRepository userRepository;

    public UserRegistrationServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void registerNewUser(Email email, Password password,
                                FirstName firstName, LastName lastName)
            throws UserAlreadyExistsException {

    }
}
