package com.javaclasses.dao.tinytype;

/**
 * Tiny type for user's password
 */
public class Password {

    private final String password;

    public Password(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Password)) return false;

        Password password = (Password) o;

        return getPassword().equals(password.getPassword());

    }

    @Override
    public int hashCode() {
        return getPassword().hashCode();
    }
}
