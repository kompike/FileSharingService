package com.javaclasses.service;

import com.javaclasses.dao.repository.UserRepository;
import com.javaclasses.dao.repository.impl.InternalUserRepository;
import com.javaclasses.dao.tinytype.Email;
import com.javaclasses.dao.tinytype.Password;
import com.javaclasses.service.impl.UserAuthenticationServiceImpl;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

public class UserAuthenticationServiceShould {

    private final UserRepository userRepository = new InternalUserRepository();

    private final UserAuthenticationService userAuthenticationService =
            new UserAuthenticationServiceImpl(userRepository);

    @Test
    public void login() throws UserNotFoundException {

        assertNotNull("User with given credentials not found.",
                userAuthenticationService.login(new Email("email"), new Password("password")));
    }

    @Test
    public void testUserNotFoundException() {

        try {
            userAuthenticationService.login(new Email("newEmail"), new Password("newPassword"));
            fail("UserNotFoundException was not thrown.");
        } catch (UserNotFoundException ex) {
            assertEquals("Wrong message for UserNotFoundException.",
                    "User with given credentials not found.", ex.getMessage());
        }

    }
}
