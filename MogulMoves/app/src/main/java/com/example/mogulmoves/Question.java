package com.example.mogulmoves;

import java.util.ArrayList;

public class Question extends Message {

    private ArrayList<Integer> replies;

    public Question(String text) {
        super(text);

        replies = new ArrayList<>();
    }

    public void addReply(int reply) {

        replies.add(reply);

        //MessageSerializer serializer = new MessageSerializer();
        //DatabaseHandler.pushData("messages", "" + reply.getId(), serializer.toData(reply));

    }

    public ArrayList<Integer> getReplies() {
        return replies;
    }
}
