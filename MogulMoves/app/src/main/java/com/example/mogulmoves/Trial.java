package com.example.mogulmoves;

public abstract class Trial extends SavedObject {

    private User experimenter;  // user who performed this trial

    public void setExperimenter(User experimenter) {
        this.experimenter = experimenter;
    }

    public User getExperimenter() {
        return experimenter;
    }
}
