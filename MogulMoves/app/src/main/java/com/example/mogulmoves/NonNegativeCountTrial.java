package com.example.mogulmoves;

public class NonNegativeCountTrial extends Trial {

    protected int count;

    public NonNegativeCountTrial(int count) {
        this.count = count;
    }

    public int getCount() {
        return count;
    }

    public void increment(){
        count++;
    }

}
