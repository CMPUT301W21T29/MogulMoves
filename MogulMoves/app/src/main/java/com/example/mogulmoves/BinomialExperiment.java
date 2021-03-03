package com.example.mogulmoves;

import java.util.ArrayList;

public class BinomialExperiment extends Experiment {

    private ArrayList<BinomialTrial> trials;

    public BinomialExperiment(String description, String region, int minTrials) {
        super(description, region, minTrials);
    }
}
