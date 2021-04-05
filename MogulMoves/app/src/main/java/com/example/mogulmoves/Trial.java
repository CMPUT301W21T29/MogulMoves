package com.example.mogulmoves;

import android.location.Location;

import com.google.type.LatLng;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Abstract class to represent a trial of an experiment.
 */
public abstract class Trial extends SavedObject implements GeoTrial {

    private final int experimenter;

    private ArrayList<Double> trialLocation;

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
    public ArrayList<Double> getExperimenterGeo() throws IOException {
        return trialLocation;
    }

}
