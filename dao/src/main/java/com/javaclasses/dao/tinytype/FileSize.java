package com.javaclasses.dao.tinytype;

/**
 * Tiny type for file size
 */
public class FileSize {

    private final long fileSize;

    public FileSize(long fileSize) {
        this.fileSize = fileSize;
    }

    public long getSize() {
        return fileSize;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FileSize)) return false;

        FileSize fileSize1 = (FileSize) o;

        return getSize() == fileSize1.getSize();

    }

    @Override
    public int hashCode() {
        return (int) (getSize() ^ (getSize() >>> 32));
    }

    @Override
    public String toString() {
        return String.valueOf(fileSize);
    }
}
