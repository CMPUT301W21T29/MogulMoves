package com.example.mogulmoves;

/**
 * Abstract class to represent a trial of an experiment.
 */
public abstract class Trial extends SavedObject {

    private final int experimenter;

    // TODO add locations

    /**
     * Creates the trial.
     *
     * @param experimenter the id of the user that did the trial
     */
    public Trial(int experimenter) {
        super();

        this.experimenter = experimenter;
    }

    /**
     * Creates the trial with a set id.
     *
     * @param id the object id of the trial
     * @param experimenter the id of the user that did the trial
     */
    public Trial(int id, int experimenter) {
        super(id);

        this.experimenter = experimenter;
    }

    /**
     * Returns the id of the experimenter who did the trial.
     *
     * @return the id of the experimenter who did the trial
     */
    public int getExperimenter() {
        return experimenter;
    }

    public void setGeoRequired() {
        this.isGeoRequired = ;
    }

}
