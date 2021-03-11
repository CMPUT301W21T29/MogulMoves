package com.example.mogulmoves;

public class BinomialTrial extends Trial {

    private int successes;
    private int failures;

    public BinomialTrial(User experimenter, int successes, int failures) {
        super(experimenter);

        this.successes = successes;
        this.failures = failures;
    }

    public int getTrials(){
        return successes + failures;
    }

}
