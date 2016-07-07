package com.javaclasses.dao.tinytype;

/**
 * Tiny type for user's unique identifier
 */
public class UserId {

    private final long userId;

    public UserId(long userId) {
        this.userId = userId;
    }

    public long getUserId() {
        return userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserId)) return false;

        UserId userId1 = (UserId) o;

        return getUserId() == userId1.getUserId();

    }

    @Override
    public int hashCode() {
        return (int) (getUserId() ^ (getUserId() >>> 32));
    }

    @Override
    public String toString() {
        return String.valueOf(userId);
    }
}
