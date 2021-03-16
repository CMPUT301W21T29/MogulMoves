package com.example.mogulmoves;

import java.util.ArrayList;

public class IntegerCountExperiment extends NonNegativeCountExperiment {

    private ArrayList<IntegerCountTrial> trials;

    public IntegerCountExperiment(String description, String region, int minTrials, boolean locationRequired) {
        super(description, region, minTrials, locationRequired);
    }
}
