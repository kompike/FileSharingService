package com.javaclasses.dao.entity;

import java.sql.Date;

/**
 * Service file entity
 */
public class File {

    private long fileId;
    private String fileName;
    private long fileSize;
    private Date creationDate;
    private User fileOwner;

    public File(String fileName, long fileSize, Date creationDate, User fileOwner) {
        this.fileName = fileName;
        this.fileSize = fileSize;
        this.creationDate = creationDate;
        this.fileOwner = fileOwner;
    }

    public long getFileId() {

        return fileId;
    }

    public void setFileId(long fileId) {
        this.fileId = fileId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public long getFileSize() {
        return fileSize;
    }

    public void setFileSize(long fileSize) {
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

        if (getFileId() != file.getFileId()) return false;
        if (getFileSize() != file.getFileSize()) return false;
        if (!getFileName().equals(file.getFileName())) return false;
        if (!getCreationDate().equals(file.getCreationDate())) return false;
        return getFileOwner().equals(file.getFileOwner());

    }

    @Override
    public int hashCode() {
        int result = (int) (getFileId() ^ (getFileId() >>> 32));
        result = 31 * result + getFileName().hashCode();
        result = 31 * result + (int) (getFileSize() ^ (getFileSize() >>> 32));
        result = 31 * result + getCreationDate().hashCode();
        result = 31 * result + getFileOwner().hashCode();
        return result;
    }
}
