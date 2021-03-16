package com.example.mogulmoves;

import java.util.HashMap;

public abstract class SavedObject {

    private int id;

    public SavedObject() {

        id = ObjectContext.nextId;
        ObjectContext.nextId++;

        HashMap<String, Object> map = new HashMap<>();
        map.put("nextId", ObjectContext.nextId);
        DatabaseHandler.pushData("globals", "globals", map);

    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
