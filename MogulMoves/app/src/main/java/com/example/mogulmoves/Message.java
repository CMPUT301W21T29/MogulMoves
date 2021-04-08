package com.example.mogulmoves;

/**
 * Class to represent a message from a user with some text.
 */
public class Message extends SavedObject {

    private final int user;
    private final String text;
    private final String date;
    private final String time;

    /**
     * Creates the message.
     *
     * @param user the id of the user that created the message
     * @param text the text of the message
     */
    public Message(int user, String text, String date, String time) {
        super();

        this.user = user;
        this.text = text;
        this.date = date;
        this.time = time;
    }

    /**
     * Creates the message with a set id.
     *
     * @param id the object id of the message
     * @param user the id of the user that created the message
     * @param text the text of the message
     */
    public Message(int id, int user, String text, String date, String time) {
        super(id);

        this.user = user;
        this.text = text;
        this.date = date;
        this.time = time;
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

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }
}
