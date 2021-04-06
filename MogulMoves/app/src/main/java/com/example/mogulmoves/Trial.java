package com.example.mogulmoves;

import android.location.Location;
import android.os.Build;

import androidx.annotation.RequiresApi;

import java.io.IOException;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Abstract class to represent a trial of an experiment.
 */
public abstract class Trial extends SavedObject implements GeoTrial {

    private final int experimenter;

    private Double[] trialLocation = new Double[2];

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
    /*
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void addExperimenterGeo() {
        //User experimenterObject = (User) ObjectContext.getObjectById(experimenter);
        //trialLocation = experimenterObject.getDefaultLocation();
        Double random1 = ThreadLocalRandom.current().nextDouble(1, 89);
        Double random2 = ThreadLocalRandom.current().nextDouble(-179, -1);
        trialLocation[0] = random1;
        trialLocation[1] = random2;
    }*/

    /**
     * @return the location of this trial
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public Double[] getExperimenterGeo() {
        //return trialLocation;
        // for testing 06.04 only
        Double random1 = ThreadLocalRandom.current().nextDouble(1, 89);
        Double random2 = ThreadLocalRandom.current().nextDouble(-179, -1);
        Double[] test1 = {random1, random2};
        return test1;
    }

}
