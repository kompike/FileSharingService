package com.javaclasses.dao.repository.impl;

import com.javaclasses.dao.entity.User;
import com.javaclasses.dao.repository.UserRepository;
import com.javaclasses.dao.tinytype.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Implementation of {@link UserRepository} interface for in memory data
 */
public class InMemoryUserRepository implements UserRepository {

    private final Logger log = LoggerFactory.getLogger(InMemoryUserRepository.class);

    private volatile long userIdCounter;

    private final Map<Long, User> registeredUsers = new HashMap<>();

    private final Map<SecurityToken, User> loggedUsers = new HashMap<>();

    public InMemoryUserRepository() {
        userIdCounter = registeredUsers.size();
    }

    @Override
    public synchronized void create(User user) {

        if (log.isInfoEnabled()) {
            log.info("Start creating new user...");
        }

        user.setId(new UserId(userIdCounter));

        if (log.isInfoEnabled()) {
            log.info("New user id: " + user.getId());
        }

        registeredUsers.put(userIdCounter++, user);

        if (log.isInfoEnabled()) {
            log.info("Users map size is: " + registeredUsers.size());
        }

        if (log.isInfoEnabled()) {
            log.info("New user successfully created.");
        }
    }

    @Override
    public synchronized User findUserById(UserId userId) {

        if (log.isInfoEnabled()) {
            log.info("Looking for user with id: " + userId);
        }

        return registeredUsers.get(userId.getUserId());
    }

    @Override
    public synchronized User findUserByEmail(Email email) {

        final Collection<User> registeredUsers = findAllRegisteredUsers();

        for (User user : registeredUsers) {

            if (log.isInfoEnabled()) {
                log.info("User with given email found: " + email);
            }

            if (user.getEmail().equals(email)) {

                return user;
            }
        }

        if (log.isInfoEnabled()) {
            log.info("User with given email not found: " + email);
        }

        return null;
    }

    @Override
    public synchronized Collection<User> findAllRegisteredUsers() {

        if (log.isInfoEnabled()) {
            log.info("Getting the list of all registered users.");
        }

        return registeredUsers.values();
    }

    @Override
    public synchronized void addLoggedUser(SecurityToken token, User user) {

        if (log.isInfoEnabled()) {
            log.info("User logged in.");
        }

        loggedUsers.put(token, user);
    }

    @Override
    public synchronized User findLoggedUserBySecurityToken(SecurityToken token) {

        if (log.isInfoEnabled()) {
            log.info("Looking for user with security token: " + token);
        }

        return loggedUsers.get(token);
    }
}
