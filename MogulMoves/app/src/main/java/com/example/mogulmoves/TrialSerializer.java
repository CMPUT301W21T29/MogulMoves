package com.example.mogulmoves;

import java.util.HashMap;

/**
 * Class to convert Trial objects into savable data and vice-versa.
 */
public class TrialSerializer extends Serializer<Trial> {

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
        map.put("timestamp", trial.getTimestamp());

        double[] trialLocation = trial.getTrialLocation();
        map.put("locationLat", trialLocation[0]);
        map.put("locationLong", trialLocation[1]);

        if(trial instanceof BinomialTrial){

            map.put("type", 0);
            map.put("isSuccess", ((BinomialTrial) trial).getIsSuccess());

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
        long timestamp = (long) map.get("timestamp");
        double[] trialLocation = {convertToDouble(map.get("locationLat")), convertToDouble(map.get("locationLong"))};

        if(type == 0) {

            boolean isSuccess = (boolean) map.get("isSuccess");
            trial = new BinomialTrial(id, timestamp, owner, isSuccess, trialLocation);

        } else if(type == 1) {

            int count = (int) (long) map.get("count");
            trial = new NonNegativeCountTrial(id, timestamp, owner, count, trialLocation);

        } else if(type == 2) {

            int count = (int) (long) map.get("count");
            trial = new IntegerCountTrial(id, timestamp, owner, count, trialLocation);

        }  else {

            float measurement = (float) (double) map.get("measurement");
            trial = new MeasureTrial(id, timestamp, owner, measurement, trialLocation);
        }

        return trial;

    }
}