package com.example.mogulmoves;

import java.util.HashMap;

public class ExperimentSerializer implements Serializer {

    public HashMap<String, Object> serialize(Object object){

        HashMap<String, Object> map = new HashMap<>();
        Experiment experiment = (Experiment) object;

        map.put("description", experiment.getDescription());
        map.put("region", experiment.getRegion());
        map.put("minTrials", experiment.getMinTrials());

        if(object instanceof BinomialExperiment){
            map.put("type", 0);
        }else if(object instanceof NonNegativeCountExperiment &&
                !(object instanceof IntegerCountExperiment)){
            map.put("type", 1);
        }else if(object instanceof IntegerCountExperiment){
            map.put("type", 2);
        }else if(object instanceof MeasureExperiment){
            map.put("type", 3);
        }

        return map;

    }

    public Experiment fromSerialized(HashMap<String, Object> map){

        Experiment experiment;

        String description = (String) map.get("description");
        String region = (String) map.get("region");
        int minTrials = (int) map.get("minTrials");
        int type = (int) map.get("type");

        if(type == 0) {
            experiment = new BinomialExperiment(description, region, minTrials);

        }else if(type == 1){
            experiment = new NonNegativeCountExperiment(description, region, minTrials);

        }else if (type == 2){
            experiment = new IntegerCountExperiment(description, region, minTrials);

        }else {
            experiment = new MeasureExperiment(description, region, minTrials);
        }

        return experiment;

    }
}
