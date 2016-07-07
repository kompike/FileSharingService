package com.javaclasses.dao.repository.impl;

import com.google.common.io.ByteStreams;
import com.javaclasses.dao.entity.File;
import com.javaclasses.dao.entity.User;
import com.javaclasses.dao.repository.FileRepository;
import com.javaclasses.dao.tinytype.FileId;
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

    private volatile long fileIdCounter;

    private final Map<Long, File> files = new HashMap<>();

    private final Map<File, byte[]> uploadedFilesContent = new HashMap<>();

    @Override
    public synchronized void createFile(File file, User user, InputStream inputStream)
            throws IOException {

        if (log.isInfoEnabled()) {
            log.info("Start adding new file...");
        }

        file.setFileId(new FileId(fileIdCounter));

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
    public synchronized File findFileById(FileId fileId) {

        if (log.isInfoEnabled()) {
            log.info("Looking for file with id: " + fileId);
        }

        return files.get(fileId.getFileId());
    }

    @Override
    public synchronized Collection<File> findAllUserFiles(User user) {

        if (log.isInfoEnabled()) {
            log.info("Getting the list of all files in memory...");
        }

        final Collection<File> allAvailableFiles = files.values();

        final Collection<File> userFiles = new ArrayList<>();

        if (log.isInfoEnabled()) {
            log.info("Looking for files of current user...");
        }

        for (File file : allAvailableFiles) {

            if (file.getFileOwner().equals(user)) {

                userFiles.add(file);
            }
        }

        if (log.isInfoEnabled()) {
            log.info("All files of current user found.");
        }

        return userFiles;
    }

    @Override
    public synchronized InputStream downloadFile(FileId fileId) {

        if (log.isInfoEnabled()) {
            log.info("Start downloading file...");
        }

        final File file = findFileById(fileId);

        final byte[] result = uploadedFilesContent.get(file);

        try {
            return new ByteArrayInputStream(result);
        } finally {

            if (log.isInfoEnabled()) {
                log.info("File successfully downloaded.");
            }

        }
    }

    @Override
    public synchronized void deleteFile(FileId fileID) {

        if (log.isInfoEnabled()) {
            log.info("Searching for file to be deleted...");
        }

        final long fileId = fileID.getFileId();

        final File fileToDelete = files.get(fileId);

        if (log.isInfoEnabled()) {
            log.info("Removing file from files map...");
        }

        files.remove(fileId);

        if (log.isInfoEnabled()) {
            log.info("Removing file's content...");
        }

        uploadedFilesContent.remove(fileToDelete);

        if (log.isInfoEnabled()) {
            log.info("File successfully deleted.");
        }
    }
}
