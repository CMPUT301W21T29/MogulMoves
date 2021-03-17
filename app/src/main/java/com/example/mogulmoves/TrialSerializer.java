package com.example.mogulmoves;

import java.util.HashMap;

/**
 * Class to convert Trial objects into savable data and vice-versa.
 */
public class TrialSerializer implements Serializer<Trial> {

    /**
     * Converts the necessary data from a Trial into a HashMap.
     *
     * @param trial a Trial object to have it's data pulled and converted
     * @return a HashMap containing key/value pairs of all the necessary data
     */
    public HashMap<String, Object> toData(Trial trial){

        HashMap<String, Object> map = new HashMap<>();

        map.put("id", trial.getId());
        map.put("owner", trial.getExperimenter());

        if(trial instanceof BinomialTrial){

            map.put("type", 0);
            map.put("successes", ((BinomialTrial) trial).getSuccesses());
            map.put("failures", ((BinomialTrial) trial).getFailures());

        }else if(trial instanceof NonNegativeCountTrial &&
                !(trial instanceof IntegerCountTrial)){

            map.put("type", 1);
            map.put("count", ((NonNegativeCountTrial) trial).getCount());

        }else if(trial instanceof IntegerCountTrial){

            map.put("type", 2);
            map.put("count", ((IntegerCountTrial) trial).getCount());

        }else if(trial instanceof MeasureTrial){

            map.put("type", 3);
            map.put("measurement", ((MeasureTrial) trial).getMeasurement());

        }

        return map;

    }

    /**
     * Converts a HashMap of object data into a Trial.
     *
     * @param map a HashMap containing all the necessary key/value pairs to construct the message
     * @return a Trial object with the properties and attributes specified by the data
     */
    public Trial fromData(HashMap<String, Object> map){

        Trial trial;
        int type = (int) (long) map.get("type");
        int id = (int) (long) map.get("id");
        int owner = (int) (long) map.get("owner");

        if(type == 0) {

            int successes = (int) (long) map.get("successes");
            int failures = (int) (long) map.get("failures");
            trial = new BinomialTrial(id, owner, successes, failures);

        } else if(type == 1) {

            int count = (int) (long) map.get("count");
            trial = new NonNegativeCountTrial(id, owner, count);

        } else if(type == 2) {

            int count = (int) (long) map.get("count");
            trial = new IntegerCountTrial(id, owner, count);

        }  else {

            float measurement = (int) (long) map.get("measurement");
            trial = new MeasureTrial(id, owner, measurement);
        }

        return trial;

    }
}