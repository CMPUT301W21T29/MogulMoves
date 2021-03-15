package com.example.mogulmoves;

import java.util.ArrayList;

public abstract class Experiment {

    private boolean active = true;
    private boolean locationRequired = false;
    private String description;
    private String region;
    private int minTrials;
    protected ArrayList<Trial> trials;
    private User owner;

    public Experiment(String description, String region, int minTrials) {
        this.description = description;
        this.region = region;
        this.minTrials = minTrials;

        trials = new ArrayList<>();
    }

    public void setActive(boolean active){
        this.active = active;
    }

    public boolean getActive() {
        return active;
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

    public void addTrial(Trial trial){
        trials.add(trial);
    }

    public User getOwner() {
        return owner;
    }
}
