package com.javaclasses.service;

import com.javaclasses.dao.entity.User;
import com.javaclasses.dao.repository.UserRepository;
import com.javaclasses.dao.repository.impl.InMemoryUserRepository;
import com.javaclasses.dao.tinytype.*;
import com.javaclasses.service.impl.UserAuthenticationServiceImpl;
import com.javaclasses.service.impl.UserRegistrationServiceImpl;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class UserAuthenticationServiceShould {

    private final UserRepository userRepository = new InMemoryUserRepository();

    private final UserAuthenticationService userAuthenticationService =
            new UserAuthenticationServiceImpl(userRepository);

    @Before
    public void createUsers() throws UserAlreadyExistsException {
        final UserRegistrationService userRegistrationService =
                new UserRegistrationServiceImpl(userRepository);

        final User user1 = new User(new Email("email"),
                new Password("password"), new FirstName("firstName"), new LastName("lastName"));
        final User user2 = new User(new Email("email1"),
                new Password("password1"), new FirstName("firstName1"), new LastName("lastName1"));
        final User user3 = new User(new Email("email2"),
                new Password("password2"), new FirstName("firstName2"), new LastName("lastName2"));

        userRegistrationService.registerNewUser(user1);
        userRegistrationService.registerNewUser(user2);
        userRegistrationService.registerNewUser(user3);
    }

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
