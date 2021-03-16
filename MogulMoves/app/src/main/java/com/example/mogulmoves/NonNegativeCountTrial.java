package com.example.mogulmoves;

/**
 * Abstract class to represent a trial of a trial with positive whole number outcomes.
 */
public class NonNegativeCountTrial extends Trial {

    protected int count;

    /**
     * Creates the trial.
     *
     * @param experimenter the id of the user that did the trial
     * @param count the counted number associated with the trial
     */
    public NonNegativeCountTrial(int experimenter, int count) {
        super(experimenter);

        this.count = count;
    }

    /**
     * Creates the trial with a set it.
     *
     * @param id the object id of the trial
     * @param experimenter the id of the user that did the trial
     * @param count the counted number associated with the trial
     */
    public NonNegativeCountTrial(int id, int experimenter, int count) {
        super(id, experimenter);

        this.count = count;
    }

    /**
     * Returns the count of the trial.
     *
     * @return the count of the trial
     */
    public int getCount() {
        return count;
    }

    /**
     * Increments the count of the trial.
     */
    public void increment(){
        count++;
    }

}
