package com.javaclasses.dao.entity;

import com.javaclasses.dao.tinytype.FileId;
import com.javaclasses.dao.tinytype.FileSize;

import java.sql.Date;

/**
 * Entity of service file
 */
public class File {

    private FileId fileId;
    private String fileName;
    private FileSize fileSize;
    private Date creationDate;
    private User fileOwner;

    public File(String fileName, FileSize fileSize) {
        this.fileName = fileName;
        this.fileSize = fileSize;
    }

    public FileId getFileId() {
        return fileId;
    }

    public void setFileId(FileId fileId) {
        this.fileId = fileId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public FileSize getFileSize() {
        return fileSize;
    }

    public void setFileSize(FileSize fileSize) {
        this.fileSize = fileSize;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public User getFileOwner() {
        return fileOwner;
    }

    public void setFileOwner(User fileOwner) {
        this.fileOwner = fileOwner;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof File)) return false;

        File file = (File) o;

        if (!getFileId().equals(file.getFileId())) return false;
        if (!getFileName().equals(file.getFileName())) return false;
        if (!getFileSize().equals(file.getFileSize())) return false;
        if (!getCreationDate().equals(file.getCreationDate())) return false;
        return getFileOwner().equals(file.getFileOwner());

    }

    @Override
    public int hashCode() {
        int result = getFileId().hashCode();
        result = 31 * result + getFileName().hashCode();
        result = 31 * result + getFileSize().hashCode();
        result = 31 * result + getCreationDate().hashCode();
        result = 31 * result + getFileOwner().hashCode();
        return result;
    }
}
