package com.example.mogulmoves;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Abstract class to represent a trial of an experiment.
 */
public abstract class Trial extends SavedObject implements GeoTrial {

    private final int experimenter;
    private final long timestamp;

    private double[] trialLocation = new double[2];

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
     * @param id           the object id of the trial
     * @param timestamp    when the trial was done
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

        Double[] location = {trialLocation[0], trialLocation[1]};
        return location;
    }

}
