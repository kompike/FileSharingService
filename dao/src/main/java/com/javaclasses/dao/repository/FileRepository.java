package com.javaclasses.dao.repository;

import com.javaclasses.dao.entity.File;

/**
 * Basic interface for CRUD operations with files
 */
public interface FileRepository {

    /**
     * Add new file to the database
     * @param file File to be added
     */
    void createFile(File file);
}
