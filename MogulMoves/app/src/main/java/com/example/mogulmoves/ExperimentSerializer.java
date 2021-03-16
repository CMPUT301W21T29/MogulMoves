package com.example.mogulmoves;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ExperimentSerializer implements Serializer {

    public HashMap<String, Object> toData(SavedObject object){

        HashMap<String, Object> map = new HashMap<>();
        Experiment experiment = (Experiment) object;

        map.put("description", experiment.getDescription());
        map.put("region", experiment.getRegion());
        map.put("minTrials", experiment.getMinTrials());
        map.put("locationRequired", experiment.getLocationRequired());
        map.put("active", experiment.getActive());
        map.put("owner", experiment.getOwner());
        map.put("trials", experiment.getTrials());

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

    public Experiment fromData(HashMap<String, Object> map){

        Experiment experiment;

        String description = (String) map.get("description");
        String region = (String) map.get("region");
        int minTrials = (int) (long) map.get("minTrials");
        int type = (int) (long) map.get("type");
        boolean locationRequired = (boolean) map.get("locationRequired");
        boolean active = (boolean) map.get("active");
        int owner = (int) (long) map.get("owner");
        int id = (int) (long) map.get("id");
        List<Integer> trials = (List<Integer>) map.get("trials");

        if(type == 0) {
            experiment = new BinomialExperiment(description, region, minTrials, locationRequired);

        }else if(type == 1){
            experiment = new NonNegativeCountExperiment(description, region, minTrials, locationRequired);

        }else if (type == 2){
            experiment = new IntegerCountExperiment(description, region, minTrials, locationRequired);

        }else {
            experiment = new MeasureExperiment(description, region, minTrials, locationRequired);
        }

        experiment.setActive(active);
        experiment.setOwner(owner);
        experiment.setId(id);

        for(int trial: trials){
            experiment.addTrial(trial);
        }

        return experiment;

    }
}
