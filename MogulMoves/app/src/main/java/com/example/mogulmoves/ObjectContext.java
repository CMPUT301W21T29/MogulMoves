package com.example.mogulmoves;

import android.widget.ArrayAdapter;

import java.util.ArrayList;

/**
 * Class to contain methods and attributes that can be accessed globally.
 */
public class ObjectContext {

    public static String TAG = "MogulMoves";

    public static int nextId;
    public static String installationId;
    public static int userDatabaseId;

    public static ArrayList<User> users = new ArrayList<>();
    public static ArrayList<Experiment> experiments = new ArrayList<>();
    public static ArrayList<Trial> trials = new ArrayList<>();
    public static ArrayList<Question> questions = new ArrayList<>();

    public static ArrayList<ArrayAdapter> adapters = new ArrayList<>();

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

        for(Question question: questions) {
            if(question.getId() == id) {
                return question;
            }
        }

        return null; // should not happen hopefully

    }

    /**
     * Adds an experiment to the list of experiments.
     * Pushes its data to the database, will cause refresh of all experiments.
     *
     * @param experiment an Experiment object to add
     */
    public static void addExperiment(Experiment experiment) {

        ExperimentSerializer serializer = new ExperimentSerializer();
        DatabaseHandler.pushData("experiments", "" + experiment.getId(),
                serializer.toData(experiment));

    }

    /**
     * Adds a user to the list of users.
     * Pushes its data to the database, will cause refresh of all users.
     *
     * @param user a User object to add
     */
    public static void addUser(User user) {

        UserSerializer serializer = new UserSerializer();
        DatabaseHandler.pushData("users", "" + user.getId(),
                serializer.toData(user));

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

        TrialSerializer serializer = new TrialSerializer();
        DatabaseHandler.pushData("trials", "" + trial.getId(),
                serializer.toData(trial));
    }

    /**
     * Adds a message to the list of messages, and adds it to the main question's list of replies.
     * Pushes its data to the database, will cause refresh of all messages.
     *
     * @param message a Message object to add
     * @param question the parent question to add the reply to
     */
    public static void addReply(Message message, Question question) {

        question.addReply(message.getId());

        MessageSerializer serializer = new MessageSerializer();
        DatabaseHandler.pushData("messages", "" + message.getId(),
                serializer.toData(message));
    }


    /**
     * Adds a question to the list of questions.
     * Pushes its data to the database, will cause refresh of all messages.
     *
     * @param question a Question object to add
     */
    public static void addQuestion(Question question) {

        MessageSerializer serializer = new MessageSerializer();
        DatabaseHandler.pushData("messages", "" + question.getId(),
                serializer.toData(question));
    }

    public static void refreshAdapters() {
        for(ArrayAdapter adapter: adapters) {
            adapter.notifyDataSetChanged();
        }
    }
}
