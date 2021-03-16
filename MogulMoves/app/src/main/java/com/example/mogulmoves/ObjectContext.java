package com.example.mogulmoves;

import java.util.ArrayList;

public class ObjectContext {

    public static int nextId;

    public static ArrayList<User> users = new ArrayList<>();
    public static ArrayList<Experiment> experiments = new ArrayList<>();
    public static ArrayList<Trial> trials = new ArrayList<>();
    public static ArrayList<Question> questions = new ArrayList<>();

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

        return null; // should not happen

    }

    public static void addExperiment(Experiment experiment) {
        ExperimentSerializer serializer = new ExperimentSerializer();
        DatabaseHandler.pushData("experiments", "" + experiment.getId(),
                serializer.toData(experiment));

    }

    public void addUser(User user) {

        UserSerializer serializer = new UserSerializer();
        DatabaseHandler.pushData("users", "" + user.getId(),
                serializer.toData(user));

    }

    public void addTrial(Trial trial, Experiment experiment) {

        experiment.addTrial(trial.getId());

        UserSerializer serializer = new UserSerializer();
        DatabaseHandler.pushData("users", "" + trial.getId(),
                serializer.toData(trial));
    }

    public void addReply(Message message, Question question) {

        question.addReply(message.getId());

        MessageSerializer serializer = new MessageSerializer();
        DatabaseHandler.pushData("messages", "" + message.getId(),
                serializer.toData(message));
    }

    public void addQuestion(Question question) {

        MessageSerializer serializer = new MessageSerializer();
        DatabaseHandler.pushData("messages", "" + question.getId(),
                serializer.toData(question));
    }
}
