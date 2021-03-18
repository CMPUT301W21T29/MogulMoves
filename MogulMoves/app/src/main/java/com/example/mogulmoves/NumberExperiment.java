package com.example.mogulmoves;

/**
 * Abstract class representing an experiment whose trials are simple values.
 */
public abstract class NumberExperiment {

    /**
     * Returns the values of every trial in the experiment.
     *
     * @return an array of the values
     */
    public abstract float[] getValues();

    /**
     * Calculates the median value of all the trials.
     *
     * @return the median
     */
    public float getMedian() {
        return StatCalculator.getMedian(getValues());
    }

    /**
     * Calculates the mean value of all the trials.
     *
     * @return the mean
     */
    public float getMean() {
        return StatCalculator.getMean(getValues());
    }

    /**
     * Calculates the quartiles of the values of all the trials.
     *
     * @return a 2 element array representing the 1st and 3rd quartiles
     */
    public float[] getQuartiles() {
        return StatCalculator.getQuartiles(getValues());
    }

    /**
     * Calculates the standard deviation of the values of all the trials
     *
     * @return the standard deviation
     */
    public float getStdDev() {
        return StatCalculator.getStdDev(getValues());
    }
}
