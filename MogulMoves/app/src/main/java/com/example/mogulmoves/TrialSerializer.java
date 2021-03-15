package com.example.mogulmoves;

import java.util.HashMap;

public class TrialSerializer implements Serializer {
    public HashMap<String, Object> serialize(Object object){

        HashMap<String, Object> map = new HashMap<>();
        Trial trial = (Trial) object;

        if(object instanceof BinomialTrial){

            map.put("type", 0);
            map.put("successes", ((BinomialTrial) trial).getSuccesses());
            map.put("failures", ((BinomialTrial) trial).getFailures());

        }else if(object instanceof NonNegativeCountTrial &&
                !(object instanceof IntegerCountTrial)){

            map.put("type", 1);
            map.put("count", ((NonNegativeCountTrial) trial).getCount());

        }else if(object instanceof IntegerCountTrial){

            map.put("type", 2);
            map.put("count", ((IntegerCountTrial) trial).getCount());

        }else if(object instanceof MeasureTrial){

            map.put("type", 3);
            map.put("measurement", ((MeasureTrial) trial).getMeasurement());

        }

        return map;

    }

    public Trial fromSerialized(HashMap<String, Object> map){

        Trial trial;
        int type = (int) map.get("type");

        if(type == 0) {

            int successes = (int) map.get("successes");
            int failures = (int) map.get("failures");
            trial = new BinomialTrial(successes, failures);

        }else if(type == 1){

            int count = (int) map.get("count");
            trial = new NonNegativeCountTrial(count);

        }else if (type == 2){

            int count = (int) map.get("count");
            trial = new IntegerCountTrial(count);

        }else {

            float measurement = (int) map.get("measurement");
            trial = new MeasureTrial(measurement);
        }

        return trial;

    }
}