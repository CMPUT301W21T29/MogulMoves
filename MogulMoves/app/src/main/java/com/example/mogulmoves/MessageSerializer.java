package com.example.mogulmoves;

import java.util.HashMap;
import java.util.List;

/**
 * Class to convert Message objects into savable data and vice-versa.
 */
public class MessageSerializer implements Serializer<Message> {

    /**
     * Converts the necessary data from a Message into a HashMap.
     *
     * @param message a Message object to have it's data pulled and converted
     * @return a HashMap containing key/value pairs of all the necessary data
     */
    public HashMap<String, Object> toData(Message message) {

        HashMap<String, Object> map = new HashMap<>();

        map.put("text", message.getText());
        map.put("user", message.getUser());
        map.put("id", message.getId());

        return map;

    }

    /**
     * Converts a HashMap of object data into a Message.
     *
     * @param map a HashMap containing all the necessary key/value pairs to construct the message
     * @return a Message object with the properties and attributes specified by the data
     */
    public Message fromData(HashMap<String, Object> map) {

        String text = (String) map.get("text");
        int user = (int) (long) map.get("user");
        int id =  (int) (long) map.get("id");

        Message message = new Message(id, user, text);

        return message;

    }
}
