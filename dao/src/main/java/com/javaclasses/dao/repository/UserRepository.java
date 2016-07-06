package com.javaclasses.dao.repository;

import com.javaclasses.dao.entity.User;
import com.javaclasses.dao.tinytype.Email;

/**
 * Basic interface for CRUD operations with users
 */
public interface UserRepository {

    void create(User user);

    User findUserByEmail(Email email);
}
