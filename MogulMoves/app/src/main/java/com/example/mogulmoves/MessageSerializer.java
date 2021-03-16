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
        int type;

        map.put("text", message.getText());
        map.put("user", message.getUser());
        map.put("id", message.getId());

        if(message instanceof Question) {

            map.put("replies", ((Question) message).getReplies());
            type = 1;

        } else {
            type = 0;
        }

        map.put("type", type);

        return map;

    }

    /**
     * Converts a HashMap of object data into a Message.
     *
     * @param map a HashMap containing all the necessary key/value pairs to construct the message
     * @return a Message object with the properties and attributes specified by the data
     */
    public Message fromData(HashMap<String, Object> map) {

        Message message;

        String text = (String) map.get("text");
        int user = (int) (long) map.get("user");
        int type = (int) (long) map.get("type");
        int id =  (int) (long) map.get("type");

        if (type == 0) {
            message = new Message(id, user, text);

        } else {

            message = new Question(id, user, text);

            for(int messageId: (List<Integer>) map.get("replies")) {
                ((Question) message).addReply(messageId);
            }
        }

        return message;

    }
}
