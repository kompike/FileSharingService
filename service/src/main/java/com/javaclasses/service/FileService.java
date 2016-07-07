package com.javaclasses.service;

import com.javaclasses.dao.entity.File;
import com.javaclasses.dao.tinytype.SecurityToken;

import java.io.InputStream;
import java.util.Collection;

/**
 * Basic interface for file management
 */
public interface FileService {

    /**
     * Upload new file to the system
     * @param token Security token of current user
     * @param file File to be added to the system
     * @param inputStream Stream for saving file content
     * @throws IllegalSecurityTokenException Occurs when given security token is not valid
     */
    void uploadFile(SecurityToken token, File file, InputStream inputStream)
            throws IllegalSecurityTokenException;

    /**
     * Looking for all files of current user
     * @param token Security token of current user
     * @return List of all files of current user
     */
    Collection<File> findAllUserFiles(SecurityToken token);
}
