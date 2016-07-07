package com.javaclasses.service;

import com.javaclasses.dao.entity.File;
import com.javaclasses.dao.entity.User;
import com.javaclasses.dao.repository.FileRepository;
import com.javaclasses.dao.repository.UserRepository;
import com.javaclasses.dao.repository.impl.InMemoryFileRepository;
import com.javaclasses.dao.repository.impl.InMemoryUserRepository;
import com.javaclasses.dao.tinytype.*;
import com.javaclasses.service.impl.FileServiceImpl;
import com.javaclasses.service.impl.UserAuthenticationServiceImpl;
import com.javaclasses.service.impl.UserRegistrationServiceImpl;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

import static org.junit.Assert.assertEquals;

public class FileSharingServiceConcurrencyTest {

    private final FileRepository fileRepository = new InMemoryFileRepository();
    private final UserRepository userRepository = new InMemoryUserRepository();

    private final UserRegistrationService userRegistrationService =
            new UserRegistrationServiceImpl(userRepository);

    private final UserAuthenticationService userAuthenticationService =
            new UserAuthenticationServiceImpl(userRepository);

    private final FileService fileService =
            new FileServiceImpl(fileRepository, userRepository);

    @Test
    public void testExecutionInMultipleTreads()
            throws InterruptedException, ExecutionException {

        final int threadPoolSize = 50;

        final CountDownLatch startLatch = new CountDownLatch(threadPoolSize);

        final ExecutorService executorService = Executors.newFixedThreadPool(threadPoolSize);

        final List<Future<User>> futureList = new ArrayList<>();

        for (int i = 0; i < threadPoolSize; i++) {

            final int currentIndex = i;

            final Future<User> future = executorService.submit(() -> {

                startLatch.countDown();
                startLatch.await();

                final Email email = new Email("email" + currentIndex);

                final User user = new User(
                        email, new Password("password" + currentIndex),
                        new FirstName("firstName" + currentIndex), new LastName("lastName" + currentIndex));

                try {

                    userRegistrationService.registerNewUser(user);
                } catch (UserAlreadyExistsException e) {
                    e.getMessage();
                }

                final UserId userId = user.getId();

                final User userById = userRepository.findUserById(userId);

                assertEquals("Users with given ids must be equal: " +
                        userId + " and " + userById.getId(), user, userById);

                final SecurityToken token =
                        userAuthenticationService.login(email,
                                new Password("password" + currentIndex));

                assertEquals("Emails must be equal", "email" + currentIndex,
                        userRepository.findLoggedUserBySecurityToken(token).getEmail().getEmail());

                final File fileToBeAdded =
                        new File("fileName" + currentIndex, new FileSize(256));

                final ByteArrayInputStream fileContent =
                        new ByteArrayInputStream(new byte[256]);

                fileService.uploadFile(token, fileToBeAdded, fileContent);

                final File fileFromRepository =
                        fileRepository.findFileById(fileToBeAdded.getFileId());

                assertEquals("Files must be equals.", fileToBeAdded, fileFromRepository);

                final File secondFileToBeAdded =
                        new File("fileName" + currentIndex, new FileSize(256));

                fileService.uploadFile(token, secondFileToBeAdded, fileContent);

                assertEquals("User must have 2 files.", 2,
                        fileRepository.findAllUserFiles(userId).size());

                final File thirdFileToBeAdded =
                        new File("fileName" + currentIndex, new FileSize(256));

                fileService.uploadFile(token, thirdFileToBeAdded, fileContent);

                assertEquals("User must have 3 files.", 3,
                        fileRepository.findAllUserFiles(userId).size());

                fileService.deleteFile(token, secondFileToBeAdded.getFileId());

                assertEquals("User must have 2 files.", 2,
                        fileRepository.findAllUserFiles(userId).size());

                return user;
            });

            futureList.add(future);
        }

        for (Future future: futureList) {

            future.get();
        }

        assertEquals("Users number must be " + threadPoolSize, threadPoolSize,
                userRepository.findAllRegisteredUsers().size());
    }
}
