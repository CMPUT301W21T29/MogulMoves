package com.example.mogulmoves;

import java.util.HashMap;

public interface Serializer {

    public HashMap<String, Object> serialize(Object object);
    public Object fromSerialized(HashMap<String, Object>);

}
