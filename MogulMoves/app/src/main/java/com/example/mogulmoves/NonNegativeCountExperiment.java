package com.example.mogulmoves;

import java.util.ArrayList;

public class NonNegativeCountExperiment extends Experiment {

    private ArrayList<NonNegativeCountTrial> trials;

    public NonNegativeCountExperiment(String description, String region, int minTrials, boolean locationRequired) {
        super(description, region, minTrials, locationRequired);
    }
}
