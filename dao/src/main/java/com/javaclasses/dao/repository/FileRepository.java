package com.javaclasses.dao.repository;

import com.javaclasses.dao.entity.File;
import com.javaclasses.dao.tinytype.SecurityToken;

import java.io.InputStream;

/**
 * Basic interface for CRUD operations with files
 */
public interface FileRepository {

    /**
     * Add new file to the database
     * @param file File to be added
     * @param inputStream Stream to upload file content     *
     */
    void createFile(File file, InputStream inputStream);

    /**
     * Search for file in database by id
     * @param fileId Id of desired file
     * @return File with given id
     */
    File findFileById(long fileId);
}
