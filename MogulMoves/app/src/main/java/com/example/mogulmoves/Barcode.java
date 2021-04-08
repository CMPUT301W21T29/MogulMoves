package com.example.mogulmoves;

/**
 * Class to represent a registered barcode for a specific user to scan.
 */
public class Barcode extends SavedObject {

    private final int experiment;
    private final int user;
    private final String code;
    private final String action;

    /**
     * Creates the barcode with a set id
     *
     * @param experiment the experiment the barcode belongs to
     * @param user the user who registered the barcode
     * @param code the barcode value
     * @param action a string encoding the action to take when the barcode is scanned
     */
    public Barcode(int experiment, int user, String code, String action) {
        super();

        this.experiment = experiment;
        this.user = user;
        this.code = code;
        this.action = action;

    }

    /**
     * Creates the barcode with a set id
     *
     * @param id the id of the barcode object
     * @param experiment the experiment the barcode belongs to
     * @param user the user who registered the barcode
     * @param code the barcode value
     * @param action a string encoding the action to take when the barcode is scanned
     */
    public Barcode(int id, int experiment, int user, String code, String action) {
        super(id);

        this.experiment = experiment;
        this.user = user;
        this.code = code;
        this.action = action;

    }

    /**
     * Returns the experiment the barcode belongs to.
     *
     * @return the experiment the barcode belongs to
     */
    public int getExperiment() {
        return experiment;
    }

    /**
     * Returns the user who registered the barcode.
     *
     * @return the user who registered the barcode
     */
    public int getUser() {
        return user;
    }

    /**
     * Returns the barcode value.
     *
     * @return the barcode value
     */
    public String getCode() {
        return code;
    }

    /**
     * Returns the string encoding the action to take when the barcode is scanned.
     *
     * @return the string encoding the action to take when the barcode is scanned
     */
    public String getAction() {
        return action;
    }
}
