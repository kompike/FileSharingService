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

public class UserRegistrationServiceShould {

    private final UserRepository userRepository = new InternalUserRepository();

    private final UserRegistrationService userRegistrationService =
            new UserRegistrationServiceImpl(userRepository);

    @Test
    public void registerNewUser() throws UserAlreadyExistsException {

        final User user = new User(new Email("test@test.com"), new Password("password"),
                new FirstName("Test"), new LastName("Test"));

        userRegistrationService.registerNewUser(user.getEmail(), user.getPassword(),
                user.getFirstName(), user.getLastName());

        final User userFromRepository = userRepository.findUserByEmail(user.getEmail());

        assertEquals("Returned user does not equals registered.",
                user, userFromRepository);
    }
}
