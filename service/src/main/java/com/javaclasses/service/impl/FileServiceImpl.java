package com.javaclasses.service.impl;

import com.javaclasses.dao.entity.File;
import com.javaclasses.dao.entity.User;
import com.javaclasses.dao.repository.FileRepository;
import com.javaclasses.dao.repository.UserRepository;
import com.javaclasses.dao.tinytype.SecurityToken;
import com.javaclasses.service.FileService;
import com.javaclasses.service.IllegalSecurityTokenException;

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
            throws IllegalSecurityTokenException {

        final User user = userRepository.findLoggedUserBySecurityToken(token);

        if (user == null) {

            throw new IllegalSecurityTokenException(
                    "User with given security token not found", token);
        }

        fileRepository.createFile(file, inputStream);
    }

    @Override
    public Collection<File> findAllUserFiles(SecurityToken token) {
        return null;
    }
}
