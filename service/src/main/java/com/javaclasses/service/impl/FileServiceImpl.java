package com.javaclasses.service.impl;

import com.javaclasses.dao.entity.File;
import com.javaclasses.dao.repository.FileRepository;
import com.javaclasses.dao.tinytype.SecurityToken;
import com.javaclasses.service.FileService;
import com.javaclasses.service.IllegalSecurityTokenException;

import java.io.InputStream;

/**
 * Implementation of {@link FileService} interface
 */
public class FileServiceImpl implements FileService {

    private final FileRepository fileRepository;

    public FileServiceImpl(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    @Override
    public void uploadFile(SecurityToken token, File file, InputStream inputStream)
            throws IllegalSecurityTokenException {

    }
}
