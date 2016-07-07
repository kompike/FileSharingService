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
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.sql.Date;
import java.util.Collection;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

public class FileServiceImplShould {

    private final FileRepository fileRepository = new InMemoryFileRepository();
    private final UserRepository userRepository = new InMemoryUserRepository();
    private final FileService fileService = new FileServiceImpl(fileRepository, userRepository);

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

        fileService.uploadFile(token, file,
                new ByteArrayInputStream(new byte[(int) file.getFileSize()]));

        final File fileFromRepository = fileRepository.findFileById(file.getFileId());

        assertEquals("", file, fileFromRepository);
    }



    @Test
    public void testIllegalSecurityTokenWhileUploadingFile() throws UserNotFoundException {

        final Email email = new Email("email");
        final Password password = new Password("password");

        final SecurityToken token =
                userAuthenticationService.login(email, password);

        final User user = userRepository.findLoggedUserBySecurityToken(token);

        final File file = new File("newFile", 256, new Date(System.currentTimeMillis()), user);

        final SecurityToken fakeToken = new SecurityToken(11);

        try {

            fileService.uploadFile(fakeToken, file,
                    new ByteArrayInputStream(new byte[(int) file.getFileSize()]));

            fail("IllegalSecurityTokenException was not thrown.");
        } catch (IllegalSecurityTokenException ex) {

            assertEquals("Expected and actual security tokens must be equal for new file uploading.",
                    fakeToken, ex.getToken());
        }

    }

    @Test
    public void findAllFilesOfCurrentUser() throws UserNotFoundException, IllegalSecurityTokenException {

        final Email email = new Email("email");
        final Password password = new Password("password");

        final SecurityToken token =
                userAuthenticationService.login(email, password);

        final Collection<File> files = fileService.findAllFilesOfCurrentUser(token);

        assertNotNull("Wrong number of current user's uploaded files.", files);
    }

    @Test
    public void testIllegalSecurityTokenWhileSearchingUserFiles() throws UserNotFoundException {

        final SecurityToken fakeToken = new SecurityToken(11);

        try {

            fileService.findAllFilesOfCurrentUser(fakeToken);

            fail("IllegalSecurityTokenException was not thrown.");
        } catch (IllegalSecurityTokenException ex) {

            assertEquals("Wrong message for searching user files with fake security token.",
                    "Can not find user by given security token", ex.getMessage());
        }

    }

}
