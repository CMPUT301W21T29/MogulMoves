package com.example.mogulmoves;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.Date;

/**
 * Abstract class to represent a trial of an experiment.
 */
public abstract class Trial extends SavedObject {

    private final int experimenter;
    private final long timestamp;

    private final double[] trialLocation;

    /**
     * Creates the trial.
     *
     * @param experimenter the id of the user that did the trial
     */
    public Trial(int experimenter) {
        super();

        this.experimenter = experimenter;
        this.timestamp = new Date().getTime();
        this.trialLocation = ObjectContext.location;

    }

    /**
     * Creates the trial with a set id.
     *
     * @param id the object id of the trial
     * @param timestamp when the trial was done
     * @param experimenter the id of the user that did the trial
     */
    public Trial(int id, long timestamp, int experimenter, double[] trialLocation) {
        super(id);

        this.experimenter = experimenter;
        this.timestamp = timestamp;
        this.trialLocation = trialLocation;

    }

    /**
     * Returns the id of the experimenter who did the trial.
     *
     * @return the id of the experimenter who did the trial
     */
    public int getExperimenter() {
        return experimenter;
    }

    /**
     * Returns the timestamp of when the trial was performed.
     *
     * @return the timestamp of the trial
     */
    public long getTimestamp() {
        return timestamp;
    }


    /**
     * Set experimenter's default location as this trial's location
     */
    public double[] getTrialLocation() {
        return trialLocation;
    }

    /**
     * @return the location of this trial
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public Double[] getExperimenterGeo() {

        return new Double[]{trialLocation[0], trialLocation[1]};
    }

}
