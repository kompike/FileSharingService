package com.javaclasses.service.impl;

import com.google.common.base.Preconditions;
import com.javaclasses.dao.entity.User;
import com.javaclasses.dao.repository.UserRepository;
import com.javaclasses.dao.tinytype.Email;
import com.javaclasses.service.UserAlreadyExistsException;
import com.javaclasses.service.UserRegistrationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Implementation of {@link UserRegistrationService} interface
 */
public class UserRegistrationServiceImpl implements UserRegistrationService {

    private final Logger log = LoggerFactory.getLogger(UserRegistrationServiceImpl.class);

    private final UserRepository userRepository;

    public UserRegistrationServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void registerNewUser(User user)
            throws UserAlreadyExistsException {

        checkNotNull(user, "User argument has to be initialized.");

        final Collection<User> users = userRepository.findAllRegisteredUsers();

        final Email userEmail = user.getEmail();

        if (log.isInfoEnabled()) {

            log.info("Checking if user with given credentials exists...");
        }

        for (User alreadyRegisteredUser : users) {

            if (alreadyRegisteredUser.getEmail().equals(userEmail)) {

                if (log.isWarnEnabled()) {

                    log.warn("User with given email already exists: " + userEmail);
                }

                throw new UserAlreadyExistsException("User with given email already exists",
                        userEmail);
            }
        }

        if (log.isInfoEnabled()) {

            log.info("User successfully registered.");
        }

        userRepository.create(user);
    }
}
