package com.example.mogulmoves;

import java.util.HashMap;
import java.util.List;

/**
 * Class to convert User objects into savable data and vice-versa.
 */
public class UserSerializer implements Serializer<User> {

    /**
     * Converts the necessary data from a User into a HashMap.
     *
     * @param user an Experiment object to have it's data pulled and converted
     * @return a HashMap containing key/value pairs of all the necessary data
     */
    public HashMap<String, Object> toData(User user){

        HashMap<String, Object> map = new HashMap<>();

        map.put("username", user.getUsername());
        map.put("email", user.getEmail());
        map.put("phone", user.getPhone());
        map.put("id", user.getId());
        map.put("subscribed", user.getSubscribed());
        map.put("installationId", user.getInstallationId());

        return map;

    }

    /**
     * Converts a HashMap of object data into a User.
     *
     * @param map a HashMap containing all the necessary key/value pairs to construct the message
     * @return a User object with the properties and attributes specified by the data
     */
    public User fromData(HashMap<String, Object> map){

        String installationId = (String) map.get("installationId");
        String username = (String) map.get("username");
        String email = (String) map.get("email");
        String phone = (String) map.get("phone");
        int id = (int) (long) map.get("id");

        User user = new User(id, installationId, username, email, phone);

        try {
            for (long experiment : (List<Integer>) map.get("subscribed")) {
                user.addSubscription((int) experiment);
            }
        } catch (java.lang.NullPointerException e) {
        }

        return user;

    }
}
