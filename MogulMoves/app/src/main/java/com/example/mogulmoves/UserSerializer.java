package com.example.mogulmoves;

import java.util.HashMap;

public class UserSerializer implements Serializer {

    public HashMap<String, Object> toData(SavedObject object){

        HashMap<String, Object> map = new HashMap<>();
        User user = (User) object;

        map.put("username", user.getUsername());
        map.put("email", user.getEmail());
        map.put("phone", user.getPhone());
        map.put("id", user.getId());

        return map;

    }

    public User fromData(HashMap<String, Object> map){

        String username = (String) map.get("username");
        String email = (String) map.get("email");
        String phone = (String) map.get("phone");
        int id = (int) (long) map.get("id");

        User user = new User(username, email, phone);
        user.setId(id);

        return user;

    }
}
