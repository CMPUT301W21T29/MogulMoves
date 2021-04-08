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
 * Takes data from a serializer and pushes it to the database, or retrieves serialized data.
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
}
