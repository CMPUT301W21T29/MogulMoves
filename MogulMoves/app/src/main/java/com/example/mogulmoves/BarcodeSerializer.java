package com.example.mogulmoves;

import java.util.HashMap;
import java.util.List;

/**
 * Class to convert Barcode objects into savable data and vice-versa.
 */
public class BarcodeSerializer extends Serializer<Barcode> {

    /**
     * Converts the necessary data from a User into a HashMap.
     *
     * @param barcode a Barcode object to have it's data pulled and converted
     * @return a HashMap containing key/value pairs of all the necessary data
     */
    public HashMap<String, Object> toData(Barcode barcode) {

        HashMap<String, Object> map = new HashMap<>();

        map.put("experiment", barcode.getExperiment());
        map.put("user", barcode.getUser());
        map.put("code", barcode.getCode());
        map.put("action", barcode.getAction());
        map.put("id", barcode.getId());

        return map;

    }

    /**
     * Converts a HashMap of object data into a User.
     *
     * @param map a HashMap containing all the necessary key/value pairs to construct the barcode
     * @return a Barcode object with the properties and attributes specified by the data
     */
    public Barcode fromData(HashMap<String, Object> map ){

        int experiment = (int) (long) map.get("experiment");
        int user = (int) (long) map.get("user");
        String code = (String) map.get("code");
        String action = (String) map.get("action");
        int id = (int) (long) map.get("id");

        return new Barcode(id, experiment, user, code, action);

    }
}
