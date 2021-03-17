package com.example.mogulmoves;

import java.util.ArrayList;

/**
 * Class to represent a user and their profile data.
 */
public class User extends SavedObject {

    private String username;
    private String email;
    private String phone;

    private ArrayList<Integer> subscribed;

    /**
     * Creates the user.
     *
     * @param username the username of the user
     * @param email the email address of the user
     * @param phone the phone number of the user
     */
    public User(String username, String email, String phone) {
        super();

        this.username = username;
        this.email = email;
        this.phone = phone;

        subscribed = new ArrayList<>();
    }

    /**
     * Creates the user with a set id.
     *
     * @param id the object id of the user
     * @param username the username of the user
     * @param email the email address of the user
     * @param phone the phone number of the user
     */
    public User(int id, String username, String email, String phone) {
        super(id);

        this.username = username;
        this.email = email;
        this.phone = phone;

        subscribed = new ArrayList<>();
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
}
