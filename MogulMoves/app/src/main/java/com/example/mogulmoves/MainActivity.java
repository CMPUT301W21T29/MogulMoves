package com.example.mogulmoves;

import androidx.annotation.NonNull;

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
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private ArrayList<User> users = new ArrayList<>();
    private ArrayList<Experiment> experiments = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupDatabaseListeners();
        initQAModule();
    }

    private void initQAModule() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        Spinner expSpinner = findViewById(R.id.spinner);

        EditText txtSubject = findViewById(R.id.txtSubject);
        EditText txtEmail = findViewById(R.id.txtEmail);
        EditText txtQuestion = findViewById(R.id.txtQuestion);

        Button btnQuestionSubmit = findViewById(R.id.btnSubmitQuestion);
        btnQuestionSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String experiment = expSpinner.getSelectedItem().toString();
                String subject = txtSubject.getText().toString();
                String email = txtEmail.getText().toString();
                String question = txtQuestion.getText().toString();

                Map<String, Object> docData = new HashMap<>();
                docData.put("subject", subject);
                docData.put("experiment", experiment);
                docData.put("email", email);
                docData.put("question", question);

                db.collection("questions")
                        .add(docData)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                System.out.println("Question Added");
                                txtSubject.setText("");
                                txtEmail.setText("");
                                txtQuestion.setText("");
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                System.out.println("Question Addition Failed");
                            }
                        });

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

    private void setupDatabaseListeners() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference collectionReference;

        // user data listener
        collectionReference = db.collection("users");
        collectionReference.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable
                    FirebaseFirestoreException error) {

                users.clear();

                for(QueryDocumentSnapshot doc: queryDocumentSnapshots) {
                    // Log.d(TAG, String.valueOf(doc.getData().get("Province Name")));
                    // some kind of log message here

                    UserSerializer serializer = new UserSerializer();
                    users.add(serializer.fromData((HashMap<String, Object>) doc.getData()));
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

                experiments.clear();

                HashMap<Integer, User> userHashMap = new HashMap<>();

                for(User user: users){
                    userHashMap.put(user.getId(), user);
                }

                for(QueryDocumentSnapshot doc: queryDocumentSnapshots) {
                    // Log.d(TAG, String.valueOf(doc.getData().get("Province Name")));
                    // some kind of log message here

                    ExperimentSerializer serializer = new ExperimentSerializer();
                    HashMap<String, Object> data = (HashMap<String, Object>) doc.getData();
                    Experiment experiment = serializer.fromData(data);

                    experiment.setOwner(userHashMap.get((int) data.get("id")));
                    experiments.add(experiment);
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

                HashMap<Integer, Experiment> experimentHashMap = new HashMap<>();
                HashMap<Integer, User> userHashMap = new HashMap<>();

                for(Experiment experiment: experiments){
                    experiment.trials.clear();
                    experimentHashMap.put(experiment.getId(), experiment);
                }

                for(User user: users){
                    userHashMap.put(user.getId(), user);
                }

                for(QueryDocumentSnapshot doc: queryDocumentSnapshots) {
                    // Log.d(TAG, String.valueOf(doc.getData().get("Province Name")));
                    // some kind of log message here

                    HashMap<String, Object> data = (HashMap<String, Object>) doc.getData();

                    TrialSerializer serializer = new TrialSerializer();
                    Trial trial = serializer.fromData(data);

                    trial.setExperimenter(userHashMap.get((int) data.get("experimenter")));

                    experimentHashMap.get((int) data.get("id")).addTrial(trial);
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

                        SavedObject.nextId = (int) doc.getData().get("nextId");
                    }
                }
            }
        });
    }

    public void publishExperiment(Experiment experiment) {

        experiments.add(experiment);

        ExperimentSerializer serializer = new ExperimentSerializer();
        DatabaseHandler.pushData("experiments", "" + experiment.getId(),
                serializer.toData(experiment));

    }

    public void addUser(User user) {

        users.add(user);

        UserSerializer serializer = new UserSerializer();
        DatabaseHandler.pushData("users", "" + user.getId(),
                serializer.toData(user));

    }

}