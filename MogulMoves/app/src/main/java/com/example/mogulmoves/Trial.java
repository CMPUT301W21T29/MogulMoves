package com.example.mogulmoves;

public abstract class Trial extends SavedObject {

    private int experimenter;  // id of user who performed this trial

    public void setExperimenter(int experimenter) {
        this.experimenter = experimenter;
    }

    public int getExperimenter() {
        return experimenter;
    }

}
