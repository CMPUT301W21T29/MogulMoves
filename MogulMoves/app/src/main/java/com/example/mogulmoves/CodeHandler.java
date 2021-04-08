package com.example.mogulmoves;

public class CodeHandler {

    public static void handleCode(String code) {

        String action = code.substring(0, 4);
        int id = Integer.parseInt(code.substring(4));
        Experiment experiment = (Experiment) ObjectContext.getObjectById(id);
        int experimenter = ObjectContext.userDatabaseId;
        Trial trial;

        switch(action) {

            case "succ":
                trial = new BinomialTrial(experimenter, true);
                break;

            case "fail":
                trial = new BinomialTrial(experimenter, false);
                break;

            case "incr":

                for(int trialId: experiment.getTrials()) {

                    Trial checkTrial = (Trial) ObjectContext.getObjectById(trialId);

                    if(checkTrial.getExperimenter() == experimenter) {

                        ((IntegerCountTrial) checkTrial).increment();
                        return;

                    }
                }

                trial = new IntegerCountTrial(experimenter, 1);

                break;

            default:
                trial = new NonNegativeCountTrial(experimenter, Integer.parseInt(action));
                break;

        }

        ObjectContext.addTrial(trial, experiment);

    }
}
