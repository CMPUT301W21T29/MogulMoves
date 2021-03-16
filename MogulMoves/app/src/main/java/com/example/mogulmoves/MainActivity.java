package com.example.mogulmoves;

import androidx.annotation.NonNull;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.util.Log;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupDatabaseListeners();
    }

    private void setupDatabaseListeners() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference collectionReference;

        // user data listener
        collectionReference = db.collection("users");
        collectionReference.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable
                    FirebaseFirestoreException error) {

                ObjectContext.users.clear();

                for(QueryDocumentSnapshot doc: queryDocumentSnapshots) {
                    // Log.d(TAG, String.valueOf(doc.getData().get("Province Name")));
                    // some kind of log message here

                    UserSerializer serializer = new UserSerializer();
                    HashMap<String, Object> data = (HashMap<String, Object>) doc.getData();
                    ObjectContext.users.add(serializer.fromData(data));
                }
                // notify any adapters that things have changed here
            }
        });

        // experiment data listener
        collectionReference = db.collection("experiments");
        collectionReference.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable
                    FirebaseFirestoreException error) {

                ObjectContext.experiments.clear();

                for(QueryDocumentSnapshot doc: queryDocumentSnapshots) {
                    // Log.d(TAG, String.valueOf(doc.getData().get("Province Name")));
                    // some kind of log message here

                    ExperimentSerializer serializer = new ExperimentSerializer();
                    HashMap<String, Object> data = (HashMap<String, Object>) doc.getData();
                    Experiment experiment = serializer.fromData(data);

                    ObjectContext.experiments.add(experiment);
                }
                // notify any adapters that things have changed here
            }
        });

        // trial data listener
        collectionReference = db.collection("trials");
        collectionReference.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable
                    FirebaseFirestoreException error) {

                ObjectContext.trials.clear();

                for(QueryDocumentSnapshot doc: queryDocumentSnapshots) {
                    // Log.d(TAG, String.valueOf(doc.getData().get("Province Name")));
                    // some kind of log message here

                    TrialSerializer serializer = new TrialSerializer();
                    HashMap<String, Object> data = (HashMap<String, Object>) doc.getData();
                    Trial trial = serializer.fromData(data);

                    ObjectContext.trials.add(trial);
                }
                // notify any adapters that things have changed here
            }
        });

        // global data listener
        collectionReference = db.collection("globals");
        collectionReference.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable
                    FirebaseFirestoreException error) {

                for(QueryDocumentSnapshot doc: queryDocumentSnapshots) {

                    if(doc.getId().equals("globals")) {
                        // Log.d(TAG, String.valueOf(doc.getData().get("Province Name")));
                        // some kind of log message here

                        ObjectContext.nextId = (int) doc.getData().get("nextId");
                    }
                }
            }
        });
    }

    public void toProfileActivity (View view)
    {
        Intent i = new Intent(getApplicationContext(), ProfileActivity.class);
        startActivity(i);
    }

    public void toNewExperimentActivity (View view)
    {
        Intent i = new Intent(getApplicationContext(), NewExperimentActivity.class);
        startActivity(i);
    }

}