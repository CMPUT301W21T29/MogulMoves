package com.example.mogulmoves;

/**
 * Class to represent a message from a user with some text.
 */
public class Message extends SavedObject {

    int user;
    String text;

    /**
     * Creates the message.
     *
     * @param user the id of the user that created the message
     * @param text the text of the message
     */
    public Message(int user, String text) {
        super();

        this.user = user;
        this.text = text;
    }

    /**
     * Creates the message with a set id.
     *
     * @param id the object id of the message
     * @param user the id of the user that created the message
     * @param text the text of the message
     */
    public Message(int id, int user, String text) {
        super(id);

        this.user = user;
        this.text = text;
    }

    /**
     * Gets the user that created this message.
     *
     * @return the id of the user that created the message
     */
    public int getUser() {
        return user;
    }

    /**
     * Gets the text of the message.
     *
     * @return the text of the message
     */
    public String getText() {
        return text;
    }
}
