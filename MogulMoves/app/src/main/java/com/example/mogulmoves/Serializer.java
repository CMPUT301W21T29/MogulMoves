package com.example.mogulmoves;

import java.util.HashMap;

public interface Serializer {
    HashMap<String, Object> serialize(Object object);
    Object fromSerialized(HashMap<String, Object> map);
}
