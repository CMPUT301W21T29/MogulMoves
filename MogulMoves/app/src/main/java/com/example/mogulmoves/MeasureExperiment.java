package com.example.mogulmoves;

/**
 * Class to represent an experiment using decimal valued trials.
 */
public class MeasureExperiment extends Experiment {

    /**
     * Creates the experiment.
     *
     * @param owner the id of the user that owns the experiment
     * @param description the description of the experiment
     * @param region the region the experiment is taking place
     * @param minTrials the required minimum number of trials of the experiment
     * @param locationRequired whether or not the trials of this experiment require a location
     */
    public MeasureExperiment(int owner, String description, String region,
                              int minTrials, boolean locationRequired, boolean visible) {
        super(owner, description, region, minTrials, locationRequired, visible);
    }

    /**
     * Creates the experiment with a set id.
     *
     * @param id the object id of the experiment
     * @param owner the id of the user that owns the experiment
     * @param description the description of the experiment
     * @param region the region the experiment is taking place
     * @param minTrials the required minimum number of trials of the experiment
     * @param locationRequired whether or not the trials of this experiment require a location
     */
    public MeasureExperiment(int id, int owner, String description, String region,
                              int minTrials, boolean locationRequired, boolean visible) {
        super(id, owner, description, region, minTrials, locationRequired, visible);
    }

    /**
     * Returns the values of every trial in the experiment.
     *
     * @return an array of the values
     */
    protected float[] getValues() {

        int length = trials.size();
        float[] values = new float[length];

        for(int i = 0; i < length; i++) {
            MeasureTrial trial = (MeasureTrial) ObjectContext.getObjectById(trials.get(i));
            values[i] = trial.getMeasurement();
        }

        return values;
    }

    @Override
    public boolean getLocationRequired() {
        return false;
    }
}
