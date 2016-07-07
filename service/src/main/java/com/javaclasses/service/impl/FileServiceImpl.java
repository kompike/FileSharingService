package com.javaclasses.service.impl;

import com.javaclasses.dao.entity.File;
import com.javaclasses.dao.entity.User;
import com.javaclasses.dao.repository.FileRepository;
import com.javaclasses.dao.repository.UserRepository;
import com.javaclasses.dao.tinytype.FileId;
import com.javaclasses.dao.tinytype.SecurityToken;
import com.javaclasses.service.FileService;
import com.javaclasses.service.UserNotAuthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;

/**
 * Implementation of {@link FileService} interface
 */
public class FileServiceImpl implements FileService {

    private final Logger log = LoggerFactory.getLogger(FileServiceImpl.class);

    private final FileRepository fileRepository;
    private final UserRepository userRepository;

    public FileServiceImpl(FileRepository fileRepository, UserRepository userRepository) {
        this.fileRepository = fileRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void uploadFile(SecurityToken token, File file, InputStream inputStream)
            throws UserNotAuthorizedException, IOException {

        final User user = findUserBySecurityToken(token, "User must be authorized to upload files.");

        fileRepository.createFile(file, user, inputStream);
    }

    @Override
    public Collection<File> findAllFilesOfCurrentUser(SecurityToken token)
            throws UserNotAuthorizedException {

        final User user = findUserBySecurityToken(token, "User not authorized to search files.");

        return fileRepository.findAllUserFiles(user);
    }

    @Override
    public InputStream downloadFile(SecurityToken token, FileId fileId)
            throws UserNotAuthorizedException {

        findUserBySecurityToken(token, "User must be authorized to download files.");

        return fileRepository.downloadFile(fileId);
    }

    @Override
    public void deleteFile(SecurityToken token, FileId fileId)
            throws UserNotAuthorizedException {

        findUserBySecurityToken(token, "User must be authorized to delete files.");

        fileRepository.deleteFile(fileId);
    }

    private User findUserBySecurityToken(SecurityToken token, String errorMessage)
            throws UserNotAuthorizedException {

        if (log.isInfoEnabled()) {

            log.info("Checking given security token...");
        }

        final User user = userRepository.findLoggedUserBySecurityToken(token);

        if (user == null) {

            if (log.isWarnEnabled()) {

                log.warn(errorMessage);
            }

            throw new UserNotAuthorizedException(errorMessage);
        }

        if (log.isInfoEnabled()) {

            log.info("User successfully found with given security token.");
        }

        return user;
    }
}
