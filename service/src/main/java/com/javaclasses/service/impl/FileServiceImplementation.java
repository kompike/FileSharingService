package com.javaclasses.service.impl;

import com.javaclasses.dao.entity.File;
import com.javaclasses.dao.tinytype.SecurityToken;
import com.javaclasses.service.FileService;
import com.javaclasses.service.IllegalSecurityTokenException;

import java.io.InputStream;

/**
 * Implementation of {@link FileService} interface
 */
public class FileServiceImplementation implements FileService {

    @Override
    public void uploadFile(SecurityToken token, File file, InputStream inputStream)
            throws IllegalSecurityTokenException {

    }
}
