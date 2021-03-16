package com.example.mogulmoves;

import java.util.ArrayList;

public class Question extends Message {

    private ArrayList<Message> replies;

    public Question() {
        super();

        replies = new ArrayList<>();
    }

    public void addReply(Message reply) {

        replies.add(reply);

        //MessageSerializer serializer = new MessageSerializer();
        //DatabaseHandler.pushData("messages", "" + reply.getId(), serializer.toData(reply));

    }

}
