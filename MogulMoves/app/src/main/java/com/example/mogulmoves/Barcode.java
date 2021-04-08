package com.example.mogulmoves;

public class Barcode extends SavedObject {

    private final int experiment;
    private final int user;
    private final String code;
    private final String action;


    public Barcode(int id, int experiment, int user, String code, String action) {
        super(id);

        this.experiment = experiment;
        this.user = user;
        this.code = code;
        this.action = action;

    }

    public int getExperiment() {
        return experiment;
    }

    public int getUser() {
        return user;
    }

    public String getCode() {
        return code;
    }


    public String getAction() { return action; }
}
