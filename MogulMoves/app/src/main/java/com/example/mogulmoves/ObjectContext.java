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
    public static int nextPostId = 10000;

    public static ArrayList<User> users = new ArrayList<>();
    public static ArrayList<Experiment> experiments = new ArrayList<>();
    public static ArrayList<Trial> trials = new ArrayList<>();
    public static ArrayList<Message> messages = new ArrayList<>();

    public static ArrayList<ArrayAdapter> adapters = new ArrayList<>();

    /**
     * Sets the nextId value to a random 32 bit value to prevent overlap with other ids in case
     * something goes wrong.
     */
    public static void randomizeNextId() {
        nextId = new Random().nextInt();

        if(getObjectById(nextId) != null) {
            randomizeNextId();
        }
    }

    /**
     * Returns the object with the given id, or null if that object does not exist.
     *
     * @param id the id of an object
     * @return the corresponding SavedObject
     */
    public static SavedObject getObjectById(int id) {

        for(User user: users) {
            if(user.getId() == id) {
                return user;
            }
        }

        for(Experiment experiment: experiments) {
            if(experiment.getId() == id) {
                return experiment;
            }
        }

        for(Trial trial: trials) {
            if(trial.getId() == id) {
                return trial;
            }
        }

        for(Message message: messages) {
            if(message.getId() == id) {
                return message;
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
            if(experiment.getDescription().toLowerCase().contains(keyword)){
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
        pushExperimentData(experiment);
    }

    /**
     * Adds a user to the list of users.
     * Pushes its data to the database, will cause refresh of all users.
     *
     * @param user a User object to add
     */
    public static void addUser(User user) {

        pushUserData(user);

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

        pushTrialData(trial);
        pushExperimentData(experiment);
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

        pushMessageData(message);
        pushExperimentData(experiment);

    }

    /**
     * Pushes the data for an experiment to the database.
     *
     * @param experiment an experiment to push
     */
    public static void pushExperimentData(Experiment experiment) {

        ExperimentSerializer serializer = new ExperimentSerializer();
        DatabaseHandler.pushData("experiments", "" + experiment.getId(),
                serializer.toData(experiment));

    }

    /**
     * Pushes the data for a user to the database.
     *
     * @param user a user to push
     */
    public static void pushUserData(User user) {

        UserSerializer serializer = new UserSerializer();
        DatabaseHandler.pushData("users", "" + user.getId(),
                serializer.toData(user));

    }

    /**
     * Pushes the data for a trial to the database.
     *
     * @param trial a trial to push
     */
    public static void pushTrialData(Trial trial) {

        TrialSerializer serializer = new TrialSerializer();
        DatabaseHandler.pushData("trials", "" + trial.getId(),
                serializer.toData(trial));

    }

    /**
     * Pushes the data for a message to the database.
     *
     * @param message a message to push
     */
    public static void pushMessageData(Message message) {

        MessageSerializer serializer = new MessageSerializer();
        DatabaseHandler.pushData("messages", "" + message.getId(),
                serializer.toData(message));

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
