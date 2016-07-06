package com.javaclasses.service.impl;

import com.javaclasses.dao.entity.User;
import com.javaclasses.dao.repository.UserRepository;
import com.javaclasses.dao.tinytype.Email;
import com.javaclasses.dao.tinytype.Password;
import com.javaclasses.dao.tinytype.SecurityToken;
import com.javaclasses.service.UserAuthenticationService;
import com.javaclasses.service.UserNotFoundException;

import java.util.Random;

/**
 * Implementation of {@link UserAuthenticationService} interface
 */
public class UserAuthenticationServiceImpl implements UserAuthenticationService {

    private final UserRepository userRepository;

    public UserAuthenticationServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public SecurityToken login(Email email, Password password)
            throws UserNotFoundException {

        final User user = userRepository.findUserByEmail(email);

        if (user == null || !user.getPassword().equals(password)) {

            throw new UserNotFoundException("User with given credentials not found.");
        }

        final SecurityToken token = new SecurityToken(user.hashCode());

        userRepository.addLoggedUser(token, user);

        return token;
    }
}
