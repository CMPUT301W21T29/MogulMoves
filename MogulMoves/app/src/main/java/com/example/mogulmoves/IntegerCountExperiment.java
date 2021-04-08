package com.example.mogulmoves;

/**
 * Class to represent an experiment using integer valued trials.
 */
public class IntegerCountExperiment extends NonNegativeCountExperiment {

    /**
     * Creates the experiment.
     *
     * @param owner the id of the user that owns the experiment
     * @param description the description of the experiment
     * @param region the region the experiment is taking place
     * @param minTrials the required minimum number of trials of the experiment
     * @param locationRequired whether or not the trials of this experiment require a location
     * @param visible whether the experiment should be visible to all users
     */
    public IntegerCountExperiment(int owner, String description, String region,
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
     * @param visible whether the experiment should be visible to all users
     */
    public IntegerCountExperiment(int id, int owner, String description, String region,
                             int minTrials, boolean locationRequired, boolean visible) {
        super(id, owner, description, region, minTrials, locationRequired, visible);
    }

    /**
     * Checks if the user already has a trial for this experiment.
     *
     * @param user the id of the user to check
     * @return a boolean representing whether the user has a trial already
     */
    public boolean userHasTrial(int user) {

        for(int trial: trials) {
            if(((Trial) ObjectContext.getObjectById(trial)).getExperimenter() == user) {
                return true;
            }
        }

        return false;

    }

}
