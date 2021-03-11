package com.example.mogulmoves;

public class MeasureTrial extends Trial {

    private float measurement;

    public MeasureTrial(User experimenter, float measurement) {
        super(experimenter);

        this.measurement = measurement;
    }

    public float getMeasurement(){
        return measurement;
    }

}
