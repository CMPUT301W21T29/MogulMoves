package com.example.mogulmoves;

import android.location.Location;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Abstract class to represent an experiment and all of its data.
 */
public abstract class Experiment extends SavedObject {

    private boolean active = true;
    private boolean visible;
    private final boolean locationRequired;
    private final String description;
    private final String region;
    private final int minTrials;
    private final int owner;

    protected final ArrayList<Integer> trials;
    private final ArrayList<Integer> messages;
    private final ArrayList<Integer> ignoredUsers;

    /**
     * Creates the experiment.
     *
     * @param owner the id of the user that owns the experiment
     * @param description the description of the experiment
     * @param region the region the experiment is taking place
     * @param minTrials the required minimum number of trials of the experiment
     * @param locationRequired whether or not the trials of this experiment require a location
     * @param visible whether the experiment should be visible to all users
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
        ignoredUsers = new ArrayList<>();

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
     * @param visible whether the experiment should be visible to all users
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
        ignoredUsers = new ArrayList<>();

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
     * Sets the active state of the experiment.
     *
     * @param active the new active state of the experiment
     */
    public void setActive(boolean active){
        this.active = active;
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
     * Toggles the visibility of the experiment.
     */
    public void toggleVisible() {
        visible = !visible;
    }

    /**
     * Returns whether locations are required for the experiment.
     *
     * @return whether locations are required for the experiment
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
     * Returns the list of trials that haven't been ignored.
     *
     * @return the list of unignored trials
     */
    public ArrayList<Integer> getUnignoredTrials() {

        ArrayList<Integer> result = new ArrayList<>();

        for(int trial: trials) {

            boolean keep = true;
            
            for(int ignoredUser: ignoredUsers) {
                if((ObjectContext.getTrialById(trial)).getExperimenter() == ignoredUser) {
                    keep = false;
                    break;
                }
            }

            if(keep) {
                result.add(trial);
            }
        }

        return result;

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
     * Returns the list of trials added to the experiment.
     *
     * @return the list of trials added to the experiment
     */
    public ArrayList<Integer> getTrials() {
        return trials;
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
     * Returns the list of messages added to the experiment.
     *
     * @return the list of messages added to the experiment
     */
    public ArrayList<Integer> getMessages() {
        return messages;
    }

    /**
     * Adds an experimenter to the list of ignored experimenters.
     *
     * @param experimenter the id of an experimenter to ignore
     */
    public void addIgnoredUser(int experimenter) {
        ignoredUsers.add(experimenter);
    }

    /**
     * Returns the list of users that have been ignored.
     *
     * @return the list of ignored users
     */
    public ArrayList<Integer> getIgnoredUsers() {
        return ignoredUsers;
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

    /**
     * @param locationRequired a boolean representing whether locations are required for this experiment
     * @param user the user who try to set if locations are required for this experiment
     * @throws IOException

     @Override
     public void setLocationRequired(boolean locationRequired, int user) throws IOException {
     if (owner == user) {
     this.locationRequired = locationRequired;
     }
     else {
     throw new IOException("Permission Denied");
     }
     }*/

    /**
     * @param user the user who tries to subscribe to this experiment, or owner who tries to set geo requirement
     * @return

     @Override
     public String GeoExperimentWarning(int user) {
     if (owner == user) {
     return "Do you want to require user location for this experiment?";
     }
     else {
     return "This experiment requires collection of your location, do you want to continue?";
     }
     }*/


    /**
     * @return a list of locations of all existent trials of this experiment if geo required
     * @throws IOException

     @Override
     public ArrayList<Location> getAllLocations() throws IOException {
     ArrayList<Location> locations = new ArrayList<>();
     for (int i = 0; i < trials.size(); i++) {
     locations.add(((Trial) ObjectContext.getObjectById(trials.get(i))).getExperimenterGeo());
     }
     return locations;
     }*/
}
