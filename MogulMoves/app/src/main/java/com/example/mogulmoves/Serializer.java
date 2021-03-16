package com.example.mogulmoves;

import java.util.HashMap;

public interface Serializer {

    HashMap<String, Object> toData(SavedObject object);
    SavedObject fromData(HashMap<String, Object> map);

}
