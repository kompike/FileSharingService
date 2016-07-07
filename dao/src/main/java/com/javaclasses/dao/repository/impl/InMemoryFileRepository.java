package com.javaclasses.dao.repository.impl;

import com.google.common.io.ByteStreams;
import com.javaclasses.dao.entity.File;
import com.javaclasses.dao.entity.User;
import com.javaclasses.dao.repository.FileRepository;
import com.javaclasses.dao.tinytype.FileId;
import com.javaclasses.dao.tinytype.UserId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Implementation of {@link FileRepository} interface for in memory data
 */
public class InMemoryFileRepository implements FileRepository {

    private final Logger log = LoggerFactory.getLogger(InMemoryFileRepository.class);

    private volatile long fileIdCounter;

    private final Map<Long, File> files = new ConcurrentHashMap<>();

    private final Map<FileId, byte[]> filesContent = new ConcurrentHashMap<>();

    public InMemoryFileRepository() {
        fileIdCounter = files.size();
    }

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

        files.put(fileIdCounter++, file);

        if (log.isInfoEnabled()) {
            log.info("Start uploading file content to the storage...");
        }

        final byte[] bytes;

        try {

            bytes = ByteStreams.toByteArray(inputStream);

        } finally {

            inputStream.close();
        }

        filesContent.put(file.getFileId(), bytes);

        if (log.isInfoEnabled()) {
            log.info("File content successfully added to the storage.");
        }

        if (log.isInfoEnabled()) {
            log.info("New file successfully added.");
        }


    }

    @Override
    public File findFileById(FileId fileId) {

        if (log.isInfoEnabled()) {
            log.info("Looking for file with id: " + fileId);
        }

        return files.get(fileId.getId());
    }

    @Override
    public Collection<File> findAllUserFiles(UserId userId) {

        if (log.isInfoEnabled()) {
            log.info("Getting the list of all files in memory...");
        }

        final Collection<File> uploadedFiles = files.values();

        final Collection<File> userFiles = new ArrayList<>();

        if (log.isInfoEnabled()) {
            log.info("Looking for files of current user...");
        }

        for (File file : uploadedFiles) {

            final User fileOwner = file.getFileOwner();

            if (fileOwner.getId().equals(userId)) {

                userFiles.add(file);
            }
        }

        if (log.isInfoEnabled()) {
            log.info("All files of current user found.");
        }

        return userFiles;
    }

    @Override
    public byte[] getFileContent(FileId fileId) {

        return filesContent.get(fileId);
    }

    @Override
    public synchronized InputStream downloadFile(FileId fileId) {

        if (log.isInfoEnabled()) {
            log.info("Start downloading file...");
        }

        final byte[] result = getFileContent(fileId);

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
            log.info("Removing file from files map...");
        }

        files.remove(fileID.getId());

        if (log.isInfoEnabled()) {
            log.info("Removing file's content...");
        }

        filesContent.remove(fileID);

        if (log.isInfoEnabled()) {
            log.info("File successfully deleted.");
        }
    }
}
