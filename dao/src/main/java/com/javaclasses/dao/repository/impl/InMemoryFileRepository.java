package com.javaclasses.dao.repository.impl;

import com.google.common.io.ByteStreams;
import com.javaclasses.dao.entity.File;
import com.javaclasses.dao.entity.User;
import com.javaclasses.dao.repository.FileRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Implementation of {@link FileRepository} interface for in memory data
 */
public class InMemoryFileRepository implements FileRepository {

    private final Logger log = LoggerFactory.getLogger(InMemoryFileRepository.class);

    private long fileIdCounter;

    private final Map<Long, File> files = new HashMap<>();

    private final Map<File, byte[]> uploadedFilesContent = new HashMap<>();

    @Override
    public void createFile(File file, User user, InputStream inputStream)
            throws IOException {

        if (log.isInfoEnabled()) {
            log.info("Start adding new file...");
        }

        file.setFileId(fileIdCounter);

        if (log.isInfoEnabled()) {
            log.info("New file id: " + file.getFileId());
        }

        file.setFileOwner(user);
        file.setCreationDate(new Date(System.currentTimeMillis()));

        files.put(fileIdCounter++, file);

        if (log.isInfoEnabled()) {
            log.info("Start uploading file content to the storage...");
        }

        final byte[] bytes = ByteStreams.toByteArray(inputStream);

        uploadedFilesContent.put(file, bytes);

        if (log.isInfoEnabled()) {
            log.info("File content successfully added to the storage.");
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

    @Override
    public Collection<File> findAllUserFiles(User user) {

        final Collection<File> allAvailableFiles = files.values();

        final Collection<File> userFiles = new ArrayList<>();

        for (File file : allAvailableFiles) {

            if (file.getFileOwner().equals(user)) {

                userFiles.add(file);
            }
        }

        return userFiles;
    }

    @Override
    public InputStream downloadFile(File file) {

        final byte[] result = uploadedFilesContent.get(file);

        return new ByteArrayInputStream(result);
    }
}
