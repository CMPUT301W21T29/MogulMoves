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
import java.util.Map;

/*Main activity. Displays a list of all experiments on the server. Clicking on any of them takes
you to the respective experiment page. The toolbar also contains buttons to add a new experiment
or view profile information, as well as scan QR and bar codes and search for specific experiments
(last two not functional yet).
 */

public class MainActivity extends AppCompatActivity {

    ListView expList;
    ArrayAdapter<Experiment> expAdapter;
    User loggedInUser;
    boolean initialBootComplete = false;

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

                        Map<String, Object> data = doc.getData();

                        ObjectContext.nextId = (int) (long) data.get("nextId");
                        ObjectContext.nextPostId = (int) (long) data.get("nextPostId");

                    }
                }

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

                                        if (!initialBootComplete) {

                                            boolean isUserFound = false;

                                            for (User user : ObjectContext.users) {
                                                if (user.getInstallationId().equals(ObjectContext.installationId)) {
                                                    ObjectContext.userDatabaseId = user.getId();
                                                    Log.d("help", "3rd" + Integer.toString(ObjectContext.userDatabaseId));
                                                    isUserFound = true;
                                                }
                                            }

                                            if (!isUserFound) {
                                                User self = new User(ObjectContext.installationId, "", "", "");
                                                ObjectContext.userDatabaseId = self.getId();
                                                ObjectContext.addUser(self);
                                            }

                                            expAdapter = new ExperimentList(getApplicationContext(), ObjectContext.experiments);
                                            expList.setAdapter(expAdapter);
                                            expList.setOnItemClickListener(expOCL);

                                            ObjectContext.adapters.add(expAdapter);

                                            initialBootComplete = true;
                                        }

                                        // experiment data listener
                                        CollectionReference collectionReference2 = db.collection("experiments");
                                        collectionReference2.addSnapshotListener(new EventListener<QuerySnapshot>() {
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

                                                    ObjectContext.refreshAdapters();
                                                }
                                            }
                                        });

                                        // trial data listener
                                        CollectionReference collectionReference3 = db.collection("trials");
                                        collectionReference3.addSnapshotListener(new EventListener<QuerySnapshot>() {
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

                                                    ObjectContext.refreshAdapters();
                                                }
                                            }
                                        });
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
            }
        });

    }

    public void toUserProfilePage (View view)
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
        User self = (User)ObjectContext.getObjectById(ObjectContext.userDatabaseId);
        i.putExtra("loggedInUser", self.getUsername());
        startActivity(i);
    }

}