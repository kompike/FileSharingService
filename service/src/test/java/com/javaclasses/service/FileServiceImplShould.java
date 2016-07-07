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
import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.util.Collection;

import static org.junit.Assert.*;

public class FileServiceImplShould {

    private final FileRepository fileRepository = new InMemoryFileRepository();
    private final UserRepository userRepository = new InMemoryUserRepository();
    private final FileService fileService = new FileServiceImpl(fileRepository, userRepository);

    private final File file = new File("newFile", new FileSize(256));

    private final InputStream fileContent =
            new ByteArrayInputStream(new byte[256]);

    private final SecurityToken fakeToken = new SecurityToken(11);

    private SecurityToken token;

    public FileServiceImplShould() throws FileNotFoundException {
    }

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

    @Before
    public void authorizeUser() throws UserAlreadyExistsException, UserNotFoundException {

        final UserAuthenticationService userAuthenticationService =
                new UserAuthenticationServiceImpl(userRepository);

        final Email email = new Email("email");
        final Password password = new Password("password");

        token = userAuthenticationService.login(email, password);

    }

    @Test
    public void uploadNewFile()
            throws UserNotAuthorizedException, UserNotFoundException, IOException {

        fileService.uploadFile(token, file, fileContent);

        final FileId fileId = file.getFileId();

        final File fileFromRepository = fileRepository.findFileById(fileId);

        assertEquals("Files must be equals.", file, fileFromRepository);

        assertNotNull("Uploaded file content can not be null.",
                fileRepository.getFileContent(fileId));
    }


    @Test
    public void testIllegalSecurityTokenWhileUploadingFile()
            throws UserNotFoundException, IOException {

        try {

            fileService.uploadFile(fakeToken, file, fileContent);

            fail("UserNotAuthorizedException was not thrown.");
        } catch (UserNotAuthorizedException ex) {

            assertEquals("Wrong message for adding new file.",
                    "User must be authorized to upload files.", ex.getMessage());
        }

    }

    @Test
    public void findAllFilesOfCurrentUser() throws UserNotFoundException, UserNotAuthorizedException {

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
                    "User not authorized to search files.", ex.getMessage());
        }

    }

    @Test
    public void downloadFile()
            throws UserNotAuthorizedException, UserNotFoundException, IOException {

        final User user = userRepository.findLoggedUserBySecurityToken(token);

        fileService.uploadFile(token, file, fileContent);

        final File fileFromRepository = fileRepository.findAllUserFiles(user).iterator().next();

        final InputStream inputStream =
                fileService.downloadFile(token, fileFromRepository.getFileId());

        assertNotNull("Stream must contain data.", inputStream);
    }


    @Test
    public void testIllegalSecurityTokenWhileDownloadingFile()
            throws UserNotFoundException, IOException {

        final User user = userRepository.findLoggedUserBySecurityToken(token);

        try {

            fileService.uploadFile(token, file, fileContent);

            final File fileFromRepository = fileRepository.findAllUserFiles(user).iterator().next();

            fileService.downloadFile(fakeToken, fileFromRepository.getFileId());

            fail("UserNotAuthorizedException was not thrown.");
        } catch (UserNotAuthorizedException ex) {

            assertEquals("Wrong message for downloading file.",
                    "User must be authorized to download files.", ex.getMessage());
        }

    }

    @Test
    public void deleteFile()
            throws UserNotAuthorizedException, UserNotFoundException, IOException {

        fileService.uploadFile(token, file, fileContent);

        fileService.deleteFile(token, file.getFileId());

        assertNull("File was not deleted.", fileRepository.findFileById(file.getFileId()));
    }


    @Test
    public void testIllegalSecurityTokenWhileDeletingFile()
            throws UserNotFoundException, IOException {

        try {

            fileService.uploadFile(token, file, fileContent);

            fileService.deleteFile(fakeToken, file.getFileId());

            fail("UserNotAuthorizedException was not thrown.");
        } catch (UserNotAuthorizedException ex) {

            assertEquals("Wrong message for deleting file.",
                    "User must be authorized to delete files.", ex.getMessage());
        }

    }

}
