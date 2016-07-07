package com.javaclasses.service;

import com.javaclasses.dao.entity.File;
import com.javaclasses.dao.tinytype.FileId;
import com.javaclasses.dao.tinytype.SecurityToken;

import java.io.IOException;
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
     * @throws UserNotAuthorizedException Occurs when given security token is not valid
     */
    void uploadFile(SecurityToken token, File file, InputStream inputStream)
            throws UserNotAuthorizedException, IOException;

    /**
     * Looking for all files of current user
     * @param token Security token of current user
     * @return List of all files of current user
     * @throws UserNotAuthorizedException Occurs when given security token is not valid
     */
    Collection<File> findAllFilesOfCurrentUser(SecurityToken token)
            throws UserNotAuthorizedException;

    /**
     * Download file from the system
     * @param token Security token of current user
     * @param fileId Identifier of file to be downloaded
     * @return Input stream of downloaded file content
     * @throws UserNotAuthorizedException Occurs when given security token is not valid
     */
    InputStream downloadFile(SecurityToken token, FileId fileId)
            throws UserNotAuthorizedException;

    /**
     * Delete file from the system
     * @param token Security token of current user
     * @param fileId Identifier of file to be downloaded
     * @throws UserNotAuthorizedException Occurs when given security token is not valid
     */
    void deleteFile(SecurityToken token, FileId fileId) throws UserNotAuthorizedException;
}
