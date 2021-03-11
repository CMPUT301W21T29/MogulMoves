package com.example.mogulmoves;

public class NonNegativeCountTrial extends Trial {

    protected int count;

    public NonNegativeCountTrial(User experimenter, int count) {
        super(experimenter);

        this.count = count;
    }

    public int getCount() {
        return count;
    }

    public void increment(){
        count++;
    }

}
