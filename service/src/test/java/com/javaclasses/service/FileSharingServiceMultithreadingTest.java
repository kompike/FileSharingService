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

public class FileSharingServiceMultithreadingTest {

    private final FileRepository fileRepository = new InMemoryFileRepository();
    private final UserRepository userRepository = new InMemoryUserRepository();

    private final UserRegistrationService userRegistrationService =
            new UserRegistrationServiceImpl(userRepository);

    private final UserAuthenticationService userAuthenticationService =
            new UserAuthenticationServiceImpl(userRepository);

    private final FileService fileService =
            new FileServiceImpl(fileRepository, userRepository);



    @Test
    public void testFileSharingServiceMultithreading()
            throws InterruptedException, ExecutionException {

        final int threadPoolSize = 50;

        final CountDownLatch startLatch = new CountDownLatch(threadPoolSize);

        final ExecutorService executorService = Executors.newFixedThreadPool(threadPoolSize);

        final List<Future<User>> futureList = new ArrayList<>();

        for (int i = 0; i < threadPoolSize; i++) {

            final int number = i;

            final Future<User> future = executorService.submit(() -> {

                startLatch.countDown();
                startLatch.await();

                final User user = new User(
                        new Email("email" + number), new Password("password" + number),
                        new FirstName("firstName" + number), new LastName("lastName" + number));

                try {
                    userRegistrationService.registerNewUser(user);
                } catch (UserAlreadyExistsException e) {
                    e.getMessage();
                }

                final User userById = userRepository.findUserById(user.getId());

                assertEquals("", user, userById);

                final SecurityToken token =
                        userAuthenticationService.login(new Email("email" + number),
                                new Password("password" + number));

                assertEquals("", "email" + number,
                        userRepository.findLoggedUserBySecurityToken(token).getEmail().getEmail());

                final File file = new File("fileName" + number, new FileSize(256));

                fileService.uploadFile(token, file,
                        new ByteArrayInputStream(new byte[(int) file.getFileSize().getSize()]));

                final File fileFromRepository = fileRepository.findFileById(file.getFileId());

                assertEquals("Files must be equals.", file, fileFromRepository);

                final File secondFile = new File("fileName" + number + 1, new FileSize(256));

                fileService.uploadFile(token, secondFile,
                        new ByteArrayInputStream(new byte[(int) file.getFileSize().getSize()]));

                assertEquals("Files must be equals.", 2, fileRepository.findAllUserFiles(user).size());

                final File thirdFile = new File("fileName" + number + 1, new FileSize(256));

                fileService.uploadFile(token, thirdFile,
                        new ByteArrayInputStream(new byte[(int) file.getFileSize().getSize()]));

                assertEquals("Files must be equals.", 3, fileRepository.findAllUserFiles(user).size());

                fileService.deleteFile(token, secondFile.getFileId());

                assertEquals("Files must be equals.", 2, fileRepository.findAllUserFiles(user).size());

                return user;
            });

            futureList.add(future);
        }

        for (Future future: futureList) {

            future.get();
        }
    }
}
