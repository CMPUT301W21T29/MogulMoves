package com.example.mogulmoves;

import java.util.HashMap;
import java.util.List;

import static java.sql.Types.NULL;

/**
 * Class to convert Experiment objects into savable data and vice-versa.
 */
public class ExperimentSerializer implements Serializer<Experiment> {

    /**
     * Converts the necessary data from an Experiment into a HashMap.
     *
     * @param experiment an Experiment object to have it's data pulled and converted
     * @return a HashMap containing key/value pairs of all the necessary data
     */
    public HashMap<String, Object> toData(Experiment experiment){

        HashMap<String, Object> map = new HashMap<>();

        map.put("description", experiment.getDescription());
        map.put("region", experiment.getRegion());
        map.put("minTrials", experiment.getMinTrials());
        map.put("locationRequired", experiment.getLocationRequired());
        map.put("active", experiment.getActive());
        map.put("owner", experiment.getOwner());
        map.put("trials", experiment.getTrials());
        map.put("messages", experiment.getMessages());
        map.put("id", experiment.getId());

        if(experiment instanceof BinomialExperiment){
            map.put("type", 0);

        } else if(experiment instanceof NonNegativeCountExperiment &&
                !(experiment instanceof IntegerCountExperiment)) {
            map.put("type", 1);

        } else if(experiment instanceof IntegerCountExperiment) {
            map.put("type", 2);

        } else if(experiment instanceof MeasureExperiment) {
            map.put("type", 3);
        }

        return map;

    }

    /**
     * Converts a HashMap of object data into an Experiment.
     *
     * @param map a HashMap containing all the necessary key/value pairs to construct the message
     * @return an Experiment object with the properties and attributes specified by the data
     */
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

        List<Long> trials = (List<Long>) map.get("trials");
        List<Long> messages = (List<Long>) map.get("messages");

        if(type == 0) {
            experiment = new BinomialExperiment(id, owner, description, region,
                    minTrials, locationRequired);

        } else if(type == 1) {
            experiment = new NonNegativeCountExperiment(id, owner, description, region,
                    minTrials, locationRequired);

        } else if (type == 2) {
            experiment = new IntegerCountExperiment(id, owner, description, region,
                    minTrials, locationRequired);

        } else {
            experiment = new MeasureExperiment(id, owner, description, region,
                    minTrials, locationRequired);
        }

        experiment.setActive(active);

        try {
            for(long trial: trials){
                experiment.addTrial((int) trial);
            }
        } catch (java.lang.NullPointerException e) {
        }

        try {
            for (long message : messages) {
                experiment.addMessage((int) message);
            }
        } catch (java.lang.NullPointerException e) {
        }

        return experiment;

    }
}
