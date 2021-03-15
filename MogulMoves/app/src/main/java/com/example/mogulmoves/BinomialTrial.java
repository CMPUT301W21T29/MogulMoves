package com.example.mogulmoves;

public class BinomialTrial extends Trial {

    private int successes;
    private int failures;

    public BinomialTrial(int successes, int failures) {
        this.successes = successes;
        this.failures = failures;
    }

    public int getSuccesses() {
        return successes;
    }

    public int getFailures() {
        return failures;
    }

    public int getTrials(){
        return successes + failures;
    }

}
