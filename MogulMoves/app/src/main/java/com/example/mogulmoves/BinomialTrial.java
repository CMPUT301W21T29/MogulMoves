package com.example.mogulmoves;

/**
 * Abstract class to represent a trial of a trial with binomial outcomes.
 */
public class BinomialTrial extends Trial {

    private int successes;
    private int failures;

    /**
     * Creates the trial.
     *
     * @param experimenter the id of the user that did the trial
     * @param successes the number of successes of the trial
     * @param failures the number of failures of the trial
     */
    public BinomialTrial(int experimenter, int successes, int failures) {
        super(experimenter);

        this.successes = successes;
        this.failures = failures;
    }

    /**
     * Creates the trial with a set id.
     *
     * @param id the object id of the trial
     * @param experimenter the id of the user that did the trial
     * @param successes the number of successes of the trial
     * @param failures the number of failures of the trial
     */
    public BinomialTrial(int id, int experimenter, int successes, int failures) {
        super(id, experimenter);

        this.successes = successes;
        this.failures = failures;
    }

    /**
     * Returns the number of successes of the trial.
     *
     * @return the number of successes of the trial
     */
    public int getSuccesses() {
        return successes;
    }

    /**
     * Returns the number of failures of the trial.
     *
     * @return the number of failures of the trial
     */
    public int getFailures() {
        return failures;
    }

    /**
     * Returns the total number of results of the trial.
     *
     * @return the total number of results of the trial
     */
    public int getNumResults(){
        return successes + failures;
    }
}
