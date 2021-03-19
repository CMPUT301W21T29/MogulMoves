package com.example.mogulmoves;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
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

    final ListView.OnItemClickListener expOCL = new ListView.OnItemClickListener() {
        public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
            toViewExperimentActivity(view, ObjectContext.experiments.get(pos).getId());
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupDatabaseListeners();

        expList = findViewById(R.id.experiment_list);
        expAdapter = new ExperimentList(this, ObjectContext.experiments);

        expList.setAdapter(expAdapter);
        expList.setOnItemClickListener(expOCL);

        ObjectContext.adapters.add(expAdapter);

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

    private void setupDatabaseListeners() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference collectionReference;

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
                ObjectContext.refreshAdapters();
            }
        });

        FirebaseInstallations installation = FirebaseInstallations.getInstance();

        installation.getId()
                .addOnSuccessListener(new OnSuccessListener<String>() {
                    @Override
                    public void onSuccess(String result) {
                        Log.d(ObjectContext.TAG, "UIID grabbed successfully!");
                        ObjectContext.installationId = result;

                        // user data listener
                        CollectionReference collectionReference = db.collection("users");
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

                                ObjectContext.refreshAdapters();

                                for(User user: ObjectContext.users){
                                    if(user.getInstallationId().equals(ObjectContext.installationId)){
                                        ObjectContext.userDatabaseId = user.getId();
                                        return;
                                    }
                                }

                                User user = new User(ObjectContext.installationId, "", "", "");
                                ObjectContext.userDatabaseId = user.getId();
                                ObjectContext.addUser(user);
                            }
                        });
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(ObjectContext.TAG, "UIID could not be grabbed!" + e.toString()); // hopefully doesnt happen ohp
                    }
                });

        // experiment data listener
        collectionReference = db.collection("experiments");
        collectionReference.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable
                    FirebaseFirestoreException error) {

                ObjectContext.experiments.clear();
                System.out.println(ObjectContext.experiments.size());

                for(QueryDocumentSnapshot doc: queryDocumentSnapshots) {
                    // Log.d(TAG, String.valueOf(doc.getData().get("Province Name")));
                    // some kind of log message here

                    ExperimentSerializer serializer = new ExperimentSerializer();
                    HashMap<String, Object> data = (HashMap<String, Object>) doc.getData();
                    Experiment experiment = serializer.fromData(data);

                    ObjectContext.experiments.add(experiment);
                }

                ObjectContext.refreshAdapters();
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

                ObjectContext.refreshAdapters();
            }
        });
    }

    public void toProfileActivity (View view)
    {
        Intent i = new Intent(getApplicationContext(), UserProfilePage.class);
        startActivity(i);
    }

    public void toNewExperimentActivity (View view)
    {
        Intent i = new Intent(getApplicationContext(), NewExperimentActivity.class);
        startActivity(i);
    }

    public void toViewExperimentActivity(View v, int exp_id)
    {
        Intent i = new Intent(getApplicationContext(), ViewExperimentActivity.class);
        i.putExtra("expID", exp_id);
        startActivity(i);
    }

}