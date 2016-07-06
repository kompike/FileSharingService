package com.javaclasses.service;

import com.javaclasses.dao.entity.User;
import com.javaclasses.dao.repository.UserRepository;
import com.javaclasses.dao.repository.impl.InternalUserRepository;
import com.javaclasses.dao.tinytype.Email;
import com.javaclasses.dao.tinytype.FirstName;
import com.javaclasses.dao.tinytype.LastName;
import com.javaclasses.dao.tinytype.Password;
import com.javaclasses.service.impl.UserRegistrationServiceImpl;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class UserRegistrationServiceShould {

    private final UserRepository userRepository = new InternalUserRepository();

    private final UserRegistrationService userRegistrationService =
            new UserRegistrationServiceImpl(userRepository);

    @Test
    public void registerNewUser() throws UserAlreadyExistsException {

        final User user = new User(new Email("test@test.com"), new Password("password"),
                new FirstName("Test"), new LastName("Test"));

        userRegistrationService.registerNewUser(user);

        final User userFromRepository = userRepository.findUserById(user.getId());

        assertEquals("Returned user does not equals registered.",
                user, userFromRepository);
    }

    @Test
    public void testAlreadyExistingUserRegistration() {

        final User user = new User(new Email("test@test.com"), new Password("password"),
                new FirstName("Test"), new LastName("Test"));

        try {
            userRegistrationService.registerNewUser(user);

            userRegistrationService.registerNewUser(user);
            fail("UserAlreadyExistsException was not thrown.");
        } catch (UserAlreadyExistsException ex) {
            assertEquals("Expected and actual emails must be equal.",
                    user.getEmail(), ex.getEmail());
        }

    }
}
