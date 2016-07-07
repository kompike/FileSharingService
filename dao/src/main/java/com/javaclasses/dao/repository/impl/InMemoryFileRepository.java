package com.javaclasses.dao.repository.impl;

import com.javaclasses.dao.entity.File;
import com.javaclasses.dao.repository.FileRepository;
import com.javaclasses.dao.tinytype.SecurityToken;

/**
 * Implementation of {@link FileRepository} interface for in memory data
 */
public class InMemoryFileRepository implements FileRepository {

    @Override
    public void createFile(File file) {

    }

    @Override
    public File findFileById(SecurityToken token, long fileId) {
        return null;
    }
}
