package com.javaclasses.dao.tinytype;

/**
 * Tiny type for user's first name
 */
public class FirstName {

    private final String firstName;

    public FirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getFirstName() {
        return firstName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FirstName)) return false;

        FirstName firstName = (FirstName) o;

        return getFirstName().equals(firstName.getFirstName());

    }

    @Override
    public int hashCode() {
        return getFirstName().hashCode();
    }

    @Override
    public String toString() {
        return firstName;
    }
}
