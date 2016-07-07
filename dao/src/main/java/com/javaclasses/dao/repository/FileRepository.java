package com.javaclasses.dao.repository;

import com.javaclasses.dao.entity.File;
import com.javaclasses.dao.entity.User;
import com.javaclasses.dao.tinytype.SecurityToken;

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
    File findFileById(long fileId);

    /**
     * Looking for all files of given user
     * @param user Current user
     * @return List of all files of given user
     */
    Collection<File> findAllUserFiles(User user);

    /**
     * Downloads given file content
     * @param file File to be downloaded
     * @return Input stream with given file content
     */
    InputStream downloadFile(File file);
}
