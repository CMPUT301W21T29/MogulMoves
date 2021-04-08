package com.example.mogulmoves;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.installations.FirebaseInstallations;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import static com.google.zxing.integration.android.IntentIntegrator.QR_CODE;

// some code adapted from https://programmerworld.co/android/how-to-create-your-own-qr-code-and-barcode-scanner-reader-android-app-complete-source-code

/**
 * Main activity. Displays a list of all experiments on the server. Clicking on any of them takes
 * you to the respective experiment page. The toolbar also contains buttons to add a new experiment
 * or view profile information, as well as scan QR and bar codes and search for specific experiments.
 */
public class MainActivity extends AppCompatActivity {

    ListView expList;
    ArrayAdapter<Experiment> expAdapter;
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
        setLocation();

        expList = findViewById(R.id.experiment_list);

    }

    /*
    @Override
    protected void onRestart() {
        super.onRestart();

        expAdapter.notifyDataSetChanged();

    }*/

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

                                        // trial message data listener
                                        CollectionReference collectionReference4 = db.collection("messages");
                                        collectionReference4.addSnapshotListener(new EventListener<QuerySnapshot>() {
                                            @Override
                                            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable
                                                    FirebaseFirestoreException error) {

                                                ObjectContext.messages.clear();

                                                for(QueryDocumentSnapshot doc: queryDocumentSnapshots) {
                                                    // Log.d(TAG, String.valueOf(doc.getData().get("Province Name")));
                                                    // some kind of log message here

                                                    MessageSerializer serializer = new MessageSerializer();
                                                    HashMap<String, Object> data = (HashMap<String, Object>) doc.getData();
                                                    Message message = serializer.fromData(data);

                                                    ObjectContext.messages.add(message);
                                                    ObjectContext.refreshAdapters();

                                                }
                                            }
                                        });

                                        // trial data listener
                                        CollectionReference collectionReference5 = db.collection("barcodes");
                                        collectionReference5.addSnapshotListener(new EventListener<QuerySnapshot>() {
                                            @Override
                                            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable
                                                    FirebaseFirestoreException error) {

                                                ObjectContext.barcodes.clear();

                                                for(QueryDocumentSnapshot doc: queryDocumentSnapshots) {
                                                    // Log.d(TAG, String.valueOf(doc.getData().get("Province Name")));
                                                    // some kind of log message here

                                                    BarcodeSerializer serializer = new BarcodeSerializer();
                                                    HashMap<String, Object> data = (HashMap<String, Object>) doc.getData();
                                                    Barcode code = serializer.fromData(data);

                                                    ObjectContext.barcodes.add(code);
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

    private void setLocation() {
        FusedLocationProviderClient fusedLocationProviderClient;

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(MainActivity.this);
        if (ActivityCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

            fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                @Override
                public void onComplete(@NonNull Task<Location> task) {
                    Location location = task.getResult();
                    if (location != null) {
                        try {
                            Geocoder geocoder = new Geocoder(MainActivity.this, Locale.getDefault());

                            List<Address> addressList = geocoder.getFromLocation
                                    (location.getLatitude(), location.getLongitude(), 1);
                            double locationLatitude = addressList.get(0).getLatitude();
                            double locationLongitude = addressList.get(0).getLongitude();
                            Log.d("getLocation", "locationLatitude" + locationLatitude);
                            Log.d("getLocation", "locationLongitude" + locationLongitude);

                            ObjectContext.location[0] = locationLatitude;
                            ObjectContext.location[1] = locationLongitude;

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });

        } else {
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }


        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA},
                PackageManager.PERMISSION_GRANTED);

            /*Button b = findViewById(R.id.add_trial_button);
            b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (ActivityCompat.checkSelfPermission(ViewExperimentActivity.this,
                            Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

                        fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                            @Override
                            public void onComplete(@NonNull Task<Location> task) {
                                Location location = task.getResult();
                                if (location != null) {
                                    try {
                                        Geocoder geocoder = new Geocoder(ViewExperimentActivity.this, Locale.getDefault());

                                        List<Address> addressList = geocoder.getFromLocation
                                                (location.getLatitude(),location.getLongitude(),1);
                                    }catch (IOException e){
                                        e.printStackTrace();
                                    }

                                }
                            }
                        });

                    } else {
                        ActivityCompat.requestPermissions(ViewExperimentActivity.this,
                                new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
                    }
                }
            });*/
    }

    public void toUserProfilePage (View view) {
        Intent i = new Intent(getApplicationContext(), UserProfilePage.class);
        i.putExtra("userID", ObjectContext.userDatabaseId);
        startActivity(i);
    }

    public void toExperimentSearchActivity (View view) {
        Intent i = new Intent(getApplicationContext(), ExperimentSearchActivity.class);
        startActivity(i);
    }

    public void toNewExperimentActivity (View view) {
        Intent i = new Intent(getApplicationContext(), NewExperimentActivity.class);
        startActivity(i);
    }

    public void toViewExperimentActivity(View v, int exp_id) {
        Intent i = new Intent(getApplicationContext(), ViewExperimentActivity.class);
        i.putExtra("expID", exp_id);
        startActivity(i);
    }

    public void scanCode(View v) {
        IntentIntegrator intentIntegrator = new IntentIntegrator(this);
        intentIntegrator.initiateScan();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (intentResult != null) {

            String result = intentResult.getContents();

            if (result == null) {

                // no result, do something i guess

            } else {

                if (intentResult.getFormatName() != QR_CODE) {

                    boolean found = false;

                    for (Barcode code: ObjectContext.barcodes) {
                        if (code.getCode().equals(result)) {

                            result = code.getAction();
                            found = true;

                        }
                    }

                    if (!found) {
                        // code doesnt exist, do something i guess
                    }
                }

                String action = result.substring(0, 4);
                int id = Integer.parseInt(result.substring(4));
                Experiment experiment = (Experiment) ObjectContext.getObjectById(id);
                int experimenter = ObjectContext.userDatabaseId;
                Trial trial;

                switch(action) {

                    case "succ":
                        trial = new BinomialTrial(experimenter, true);
                        break;

                    case "fail":
                        trial = new BinomialTrial(experimenter, false);
                        break;

                    case "incr":

                        for(int trialId: experiment.getTrials()) {

                            Trial checkTrial = (Trial) ObjectContext.getObjectById(trialId);

                            if(checkTrial.getExperimenter() == experimenter) {

                                ((IntegerCountTrial) checkTrial).increment();
                                return;

                            }
                        }

                        trial = new IntegerCountTrial(experimenter, 1);
                        break;

                    default:
                        trial = new NonNegativeCountTrial(experimenter, Integer.parseInt(action));
                        break;

                }

                ObjectContext.addTrial(trial, experiment);

            }
        }

        super.onActivityResult(requestCode, resultCode, data);

    }
}