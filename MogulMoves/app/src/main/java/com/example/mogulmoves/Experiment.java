package com.example.mogulmoves;

import java.util.ArrayList;

/**
 * Abstract class to represent an experiment and all of its data.
 */
public abstract class Experiment extends SavedObject {

    private boolean active = true;
    private boolean visible = true;
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
                      int minTrials, boolean locationRequired, boolean visible) {
        super();

        this.owner = owner;
        this.description = description;
        this.region = region;
        this.minTrials = minTrials;
        this.locationRequired = locationRequired;
        this.visible = visible;

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
                      int minTrials, boolean locationRequired, boolean visible) {
        super(id);

        this.owner = owner;
        this.description = description;
        this.region = region;
        this.minTrials = minTrials;
        this.locationRequired = locationRequired;
        this.visible = visible;

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
     * Returns the visibility of the experiment.
     *
     * @return the visibility of the experiment
     */
    public boolean getVisible() {
        return visible;
    }

    /**
     * Returns whether locations are required for this experiment.
     *
     * @return a boolean representing whether locations are required for this experiment
     */
    public boolean getLocationRequired() {
        return locationRequired;
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
     * Returns the values of every trial in the experiment.
     *
     * @return an array of the values
     */
    protected abstract float[] getValues();

    /**
     * Calculates the median value of all the trials.
     *
     * @return the median
     */
    public float getMedian() {
        return StatCalculator.getMedian(getValues());
    }

    /**
     * Calculates the mean value of all the trials.
     *
     * @return the mean
     */
    public float getMean() {
        return StatCalculator.getMean(getValues());
    }

    /**
     * Calculates the quartiles of the values of all the trials.
     *
     * @return a 2 element array representing the 1st and 3rd quartiles
     */
    public float[] getQuartiles() {
        return StatCalculator.getQuartiles(getValues());
    }

    /**
     * Calculates the standard deviation of the values of all the trials
     *
     * @return the standard deviation
     */
    public float getStdDev() {
        return StatCalculator.getStdDev(getValues());
    }
}
