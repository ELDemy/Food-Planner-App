package com.dmy.foodplannerapp.data.model;

public class CustomAuthCredentials {
    private final String email;
    private final String password;
    private final String name;

    public CustomAuthCredentials(String email, String password) {
        name = null;
        this.email = email;
        this.password = password;
    }

    public CustomAuthCredentials(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }
}
