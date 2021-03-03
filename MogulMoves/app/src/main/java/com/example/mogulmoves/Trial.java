package com.example.mogulmoves;

public abstract class Trial {

    private Experimenter experimenter;  // experimenter who performed this trial

    public Trial(Experimenter experimenter) {
        this.experimenter = experimenter;
    }

    public Experimenter getExperimenter() {
        return experimenter;
    }
}
