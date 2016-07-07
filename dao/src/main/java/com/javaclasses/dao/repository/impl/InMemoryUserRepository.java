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

    private long idCounter;

    private final Map<Long, User> registeredUsers = new HashMap<Long, User>(){{

        put(0L, new User(new Email("email"),
                new Password("password"), new FirstName("firstName"), new LastName("lastName")));
        put(1L, new User(new Email("email1"),
                new Password("password1"), new FirstName("firstName1"), new LastName("lastName1")));
        put(2L, new User(new Email("email2"),
                new Password("password2"), new FirstName("firstName2"), new LastName("lastName2")));
    }};

    private final Map<SecurityToken, User> loggedUsers = new HashMap<>();

    public InMemoryUserRepository() {
        idCounter = registeredUsers.size();
    }

    @Override
    public void create(User user) {

        if (log.isInfoEnabled()) {
            log.info("Start creating new user...");
        }

        user.setId(idCounter);

        if (log.isInfoEnabled()) {
            log.info("New user id: " + user.getId());
        }

        registeredUsers.put(idCounter++, user);

        if (log.isInfoEnabled()) {
            log.info("Users map size is: " + registeredUsers.size());
        }

        if (log.isInfoEnabled()) {
            log.info("New user successfully created.");
        }
    }

    @Override
    public User findUserById(long userId) {

        if (log.isInfoEnabled()) {
            log.info("Looking for user with id: " + userId);
        }

        return registeredUsers.get(userId);
    }

    @Override
    public User findUserByEmail(Email email) {

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
    public Collection<User> findAllRegisteredUsers() {

        if (log.isInfoEnabled()) {
            log.info("Getting the list of all registered users.");
        }

        return registeredUsers.values();
    }

    @Override
    public void addLoggedUser(SecurityToken token, User user) {

        if (log.isInfoEnabled()) {
            log.info("User logged in.");
        }

        loggedUsers.put(token, user);
    }

    @Override
    public User findLoggedUserBySecurityToken(SecurityToken token) {

        if (log.isInfoEnabled()) {
            log.info("Looking for user with security token: " + token);
        }

        return loggedUsers.get(token);
    }
}
