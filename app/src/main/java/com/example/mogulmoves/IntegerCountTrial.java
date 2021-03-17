package com.example.mogulmoves;

/**
 * Abstract class to represent a trial of a trial with integral outcomes.
 */
public class IntegerCountTrial extends NonNegativeCountTrial{

    /**
     * Creates the trial.
     *
     * @param experimenter the id of the user that did the trial
     * @param count the counted number associated with the trial
     */
    public IntegerCountTrial(int experimenter, int count) {
        super(experimenter, count);
    }

    /**
     * Creates the trial with a set it.
     *
     * @param id the object id of the trial
     * @param experimenter the id of the user that did the trial
     * @param count the counted number associated with the trial
     */
    public IntegerCountTrial(int id, int experimenter, int count) {
        super(id, experimenter, count);
    }

    /**
     * Decrements the count of the trial.
     */
    public void decrement(){
        count--;
    }

}
