package com.example.mogulmoves;

import android.location.Location;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

/**
 * Abstract class to represent a trial of an experiment.
 */
public abstract class Trial extends SavedObject implements GeoTrial {

    private final int experimenter;
    private final long timestamp;

    private Location trialLocation;

    /**
     * Creates the trial.
     *
     * @param experimenter the id of the user that did the trial
     */
    public Trial(int experimenter) {
        super();

        this.experimenter = experimenter;
        this.timestamp = new Date().getTime();

    }

    /**
     * Creates the trial with a set id.
     *
     * @param id the object id of the trial
     * @param timestamp when the trial was done
     * @param experimenter the id of the user that did the trial
     */
    public Trial(int id, long timestamp, int experimenter) {
        super(id);

        this.experimenter = experimenter;
        this.timestamp = timestamp;

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
    public void addExperimenterGeo() {
        User experimenterObject = (User) ObjectContext.getObjectById(experimenter);
        trialLocation = experimenterObject.getDefaultLocation();
    }

    /**
     * @return the location of this trial
     * @throws IOException
     */
    public Location getExperimenterGeo() throws IOException {
        return trialLocation;
    }

}
