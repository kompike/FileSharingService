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
import java.io.IOException;
import java.io.InputStream;
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

    private final Email email = new Email("email");
    private final Password password = new Password("password");

    private final File file = new File("newFile", 256);

    private final SecurityToken fakeToken = new SecurityToken(11);

    @Test
    public void uploadNewFile()
            throws UserNotAuthorizedException, UserNotFoundException, IOException {

        final SecurityToken token =
                userAuthenticationService.login(email, password);

        fileService.uploadFile(token, file,
                new ByteArrayInputStream(new byte[(int) file.getFileSize()]));

        final File fileFromRepository = fileRepository.findFileById(file.getFileId());

        assertEquals("Files must be equals.", file, fileFromRepository);
    }



    @Test
    public void testIllegalSecurityTokenWhileUploadingFile()
            throws UserNotFoundException, IOException {

        userAuthenticationService.login(email, password);

        try {

            fileService.uploadFile(fakeToken, file,
                    new ByteArrayInputStream(new byte[(int) file.getFileSize()]));

            fail("UserNotAuthorizedException was not thrown.");
        } catch (UserNotAuthorizedException ex) {

            assertEquals("Wrong message for adding new file.",
                    "User must be authorized to upload files.", ex.getMessage());
        }

    }

    @Test
    public void findAllFilesOfCurrentUser() throws UserNotFoundException, UserNotAuthorizedException {

        final SecurityToken token =
                userAuthenticationService.login(email, password);

        final Collection<File> files = fileService.findAllFilesOfCurrentUser(token);

        assertNotNull("Wrong number of current user's uploaded files.", files);
    }

    @Test
    public void testIllegalSecurityTokenWhileSearchingUserFiles() throws UserNotFoundException {

        try {

            fileService.findAllFilesOfCurrentUser(fakeToken);

            fail("UserNotAuthorizedException was not thrown.");
        } catch (UserNotAuthorizedException ex) {

            assertEquals("Wrong message for searching user files with fake security token.",
                    "User not authorized.", ex.getMessage());
        }

    }

    @Test
    public void downloadFile()
            throws UserNotAuthorizedException, UserNotFoundException, IOException {

        final SecurityToken token =
                userAuthenticationService.login(email, password);

        final User user = userRepository.findLoggedUserBySecurityToken(token);

        fileService.uploadFile(token, file,
                new ByteArrayInputStream(new byte[(int) file.getFileSize()]));

        final File fileFromRepository = fileRepository.findAllUserFiles(user).iterator().next();

        final InputStream inputStream = fileService.downloadFile(token, fileFromRepository);

        assertNotNull("Stream must contain data.", inputStream);
    }



    @Test
    public void testIllegalSecurityTokenWhileDownloadingFile()
            throws UserNotFoundException, UserNotAuthorizedException, IOException {

        final SecurityToken token = userAuthenticationService.login(email, password);

        final User user = userRepository.findLoggedUserBySecurityToken(token);

        fileService.uploadFile(token, file,
                new ByteArrayInputStream(new byte[(int) file.getFileSize()]));

        final File fileFromRepository = fileRepository.findAllUserFiles(user).iterator().next();

        try {

            fileService.downloadFile(fakeToken, fileFromRepository);

            fail("UserNotAuthorizedException was not thrown.");
        } catch (UserNotAuthorizedException ex) {

            assertEquals("Wrong message for downloading file.",
                    "User must be authorized to download files.", ex.getMessage());
        }

    }

}
