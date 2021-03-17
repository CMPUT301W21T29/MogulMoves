package com.example.mogulmoves;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.installations.FirebaseInstallations;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    ListView expList;
    ArrayAdapter<Experiment> expAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        identifyUser();
        setupDatabaseListeners();

D        expList = findViewById(R.id.experiment_list);
        expAdapter = new ExperimentList(this, ObjectContext.experiments);

        expList.setAdapter(expAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();

        expAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        expAdapter.notifyDataSetChanged();
    }

    private void identifyUser() {

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseInstallations installation = FirebaseInstallations.getInstance();

        installation.getId()
                .addOnSuccessListener(new OnSuccessListener<String>() {
                    @Override
                    public void onSuccess(String result) {
                        Log.d(ObjectContext.TAG, "UIID grabbed successfully!");
                        ObjectContext.installationId = result;
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(ObjectContext.TAG, "UIID could not be grabbed!" + e.toString()); // hopefully doesnt happen ohp
                    }
                });

        for(User user: ObjectContext.users){
            if(user.getInstallationId().equals(ObjectContext.installationId)){
                return;
            }
        }

        User user = new User(ObjectContext.installationId, "", "", "");
        ObjectContext.addUser(user);

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

                        ObjectContext.nextId = (int) (long) doc.getData().get("nextId");
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