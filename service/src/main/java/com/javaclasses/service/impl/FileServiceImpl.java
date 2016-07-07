package com.javaclasses.service.impl;

import com.javaclasses.dao.entity.File;
import com.javaclasses.dao.entity.User;
import com.javaclasses.dao.repository.FileRepository;
import com.javaclasses.dao.repository.UserRepository;
import com.javaclasses.dao.tinytype.SecurityToken;
import com.javaclasses.service.FileService;
import com.javaclasses.service.UserNotAuthorizedException;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;

/**
 * Implementation of {@link FileService} interface
 */
public class FileServiceImpl implements FileService {

    private final FileRepository fileRepository;
    private final UserRepository userRepository;

    public FileServiceImpl(FileRepository fileRepository, UserRepository userRepository) {
        this.fileRepository = fileRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void uploadFile(SecurityToken token, File file, InputStream inputStream)
            throws UserNotAuthorizedException, IOException {

        final User user = userRepository.findLoggedUserBySecurityToken(token);

        if (user == null) {

            throw new UserNotAuthorizedException("User must be authorized to upload files.");
        }

        fileRepository.createFile(file, user, inputStream);
    }

    @Override
    public Collection<File> findAllFilesOfCurrentUser(SecurityToken token)
            throws UserNotAuthorizedException {

        final User user = userRepository.findLoggedUserBySecurityToken(token);

        if (user == null) {

            throw new UserNotAuthorizedException("User not authorized.");
        }

        return fileRepository.findAllUserFiles(user);
    }

    @Override
    public InputStream downloadFile(SecurityToken token, File file)
            throws UserNotAuthorizedException {

        final User user = userRepository.findLoggedUserBySecurityToken(token);

        if (user == null) {

            throw new UserNotAuthorizedException("User must be authorized to download files.");
        }

        return fileRepository.downloadFile(file);
    }

    @Override
    public void deleteFile(SecurityToken token, File file)
            throws UserNotAuthorizedException {

        final User user = userRepository.findLoggedUserBySecurityToken(token);

        if (user == null) {

            throw new UserNotAuthorizedException("User must be authorized to delete files.");
        }

        fileRepository.deleteFile(file.getFileId());
    }
}
