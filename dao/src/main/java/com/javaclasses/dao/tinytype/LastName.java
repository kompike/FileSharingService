package com.javaclasses.dao.tinytype;

/**
 * Tiny type for user's last name
 */
public class LastName {

    private final String lastName;

    public LastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLastName() {
        return lastName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LastName)) return false;

        LastName lastName = (LastName) o;

        return getLastName().equals(lastName.getLastName());

    }

    @Override
    public int hashCode() {
        return getLastName().hashCode();
    }

    @Override
    public String toString() {
        return lastName;
    }
}
