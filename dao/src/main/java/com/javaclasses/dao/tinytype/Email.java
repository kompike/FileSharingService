package com.javaclasses.dao.tinytype;

/**
 * Tiny type for user's email
 */
public class Email {

    private final String email;

    public Email(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Email)) return false;

        Email email = (Email) o;

        return getEmail().equals(email.getEmail());

    }

    @Override
    public int hashCode() {
        return getEmail().hashCode();
    }
}
