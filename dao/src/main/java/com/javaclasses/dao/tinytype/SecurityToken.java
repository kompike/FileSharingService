package com.javaclasses.dao.tinytype;

/**
 * Tiny type for security token of logged user
 */
public class SecurityToken {

    private final long token;

    public SecurityToken(long token) {
        this.token = token;
    }

    public long getToken() {
        return token;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SecurityToken)) return false;

        SecurityToken that = (SecurityToken) o;

        return getToken() == that.getToken();

    }

    @Override
    public int hashCode() {
        return (int) (getToken() ^ (getToken() >>> 32));
    }

    @Override
    public String toString() {

        return String.valueOf(token);
    }
}
