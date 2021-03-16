package com.example.mogulmoves;

public class BinomialTrial extends Trial {

    private int successes;
    private int failures;

    public BinomialTrial(Experimenter experimenter, int successes, int failures) {
        super(experimenter);

        this.successes = successes;
        this.failures = failures;
    }

    public int getTrials(){
        return successes + failures;
    }

    public int getSuccesses() {
        return successes;
    }

    public int getFailures() {
        return failures;
    }
}
