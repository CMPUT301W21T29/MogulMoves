package com.example.mogulmoves;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Handles interactions between the program and Firebase.
 */
public class DatabaseHandler {

    /**
     * Pushes a HashMap of data to Firebase.
     *
     * @param collection the id of collection to save the data to
     * @param document the id of the document to save the data to
     * @param values a hashmap containing key/value pairs of the data to add to the document
     */
    public static void pushData(String collection, String document, HashMap<String, Object> values) {

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference collectionReference = db.collection(collection);

        collectionReference
                .document(document)
                .set(values)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // These are a method which gets executed when the task is succeeded
                        Log.d(ObjectContext.TAG, "Data has been added successfully!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // These are a method which gets executed if there’s any problem
                        Log.d(ObjectContext.TAG, "Data could not be added!" + e.toString());
                    }
                });
    }

    /**
     * Deletes an item from Firebase.
     *
     * @param collection the id of collection to delete
     * @param document the id of the document to delete
     */
    public static void deleteItem(String collection, String document) {

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection(collection).document(document)
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                    }
                });
    }

    /**
     * Pushes the data for an experiment to the database.
     *
     * @param experiment an experiment to push
     */
    public static void pushExperimentData(Experiment experiment) {

        ExperimentSerializer serializer = new ExperimentSerializer();
        pushData("experiments", "" + experiment.getId(),
                serializer.toData(experiment));

    }

    /**
     * Pushes the data for a user to the database.
     *
     * @param user a user to push
     */
    public static void pushUserData(User user) {

        UserSerializer serializer = new UserSerializer();
        pushData("users", "" + user.getId(),
                serializer.toData(user));

    }

    /**
     * Pushes the data for a trial to the database.
     *
     * @param trial a trial to push
     */
    public static void pushTrialData(Trial trial) {

        TrialSerializer serializer = new TrialSerializer();
        pushData("trials", "" + trial.getId(),
                serializer.toData(trial));

    }

    /**
     * Pushes the data for a message to the database.
     *
     * @param message a message to push
     */
    public static void pushMessageData(Message message) {

        MessageSerializer serializer = new MessageSerializer();
        pushData("messages", "" + message.getId(),
                serializer.toData(message));

    }

    /**
     * Pushes the data for a barcode to the database.
     *
     * @param barcode a barcode to push
     */
    public static void pushBarcodeData(Barcode barcode) {

        BarcodeSerializer serializer = new BarcodeSerializer();
        pushData("barcodes", "" + barcode.getId(),
                serializer.toData(barcode));

    }
}
