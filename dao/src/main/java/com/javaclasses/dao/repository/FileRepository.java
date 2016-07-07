package com.javaclasses.dao.repository;

import com.javaclasses.dao.entity.File;
import com.javaclasses.dao.entity.User;
import com.javaclasses.dao.tinytype.FileId;
import com.javaclasses.dao.tinytype.UserId;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;

/**
 * Basic interface for CRUD operations with files
 */
public interface FileRepository {

    /**
     * Add new file to the database
     * @param file File to be added
     * @param user Current user
     * @param inputStream Stream to upload file content     *
     */
    void createFile(File file, User user, InputStream inputStream) throws IOException;

    /**
     * Search for file in database by id
     * @param fileId Id of desired file
     * @return File with given id
     */
    File findFileById(FileId fileId);

    /**
     * Looking for all files of given user
     * @param userId Id of current user
     * @return List of all files of given user
     */
    Collection<File> findAllUserFiles(UserId userId);

    /**
     * Browse file content by file identifier
     * @param fileId Identifier of desired file
     * @return Content of file in byte array
     */
    byte[] getFileContent(FileId fileId);

    /**
     * Downloads given file content
     * @param fileId Identifier of file to be downloaded
     * @return Input stream with given file content
     */
    InputStream downloadFile(FileId fileId);

    /**
     * Deletes given file from the database
     * @param fileId Identifier of file to be deleted
     */
    void deleteFile(FileId fileId);
}
