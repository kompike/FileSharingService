package com.javaclasses.dao.repository;

import com.javaclasses.dao.entity.User;

import java.util.Collection;

/**
 * Basic interface for CRUD operations with users
 */
public interface UserRepository {

    void create(User user);

    User findUserById(long userId);

    Collection<User> findAllUsers();
}
