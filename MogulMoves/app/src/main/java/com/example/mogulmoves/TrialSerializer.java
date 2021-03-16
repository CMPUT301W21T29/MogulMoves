package com.example.mogulmoves;

import java.util.HashMap;

public class TrialSerializer implements Serializer {
    public HashMap<String, Object> toData(SavedObject object){

        HashMap<String, Object> map = new HashMap<>();
        Trial trial = (Trial) object;

        map.put("id", trial.getId());
        map.put("owner", trial.getExperimenter());

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

    public Trial fromData(HashMap<String, Object> map){

        Trial trial;
        int type = (int) (long) map.get("type");
        int id = (int) (long) map.get("id");

        if(type == 0) {

            int successes = (int) (long) map.get("successes");
            int failures = (int) (long) map.get("failures");
            trial = new BinomialTrial(successes, failures);

        }else if(type == 1){

            int count = (int) (long) map.get("count");
            trial = new NonNegativeCountTrial(count);

        }else if (type == 2){

            int count = (int) (long) map.get("count");
            trial = new IntegerCountTrial(count);

        }else {

            float measurement = (int) (long) map.get("measurement");
            trial = new MeasureTrial(measurement);
        }

        trial.setId(id);

        return trial;

    }
}