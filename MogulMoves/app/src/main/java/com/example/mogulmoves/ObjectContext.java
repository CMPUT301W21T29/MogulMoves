package com.example.mogulmoves;

import android.widget.ArrayAdapter;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;

/**
 * Class to contain methods and attributes that can be accessed globally.
 */
public class ObjectContext {

    public static String TAG = "MogulMoves";

    public static int nextId;
    public static int userDatabaseId;
    public static String installationId;
    public static double[] location = new double[2];

    public static ArrayList<User> users = new ArrayList<>();
    public static ArrayList<Experiment> experiments = new ArrayList<>();
    public static ArrayList<Trial> trials = new ArrayList<>();
    public static ArrayList<Message> messages = new ArrayList<>();
    public static ArrayList<Barcode> barcodes = new ArrayList<>();

    public static ArrayList<ArrayAdapter> adapters = new ArrayList<>();

    /*public static void randomizeNextId() {
        nextId = new Random().nextInt();

        if(getObjectById(nextId) != null) {
            randomizeNextId();
        }
    }*/

    /**
     * Returns the user with the given id, or null if that object does not exist.
     *
     * @param id the id of a user
     * @return the corresponding User
     */
    public static User getUserById(int id) {

        for(User user: users) {
            if(user.getId() == id) {
                return user;
            }
        }

        return null; // should not happen hopefully

    }

    /**
     * Returns the experiment with the given id, or null if that object does not exist.
     *
     * @param id the id of an experiment
     * @return the corresponding Experiment
     */
    public static Experiment getExperimentById(int id) {

        for(Experiment experiment: experiments) {
            if(experiment.getId() == id) {
                return experiment;
            }
        }

        return null; // should not happen hopefully

    }

    /**
     * Returns the trial with the given id, or null if that object does not exist.
     *
     * @param id the id of a trial
     * @return the corresponding Trial
     */
    public static Trial getTrialById(int id) {

        for(Trial trial: trials) {
            if(trial.getId() == id) {
                return trial;
            }
        }

        return null; // should not happen hopefully

    }

    /**
     * Returns the message with the given id, or null if that object does not exist.
     *
     * @param id the id of a message
     * @return the corresponding Message
     */
    public static Message getMessageById(int id) {

        for(Message message: messages) {
            if(message.getId() == id) {
                return message;
            }
        }

        return null; // should not happen hopefully

    }

    /**
     * Returns the barcode with the given id, or null if that object does not exist.
     *
     * @param id the id of a barcode
     * @return the corresponding Barcode
     */
    public static Barcode getBarcodeById(int id) {

        for(Barcode barcode: barcodes) {
            if(barcode.getId() == id) {
                return barcode;
            }
        }

        return null; // should not happen hopefully

    }

    /**
     * Searches all experiments for a keyword.
     *
     * @param keyword a keyword to search the experiments for
     * @return an ArrayList of ids of experiments which have the keyword
     */
    public static ArrayList<Experiment> searchExperiments(String keyword) {

        ArrayList<Experiment> found = new ArrayList<>();
        keyword = keyword.toLowerCase();

        for(Experiment experiment: experiments) {
            if((experiment.getDescription().toLowerCase().contains(keyword) ||
                    experiment.getRegion().toLowerCase().contains(keyword) ||
                    (getUserById(experiment.getOwner())).getUsername().toLowerCase().contains(keyword)
                    ) && (experiment.getVisible() || experiment.getOwner() == userDatabaseId)) {
                found.add(experiment);
            }
        }

        return found;

    }

    /**
     * Adds an experiment to the list of experiments.
     * Pushes its data to the database, will cause refresh of all experiments.
     *
     * @param experiment an Experiment object to add
     */
    public static void addExperiment(Experiment experiment) {
        ObjectContext.experiments.add(experiment);
        DatabaseHandler.pushExperimentData(experiment);
    }

    /**
     * Adds a user to the list of users.
     * Pushes its data to the database, will cause refresh of all users.
     *
     * @param user a User object to add
     */
    public static void addUser(User user) {
        DatabaseHandler.pushUserData(user);
    }

    /**
     * Adds a trial to the list of trials, and adds it to the experiment's list of trials.
     * Pushes its data to the database, will cause refresh of all trials.
     *
     * @param trial a Trial object to add
     * @param experiment the parent experiment to add the trial to
     */
    public static void addTrial(Trial trial, Experiment experiment) {

        experiment.addTrial(trial.getId());
        trials.add(trial);

        DatabaseHandler.pushTrialData(trial);
        DatabaseHandler.pushExperimentData(experiment);
    }

    /**
     * Adds a message to the list of messages, and adds it to the main question's list of replies.
     * Pushes its data to the database, will cause refresh of all messages.
     *
     * @param message a Message object to add
     * @param experiment the parent experiment to add the reply to
     */
    public static void addMessage(Message message, Experiment experiment) {

        experiment.addMessage(message.getId());

        DatabaseHandler.pushMessageData(message);
        DatabaseHandler.pushExperimentData(experiment);

    }

    /**
     * Adds a barcode to the list of barcodes.
     * Pushes its data to the database, will cause refresh of all barcodes.
     *
     * @param barcode a Barbarcode object to add
     */
    public static void addBarcode(Barcode barcode, User user) {

        user.addBarcode(barcode.getId());

        DatabaseHandler.pushBarcodeData(barcode);
        DatabaseHandler.pushUserData(user);

    }

    /**
     * Refreshes the list adapters for when new data comes in.
     */
    public static void refreshAdapters() {
        for(ArrayAdapter adapter: adapters) {
            adapter.notifyDataSetChanged();
        }
    }
}
