package com.example.mogulmoves;

import java.util.HashMap;

/**
 * Abstract class for all objects that will be saved in the database.
 */
public abstract class SavedObject {

    private final int id;

    /**
     * Grabs a new id for the object.
     * Updates the next available id and pushes it to the database.
     */
    public SavedObject() {

        ObjectContext.nextId++;
        id = ObjectContext.nextId;

        HashMap<String, Object> map = new HashMap<>();
        map.put("nextId", ObjectContext.nextId);
        DatabaseHandler.pushData("globals", "globals", map);

    }

    /**
     * Sets the id for the object, assumes id does not already exist in the system.
     */
    public SavedObject(int id) {
        this.id = id;
    }

    /**
     * Gets the id of the object.
     *
     * @return The current object id
     */
    public int getId() {
        return id;
    }
}
