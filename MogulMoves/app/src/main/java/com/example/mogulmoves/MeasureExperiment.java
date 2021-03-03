package com.example.mogulmoves;

import java.util.ArrayList;

public class MeasureExperiment extends Experiment {

    private ArrayList<MeasureTrial> trials;

    public MeasureExperiment(String description, String region, int minTrials) {
        super(description, region, minTrials);
    }
}
