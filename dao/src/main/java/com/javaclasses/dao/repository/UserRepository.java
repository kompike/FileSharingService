package com.javaclasses.dao.repository;

import com.javaclasses.dao.entity.User;
import com.javaclasses.dao.tinytype.Email;
import com.javaclasses.dao.tinytype.SecurityToken;

import java.util.Collection;

/**
 * Basic interface for CRUD operations with users
 */
public interface UserRepository {

    /**
     * Add new user to the database
     * @param user
     */
    void create(User user);

    /**
     * Search for user in database by id
     * @param userId Id of desired user
     * @return User with given id
     */
    User findUserById(long userId);

    /**
     * Search for user in database by email
     * @param email Email of desired user
     * @return User with given email
     */
    User findUserByEmail(Email email);

    /**
     * Get all users from the database
     * @return Collection of all available users
     */
    Collection<User> findAllRegisteredUsers();

    /**
     * Add logged user to the database
     * @param token Unique security identifier of logged user
     * @param user Logged user
     */
    void addLoggedUser(SecurityToken token, User user);

    /**
     * Search for user in database of logged users by security token
     * @param token Unique security identifier of logged user
     * @return User with given security token
     */
    User findLoggedUserBySecurityToken(SecurityToken token);
}
