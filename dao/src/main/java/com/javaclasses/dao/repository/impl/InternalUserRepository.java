package com.javaclasses.dao.repository.impl;

import com.javaclasses.dao.entity.User;
import com.javaclasses.dao.repository.UserRepository;
import com.javaclasses.dao.tinytype.Email;

/**
 * Implementation of {@link UserRepository} interface for internal data
 */
public class InternalUserRepository implements UserRepository{

    @Override
    public void create(User user) {

    }

    @Override
    public User findUserByEmail(Email email) {
        return null;
    }
}
