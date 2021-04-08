package com.example.mogulmoves;

import java.util.HashMap;

/**
 * Interface for serializers to convert objects into savable data and vice-versa.
 */
public abstract class Serializer<ObjectType> {

    /**
     * Converts the necessary data from an object into a HashMap.
     *
     * @param object an object to have it's data pulled and converted
     * @return a HashMap containing key/value pairs of all the necessary data
     */
    abstract HashMap<String, Object> toData(ObjectType object);

    /**
     * Converts a HashMap of object data into an object.
     *
     * @param map a HashMap containing all the necessary key/value pairs to construct the object
     * @return an object with the properties and attributes specified by the data
     */
    abstract ObjectType fromData(HashMap<String, Object> map);

    protected double convertToDouble(Object value) {
        try {
            return (double) value;
        } catch (java.lang.ClassCastException e) {
            return (double) (int) (long) value;
        }
    }

}
