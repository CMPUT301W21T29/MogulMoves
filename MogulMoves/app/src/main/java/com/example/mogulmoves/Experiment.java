package com.example.mogulmoves;

import java.util.ArrayList;

public abstract class Experiment extends SavedObject {

    private boolean active = true;
    private boolean locationRequired = false;
    private String description;
    private String region;
    private int minTrials;
    private int owner;

    private ArrayList<Integer> trials;

    public Experiment(String description, String region, int minTrials, boolean locationRequired) {
        super();

        this.description = description;
        this.region = region;
        this.minTrials = minTrials;
        this.locationRequired = locationRequired;

        trials = new ArrayList<>();
    }

    public void setActive(boolean active){
        this.active = active;
    }

    public boolean getActive() {
        return active;
    }

    public boolean getLocationRequired() {
        return locationRequired;
    }

    public String getDescription() {
        return description;
    }

    public String getRegion() {
        return region;
    }

    public int getMinTrials() {
        return minTrials;
    }

    public int getNumTrials() {
        return trials.size();
    }

    public ArrayList<Integer> getTrials() {
        return trials;
    }

    public void addTrial(int trial){

        trials.add(trial);

        //TrialSerializer serializer = new TrialSerializer();
        //DatabaseHandler.pushData("trials", "" + trial.getId(), serializer.toData(trial));
    }

    public void setOwner(int user){
        owner = user;
    }

    public int getOwner() {
        return owner;
    }
}
