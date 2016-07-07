package com.javaclasses.dao.repository;

import com.javaclasses.dao.entity.File;
import com.javaclasses.dao.tinytype.SecurityToken;

/**
 * Basic interface for CRUD operations with files
 */
public interface FileRepository {

    /**
     * Add new file to the database
     * @param file File to be added
     */
    void createFile(File file);

    /**
     * Search for file in database by id
     * @param token Security token of current user
     * @param fileId Id of desired file
     * @return File with given id
     */
    File findFileById(SecurityToken token, long fileId);
}
