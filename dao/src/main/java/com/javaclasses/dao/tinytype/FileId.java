package com.javaclasses.dao.tinytype;

/**
 * Tiny type for files's unique identifier
 */
public class FileId {

    private final long fileId;

    public FileId(long fileId) {
        this.fileId = fileId;
    }

    public long getId() {
        return fileId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FileId)) return false;

        FileId fileId1 = (FileId) o;

        return getId() == fileId1.getId();

    }

    @Override
    public int hashCode() {
        return (int) (getId() ^ (getId() >>> 32));
    }

    @Override
    public String toString() {
        return String.valueOf(fileId);
    }
}
