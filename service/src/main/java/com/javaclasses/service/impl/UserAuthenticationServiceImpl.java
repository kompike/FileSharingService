package com.javaclasses.service.impl;

import com.javaclasses.dao.entity.User;
import com.javaclasses.dao.repository.UserRepository;
import com.javaclasses.dao.tinytype.Email;
import com.javaclasses.dao.tinytype.Password;
import com.javaclasses.dao.tinytype.SecurityToken;
import com.javaclasses.service.UserAuthenticationService;
import com.javaclasses.service.UserNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Implementation of {@link UserAuthenticationService} interface
 */
public class UserAuthenticationServiceImpl implements UserAuthenticationService {

    private final Logger log = LoggerFactory.getLogger(UserAuthenticationServiceImpl.class);

    private final UserRepository userRepository;

    public UserAuthenticationServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public SecurityToken login(Email email, Password password)
            throws UserNotFoundException {

        checkNotNull(email, "Email must not be null.");
        checkNotNull(password, "Password must not be null.");

        if (log.isInfoEnabled()) {

            log.info("Searching for user with email: " + email);
        }

        final User user = userRepository.findUserByEmail(email);

        if (user == null || !user.getPassword().equals(password)) {

            if (log.isWarnEnabled()) {

                log.warn("User with given credentials not found.");
            }

            throw new UserNotFoundException("User with given credentials not found.");
        }

        if (log.isInfoEnabled()) {

            log.info("Creating new security token...");
        }

        final SecurityToken token = new SecurityToken(user.hashCode());

        userRepository.authorizeUser(token, user);

        return token;
    }

    @Override
    public void logout(SecurityToken token) {

        checkNotNull(token, "Security token must not be null.");

        userRepository.logoutUser(token);
    }
}
