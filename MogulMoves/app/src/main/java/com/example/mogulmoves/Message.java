package com.example.mogulmoves;

public class Message extends SavedObject {

    int user;
    String text;

    public Message(String text) {
        this.text = text;
    }

    public int getUser() {
        return user;
    }

    public String getText() {
        return text;
    }

    public void setUser(int user) {
        this.user = user;
    }
}
