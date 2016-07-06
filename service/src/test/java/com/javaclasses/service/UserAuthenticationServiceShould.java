package com.javaclasses.service;

import com.javaclasses.dao.repository.UserRepository;
import com.javaclasses.dao.repository.impl.InMemoryUserRepository;
import com.javaclasses.dao.tinytype.Email;
import com.javaclasses.dao.tinytype.Password;
import com.javaclasses.dao.tinytype.SecurityToken;
import com.javaclasses.service.impl.UserAuthenticationServiceImpl;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class UserAuthenticationServiceShould {

    private final UserRepository userRepository = new InMemoryUserRepository();

    private final UserAuthenticationService userAuthenticationService =
            new UserAuthenticationServiceImpl(userRepository);

    @Test
    public void loginUser() throws UserNotFoundException {

        final Email email = new Email("email");
        final Password password = new Password("password");

        final SecurityToken token =
                userAuthenticationService.login(email, password);

        assertEquals("Returned email does not equal expected one.", email,
                userRepository.findLoggedUserBySecurityToken(token).getEmail());
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
