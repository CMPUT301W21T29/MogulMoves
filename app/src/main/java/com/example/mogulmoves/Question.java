package com.example.mogulmoves;

import java.util.ArrayList;

/**
 * Class to represent a question, with a list of reply messages.
 */
public class Question extends Message {

    private final ArrayList<Integer> replies;

    /**
     * Creates the question.
     *
     * @param user the id of the user that created the message
     * @param text the text of the message
     */
    public Question(int user, String text) {
        super(user, text);

        replies = new ArrayList<>();
    }

    /**
     * Creates the question with a set id.
     *
     * @param id the object id of the question
     * @param user the id of the user that created the message
     * @param text the text of the message
     */
    public Question(int id, int user, String text) {
        super(id, user, text);

        replies = new ArrayList<>();
    }

    /**
     * Adds a reply to the list of replies
     *
     * @param reply the id of a reply
     */
    public void addReply(int reply) {
        replies.add(reply);
    }

    /**
     * Returns the list of replies.
     *
     * @return the list of a replies
     */
    public ArrayList<Integer> getReplies() {
        return replies;
    }
}
