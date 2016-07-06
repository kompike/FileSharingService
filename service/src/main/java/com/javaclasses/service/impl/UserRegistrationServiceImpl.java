package com.javaclasses.service.impl;

import com.javaclasses.dao.entity.User;
import com.javaclasses.dao.repository.UserRepository;
import com.javaclasses.dao.tinytype.Email;
import com.javaclasses.service.UserAlreadyExistsException;
import com.javaclasses.service.UserRegistrationService;

import java.util.Collection;

/**
 * Implementation of {@link UserRegistrationService} interface
 */
public class UserRegistrationServiceImpl implements UserRegistrationService {

    private final UserRepository userRepository;

    public UserRegistrationServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void registerNewUser(User user)
            throws UserAlreadyExistsException {

        final Collection<User> users = userRepository.findAllRegisteredUsers();

        final Email userEmail = user.getEmail();

        for (User alreadyRegisteredUser : users) {

            if (alreadyRegisteredUser.getEmail().equals(userEmail)) {

                throw new UserAlreadyExistsException("User with given email already exists",
                        userEmail);
            }
        }

        userRepository.create(user);
    }
}
