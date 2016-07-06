package com.javaclasses.dao.repository.impl;

import com.javaclasses.dao.entity.User;
import com.javaclasses.dao.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Implementation of {@link UserRepository} interface for internal data
 */
public class InternalUserRepository implements UserRepository{

    private final Logger log = LoggerFactory.getLogger(InternalUserRepository.class);

    private long idCounter = 1;

    private final Map<Long, User> users = new HashMap<>();

    @Override
    public void create(User user) {

        if (log.isInfoEnabled()) {
            log.info("Start creating new user...");
        }

        user.setId(idCounter);

        if (log.isInfoEnabled()) {
            log.info("New user id: " + user.getId());
        }

        users.put(idCounter++, user);

        if (log.isInfoEnabled()) {
            log.info("Users map size is: " + users.size());
        }

        if (log.isInfoEnabled()) {
            log.info("New user successfully created");
        }
    }

    @Override
    public User findUserById(long userId) {

        if (log.isInfoEnabled()) {
            log.info("Looking for user with id: " + userId);
        }

        return users.get(userId);
    }

    @Override
    public Collection<User> findAllUsers() {

        return users.values();
    }
}
