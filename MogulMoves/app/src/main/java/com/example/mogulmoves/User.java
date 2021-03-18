package com.example.mogulmoves;

import android.location.Location;

import java.util.ArrayList;

/**
 * Class to represent a user and their profile data.
 */
public class User extends SavedObject {

    private String installationId;
    private String username;
    private String email;
    private String phone;

    private ArrayList<Integer> subscribed;

    private Location defaultLocation;

    /**
     * Creates the user.
     *
     * @param installationId the Firebase installation id of the user
     * @param username the username of the user
     * @param email the email address of the user
     * @param phone the phone number of the user
     */
    public User(String installationId, String username, String email, String phone) {
        super();

        this.installationId = installationId;
        this.username = username;
        this.email = email;
        this.phone = phone;

        subscribed = new ArrayList<>();
    }

    /**
     * Creates the user with a set id.
     *
     * @param installationId the Firebase installation id of the user
     * @param id the object id of the user
     * @param username the username of the user
     * @param email the email address of the user
     * @param phone the phone number of the user
     */
    public User(int id, String installationId, String username, String email, String phone) {
        super(id);

        this.installationId = installationId;
        this.username = username;
        this.email = email;
        this.phone = phone;

        subscribed = new ArrayList<>();
    }

    /**
     * Returns the Firebase installation id of the user.
     *
     * @return the Firebase installation id of the user
     */
    public String getInstallationId() {
        return installationId;
    }

    /**
     * Returns the username of the user.
     *
     * @return the username of the user
     */
    public String getUsername() {
        return username;
    }

    /**
     * Returns the email address of the user.
     *
     * @return the email address of the user
     */
    public String getEmail() {
        return email;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * Returns the phone number of the user.
     *
     * @return the phone number of the user
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Adds an experiment to the list of subscribed experiments.
     *
     * @param experiment the id of an experiment to subscribe to
     */
    public void addSubscription(int experiment) {
        subscribed.add(experiment);
    }

    /**
     * Returns the list of experiments that have been subscribed to.
     *
     * @return the list of subscribed experiments
     */
    public ArrayList<Integer> getSubscribed() {
        return subscribed;
    }


    /**
     * @return the default location of this user
     */
    public Location getDefaultLocation() {
        return defaultLocation;
    }
}
