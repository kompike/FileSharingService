package com.javaclasses.dao.repository.impl;

import com.javaclasses.dao.entity.File;
import com.javaclasses.dao.repository.FileRepository;
import com.javaclasses.dao.tinytype.SecurityToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Implementation of {@link FileRepository} interface for in memory data
 */
public class InMemoryFileRepository implements FileRepository {

    private final Logger log = LoggerFactory.getLogger(InMemoryFileRepository.class);

    private long fileIdCounter;

    private final Map<Long, File> files = new HashMap<>();

    @Override
    public void createFile(SecurityToken token, File file) {

        if (log.isInfoEnabled()) {
            log.info("Start adding new file...");
        }

        file.setFileId(fileIdCounter);

        if (log.isInfoEnabled()) {
            log.info("New file id: " + file.getFileId());
        }

        files.put(fileIdCounter++, file);

        if (log.isInfoEnabled()) {
            log.info("Files map size is: " + files.size());
        }

        if (log.isInfoEnabled()) {
            log.info("New file successfully added.");
        }

    }

    @Override
    public File findFileById(long fileId) {

        if (log.isInfoEnabled()) {
            log.info("Looking for file with id: " + fileId);
        }

        return files.get(fileId);
    }
}
