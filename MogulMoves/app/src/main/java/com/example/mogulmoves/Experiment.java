package com.example.mogulmoves;

import java.util.ArrayList;

public abstract class Experiment {

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

    public String getDescription() {
        return description;
    }

    public String getRegion() {
        return region;
    }

    public int getMinTrials() {
        return minTrials;
    }

    public void addTrial(Trial trial){
        trials.add(trial);
    }
}
