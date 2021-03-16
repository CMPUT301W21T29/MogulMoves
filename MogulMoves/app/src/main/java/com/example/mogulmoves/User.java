package com.example.mogulmoves;

public class User extends SavedObject {

    private String username;
    private String email;
    private String phone;

    public User(String username, String email, String phone) {
        super();

        this.username = username;
        this.email = email;
        this.phone = phone;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }
}
