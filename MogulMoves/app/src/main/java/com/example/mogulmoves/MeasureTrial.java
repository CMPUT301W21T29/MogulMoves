package com.example.mogulmoves;

/**
 * Abstract class to represent a trial of a trial with decimal outcomes.
 */
public class MeasureTrial extends Trial {

    private final float measurement;

    /**
     * Creates the trial.
     *
     * @param experimenter the id of the user that did the trial
     * @param measurement the measurement associated with the trial
     */
    public MeasureTrial(int experimenter, float measurement) {
        super(experimenter);

        this.measurement = measurement;
    }

    /**
     * Creates the trial with a set it.
     *
     * @param id the object id of the trial
     * @param experimenter the id of the user that did the trial
     * @param measurement the measurement associated with the trial
     */
    public MeasureTrial(int id, long timestamp, int experimenter, float measurement) {
        super(id, timestamp, experimenter);

        this.measurement = measurement;
    }

    /**
     * Returns the measurement of the trial.
     *
     * @return the measurement of the trial
     */
    public float getMeasurement(){
        return measurement;
    }

    /*@Override
    public boolean setGeoRequired() {
        return false;
    }*/
}
