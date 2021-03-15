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

/*
 * Handles interactions between the program and Firebase,
 * takes data from a serializer and pushes it to the database,
 * or retrieves serialized data.
 */

public class DatabaseHandler {

    static String TAG = "Sample";

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
                        Log.d(TAG, "Data has been added successfully!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // These are a method which gets executed if there’s any problem
                        Log.d(TAG, "Data could not be added!" + e.toString());
                    }
                });

    }

    public static ArrayList<HashMap<String, Object>> pullData(String collection) {

        ArrayList<HashMap<String, Object>> result = new ArrayList<>();

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference collectionReference = db.collection(collection);

        for(QueryDocumentSnapshot doc: collectionReference.get().getResult()) {
            Log.d(TAG, "Retrieving data for " + collection);
            result.add((HashMap<String, Object>) doc.getData());
        }

        return result;

    }

}
