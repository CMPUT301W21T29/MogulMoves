package com.example.mogulmoves;

/**
 * Class to represent an experiment using binomial trials.
 */
public class BinomialExperiment extends Experiment {

    /**
     * Creates the experiment.
     *
     * @param owner the id of the user that owns the experiment
     * @param description the description of the experiment
     * @param region the region the experiment is taking place
     * @param minTrials the required minimum number of trials of the experiment
     * @param locationRequired whether or not the trials of this experiment require a location
     */
    public BinomialExperiment(int owner, String description, String region,
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
    public BinomialExperiment(int id, int owner, String description, String region,
                              int minTrials, boolean locationRequired) {
        super(id, owner, description, region, minTrials, locationRequired);
    }

    /**
     * Calculates the total number of successes across all trials.
     *
     * @return the number of successes
     */
    public int getTotalSuccesses() {
        int sum = 0;

        for(int trial: trials){
            sum += ((BinomialTrial) ObjectContext.getObjectById(trial)).getSuccesses();
        }

        return sum;
    }

    /**
     * Calculates the total number of failures across all trials.
     *
     * @return the number of failures
     */
    public int getTotalFailures() {
        int sum = 0;

        for(int trial: trials){
            sum += ((BinomialTrial) ObjectContext.getObjectById(trial)).getFailures();
        }

        return sum;
    }

    /**
     * Calculates the total number of results (successes + failures) across all trials.
     *
     * @return the number of results
     */
    public int getTotalResults() {
        return getTotalSuccesses() + getTotalFailures();
    }

    /**
     * Calculates the success rate across all trials.
     *
     * @return the success rate
     */
    public float getSuccessRate() {
        return (float) getTotalSuccesses() / getTotalFailures();
    }
}
