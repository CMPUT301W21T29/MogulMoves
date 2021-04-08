package com.example.mogulmoves;

public class Barcode extends SavedObject {

    private final int experiment;
    private final int user;
    private final String code;

    public Barcode(int id, int experiment, int user, String code) {
        super(id);

        this.experiment = experiment;
        this.user = user;
        this.code = code;

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
}
