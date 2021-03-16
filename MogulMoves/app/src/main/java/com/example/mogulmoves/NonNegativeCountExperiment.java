package com.example.mogulmoves;

/**
 * Class to represent an experiment using positive whole number valued trials.
 */
public class NonNegativeCountExperiment extends Experiment {

    /**
     * Creates the experiment.
     *
     * @param owner the id of the user that owns the experiment
     * @param description the description of the experiment
     * @param region the region the experiment is taking place
     * @param minTrials the required minimum number of trials of the experiment
     * @param locationRequired whether or not the trials of this experiment require a location
     */
    public NonNegativeCountExperiment(int owner, String description, String region,
                                  int minTrials, boolean locationRequired) {
        super(owner, description, region, minTrials, locationRequired);
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
    public NonNegativeCountExperiment(int id, int owner, String description, String region,
                                  int minTrials, boolean locationRequired) {
        super(id, owner, description, region, minTrials, locationRequired);
    }
}
