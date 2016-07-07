package com.javaclasses.service;

import com.javaclasses.dao.entity.File;
import com.javaclasses.dao.entity.User;
import com.javaclasses.dao.repository.FileRepository;
import com.javaclasses.dao.repository.UserRepository;
import com.javaclasses.dao.repository.impl.InMemoryFileRepository;
import com.javaclasses.dao.repository.impl.InMemoryUserRepository;
import com.javaclasses.dao.tinytype.Email;
import com.javaclasses.dao.tinytype.Password;
import com.javaclasses.dao.tinytype.SecurityToken;
import com.javaclasses.service.impl.FileServiceImpl;
import com.javaclasses.service.impl.UserAuthenticationServiceImpl;
import com.sun.xml.internal.messaging.saaj.util.ByteInputStream;
import org.junit.Assert;
import org.junit.Test;

import java.sql.Date;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.fail;

public class FileServiceImplShould {

    private final FileRepository fileRepository = new InMemoryFileRepository();
    private final FileService fileService = new FileServiceImpl(fileRepository);

    private final UserRepository userRepository = new InMemoryUserRepository();
    private final UserAuthenticationService userAuthenticationService =
            new UserAuthenticationServiceImpl(userRepository);

    @Test
    public void uploadNewFile() throws IllegalSecurityTokenException, UserNotFoundException {

        final Email email = new Email("email");
        final Password password = new Password("password");

        final SecurityToken token =
                userAuthenticationService.login(email, password);

        final User user = userRepository.findLoggedUserBySecurityToken(token);

        final File file = new File("newFile", 256, new Date(System.currentTimeMillis()), user);

        fileService.uploadFile(token, file, new ByteInputStream());

        final File fileFromRepository = fileRepository.findFileById(token, file.getFileId());

        Assert.assertEquals("", file, fileFromRepository);
    }



    @Test
    public void testIllegalSecurityTokenException() throws UserNotFoundException {

        final Email email = new Email("email");
        final Password password = new Password("password");

        final SecurityToken token =
                userAuthenticationService.login(email, password);

        final User user = userRepository.findLoggedUserBySecurityToken(token);

        final File file = new File("newFile", 256, new Date(System.currentTimeMillis()), user);

        final SecurityToken fakeToken = new SecurityToken(11);

        try {

            fileService.uploadFile(fakeToken, file, new ByteInputStream());

            fail("IllegalSecurityTokenException was not thrown.");
        } catch (IllegalSecurityTokenException ex) {

            assertEquals("Expected and actual security tokens must be equal for new file uploading.",
                    fakeToken, ex.getToken());
        }

    }

}
