package com.example.mogulmoves;

import android.location.Location;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Abstract class to represent an experiment and all of its data.
 */
public abstract class Experiment extends SavedObject implements GeoExperiment{

    private boolean active = true;
    private boolean locationRequired = false;
    private String description;
    private String region;
    private int minTrials;
    private final int owner;

    protected final ArrayList<Integer> trials;
    private final ArrayList<Integer> messages;

    /**
     * Creates the experiment.
     *
     * @param owner the id of the user that owns the experiment
     * @param description the description of the experiment
     * @param region the region the experiment is taking place
     * @param minTrials the required minimum number of trials of the experiment
     * @param locationRequired whether or not the trials of this experiment require a location
     */
    public Experiment(int owner, String description, String region,
                      int minTrials, boolean locationRequired) {
        super();

        this.owner = owner;
        this.description = description;
        this.region = region;
        this.minTrials = minTrials;
        this.locationRequired = locationRequired;

        trials = new ArrayList<>();
        messages = new ArrayList<>();
    }

    /**
     * Creates the experiment with a set id.
     *
     * @param id the object id of the experiment
     * @param owner the id of the user that owns the experiment
     * @param description the description of the experiment
     * @param region the region the experiment is taking place
     * @param minTrials the required minimum number of trials of the experiment
     * @param locationRequired whether or not the trials of this experiment require a location
     */
    public Experiment(int id, int owner, String description, String region,
                      int minTrials, boolean locationRequired) {
        super(id);

        this.owner = owner;
        this.description = description;
        this.region = region;
        this.minTrials = minTrials;
        this.locationRequired = locationRequired;

        trials = new ArrayList<>();
        messages = new ArrayList<>();
    }

    public void setActive(boolean active){
        this.active = active;
    }

    /**
     * Returns the active state of the experiment.
     *
     * @return the active state of the experiment
     */
    public boolean getActive() {
        return active;
    }

    /**
     * Returns the description of the experiment.
     *
     * @return the description of the experiment
     */
    public String getDescription() {
        return description;
    }

    /**
     * Returns the region of the experiment.
     *
     * @return the region of the experiment
     */
    public String getRegion() {
        return region;
    }

    /**
     * Returns the required minimum number of trials of the experiment.
     *
     * @return the required minimum number of trials of the experiment
     */
    public int getMinTrials() {
        return minTrials;
    }

    /**
     * Returns the number of trials added to the experiment.
     *
     * @return the number of trials added to the experiment
     */
    public int getNumTrials() {
        return trials.size();
    }

    /**
     * Returns the id of the owner of the experiment.
     *
     * @return the id of the owner of the experiment.
     */
    public int getOwner() {
        return owner;
    }

    /**
     * Returns the list of trials added to the experiment.
     *
     * @return the list of trials added to the experiment
     */
    public ArrayList<Integer> getTrials() {
        return trials;
    }

    /**
     * Returns the list of messages added to the experiment.
     *
     * @return the list of messages added to the experiment
     */
    public ArrayList<Integer> getMessages() {
        return messages;
    }

    /**
     * Adds a trial to the experiment.
     *
     * @param trial the id of a trial to add to this experiment
     */
    public void addTrial(int trial){
        trials.add(trial);
    }

    /**
     * Adds a reply to the experiment thread.
     *
     * @param message the id of a message to add to this experiment
     */
    public void addMessage(int message){
        messages.add(message);
    }

    /**
     * Returns whether locations are required for this experiment.
     *
     * @return a boolean representing whether locations are required for this experiment
     */
    @Override
    public boolean getLocationRequired() {
        return locationRequired;
    }


    /**
     * @param locationRequired a boolean representing whether locations are required for this experiment
     * @param user the user who try to set if locations are required for this experiment
     * @throws IOException
     */
    @Override
    public void setLocationRequired(boolean locationRequired, int user) throws IOException {
        if (owner == user) {
            this.locationRequired = locationRequired;
        }
        else {
            throw new IOException("Permission Denied");
        }
    }

    /**
     * @param user the owner of this experiment who tries to set
     * @return
     */
    @Override
    public String GeoExperimentWarning(int user) {
        if (owner == user) {
            return "Do you want to require user location for this experiment?";
        }
        else {
            return "This experiment requires collection of your location, do you want to continue?";
        }
    }

    @Override
    public ArrayList<Location> getAllLocations() throws IOException {
        ArrayList<Location> locations = new ArrayList<>();
        for (int i = 0; i < trials.size(); i++) {
            locations.add(((Trial) ObjectContext.getObjectById(trials.get(i))).getExperimenterGeo());
        }
        return locations;
    }



}
