package com.example.mogulmoves;

import java.util.HashMap;

public class UserSerializer implements Serializer {

    public HashMap<String, Object> serialize(Object object){

        HashMap<String, Object> map = new HashMap<>();
        User user = (User) object;

        map.put("username", user.getUsername());
        map.put("email", user.getEmail());
        map.put("phone", user.getPhone());

        return map;

    }

    public User fromSerialized(HashMap<String, Object> map){

        String username = (String) map.get("username");
        String email = (String) map.get("email");
        String phone = (String) map.get("phone");

        User user = new User(username, email, phone);

        return user;

    }

}
