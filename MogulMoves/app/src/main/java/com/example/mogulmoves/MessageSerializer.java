package com.example.mogulmoves;

import java.util.HashMap;
import java.util.List;

public class MessageSerializer implements Serializer {

    public HashMap<String, Object> toData(SavedObject object) {

        HashMap<String, Object> map = new HashMap<>();
        Message message = (Message) object;
        int type;

        map.put("text", message.getText());
        map.put("user", message.getUser());
        map.put("id", message.getId());

        if(object instanceof Question){

            map.put("replies", ((Question) message).getReplies());
            type = 1;

        } else {
            type = 0;
        }

        map.put("type", type);

        return map;

    }

    public SavedObject fromData(HashMap<String, Object> map) {

        Message message;

        String text = (String) map.get("text");
        int user = (int) (long) map.get("user");
        int type = (int) (long) map.get("type");
        int id =  (int) (long) map.get("type");

        if (id == 0){
            message = new Message(text);

        } else {

            message = new Question(text);

            for(int messageId: (List<Integer>) map.get("replies")) {
                ((Question) message).addReply(messageId);
            }
        }

        message.setUser(user);

        return message;

    }
}
