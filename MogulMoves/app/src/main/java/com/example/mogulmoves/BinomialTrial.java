package com.example.mogulmoves;

/**
 * Abstract class to represent a trial of a trial with binomial outcomes.
 */
public class BinomialTrial extends Trial {

    private final boolean isSuccess;

    /**
     * Creates the trial.
     *
     * @param experimenter the id of the user that did the trial
     * @param isSuccess whether or not the trial was a success
     */
    public BinomialTrial(int experimenter, boolean isSuccess) {
        super(experimenter);

        this.isSuccess = isSuccess;
    }

    /**
     * Creates the trial with a set id.
     *
     * @param id the object id of the trial
     * @param experimenter the id of the user that did the trial
     * @param isSuccess whether or not the trial was a success
     */
    public BinomialTrial(int id, long timestamp, int experimenter, boolean isSuccess, double[] trialLocation) {
        super(id, timestamp, experimenter, trialLocation);

        this.isSuccess = isSuccess;
    }

    /**
     * Returns a boolean representing if the trial succeeded.
     *
     * @return the success value of the trial
     */
    public boolean getIsSuccess() {
        return isSuccess;
    }

    /*@Override
    public boolean setGeoRequired() {
        return false;
    }*/
}
