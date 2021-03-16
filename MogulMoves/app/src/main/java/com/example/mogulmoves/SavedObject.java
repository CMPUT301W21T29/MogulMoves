package com.example.mogulmoves;

import java.util.HashMap;

public abstract class SavedObject {

    private final int id;
    public static int nextId;

    public SavedObject() {

        id = nextId;
        nextId++;

        HashMap<String, Object> map = new HashMap<>();
        map.put("nextId", nextId);
        DatabaseHandler.pushData("globals", "globals", map);

    }

    public int getId() {
        return id;
    }
}
