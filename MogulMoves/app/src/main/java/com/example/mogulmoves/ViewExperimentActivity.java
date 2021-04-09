package com.example.mogulmoves;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.CancellationToken;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnTokenCanceledListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static com.google.android.gms.common.internal.safeparcel.SafeParcelable.NULL;
import static com.google.android.gms.location.LocationRequest.PRIORITY_HIGH_ACCURACY;

/**
 * Activity to view details about an experiment, including statistics, minimum and current trials,
 * and histograms, time plots and maps of trials. Also includes functionality to add trials, subscribe,
 * and post messages on the forum.
 */
public class ViewExperimentActivity extends AppCompatActivity {

    int exp_id;
    Experiment experiment;
    RecyclerView postList;
    ArrayList<Message> items;

    ListItemAdapter3 adapter3;
    User self;

    ImageButton btnExpSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_experiment);

        btnExpSettings = (ImageButton) findViewById(R.id.btnExpSettings);

        exp_id = getIntent().getIntExtra("expID", -1);
        //defaultValue just set to -1 because it should never call a nonexistent experiment anyway

        experiment = ObjectContext.getExperimentById(exp_id);
        self = ObjectContext.getUserById(ObjectContext.userDatabaseId);

        postList = (RecyclerView) findViewById(R.id.lstPosts);

        items = new ArrayList<>();

        for(int message: experiment.getMessages()) {
            items.add(ObjectContext.getMessageById(message));
        }

        adapter3 = new ListItemAdapter3(items);
        postList.setAdapter(adapter3);
        postList.setLayoutManager(new LinearLayoutManager(getBaseContext(), LinearLayoutManager.VERTICAL, false));

        updateDataDisplay();
        addListeners();

    }

    public void autoSub() {
        self.addSubscription(exp_id);

        DatabaseHandler.pushUserData(self);
        updateDataDisplay();
    }

    public void subscribe(View view) {
        if (self.getSubscribed().contains(exp_id)) {
            self.removeSubscription(exp_id);
        } else {
            self.addSubscription(exp_id);
        }

        DatabaseHandler.pushUserData(self);
        updateDataDisplay();
    }

    public void goToOwnerProfile(View view) {
        Intent i = new Intent(getApplicationContext(), UserProfilePage.class);
        i.putExtra("userID", experiment.getOwner());
        startActivity(i);
    }

    public void updateDataDisplay() {
        experiment = ObjectContext.getExperimentById(exp_id);

        if (ObjectContext.userDatabaseId != experiment.getOwner()) {
            btnExpSettings.setVisibility(View.INVISIBLE);
        }

        TextView description = findViewById(R.id.experiment_description);
        description.setText(experiment.getDescription());

        TextView owner = findViewById(R.id.exp_list_item_owner);
        User exp_owner = ObjectContext.getUserById(experiment.getOwner());

        if (exp_owner.getUsername().length() <= 0 || exp_owner.getUsername() == null) {
            String str = "(ID " + Integer.toString(ObjectContext.userDatabaseId) + ")";
            owner.setText(str);
        } else {
            owner.setText(exp_owner.getUsername());
        }

        Button trial_button = findViewById(R.id.add_trial_button);
        if (experiment instanceof IntegerCountExperiment) {
            if (((IntegerCountExperiment) experiment).userHasTrial(ObjectContext.userDatabaseId)) {
                trial_button.setText("EDIT TRIAL");
            }
        }

        LinearLayout trial_row = findViewById(R.id.trial_row);
        if (!experiment.getActive()) {
            trial_row.removeAllViews();
            View new_view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.trial_ended, null);
            trial_row.addView(new_view);
        }

        LinearLayout map_row = findViewById(R.id.map_row);
        if (!experiment.getLocationRequired()) {
            map_row.removeAllViews();
        }

        TextView trials = findViewById(R.id.experiment_trials);
        String trials_text = "Trials: " + experiment.getNumTrials() + "/" + experiment.getMinTrials();
        trials.setText(trials_text);

        TextView region = findViewById(R.id.experiment_region);
        if (experiment.getRegion().equals(NULL)) {
            region.setText("");
        } else {
            region.setText(experiment.getRegion());
        }

        Button sub_button = findViewById(R.id.subscribe_button);
        if (self.getSubscribed().contains(exp_id)) {
            sub_button.setText("UNSUBSCRIBE");
            sub_button.setBackgroundColor(Color.RED);
        } else {
            sub_button.setText("SUBSCRIBE");
            sub_button.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.purple_500));
        }

        if (experiment.getUnignoredTrials().size() > 0) {
            TextView stats = findViewById(R.id.experiment_stats_2);

            //some code below adapted from https://stackoverflow.com/a/154354

            String mean = new BigDecimal(String.valueOf(experiment.getMean()))
                    .setScale(3, BigDecimal.ROUND_HALF_UP).stripTrailingZeros().toPlainString();
            String q1 = new BigDecimal(String.valueOf(experiment.getQuartiles()[0]))
                    .setScale(3, BigDecimal.ROUND_HALF_UP).stripTrailingZeros().toPlainString();
            String median = new BigDecimal(String.valueOf(experiment.getMedian()))
                    .setScale(3, BigDecimal.ROUND_HALF_UP).stripTrailingZeros().toPlainString();
            String q3 = new BigDecimal(String.valueOf(experiment.getQuartiles()[1]))
                    .setScale(3, BigDecimal.ROUND_HALF_UP).stripTrailingZeros().toPlainString();
            String stdev = new BigDecimal(String.valueOf(experiment.getStdDev()))
                    .setScale(3, BigDecimal.ROUND_HALF_UP).stripTrailingZeros().toPlainString();
            String stats_string = mean + "\n" + stdev + "\n"
                    + q1 + "\n" + median + "\n" + q3;
            stats.setText(stats_string);
        } else {
            TextView stats = findViewById(R.id.experiment_stats_2);
            stats.setText(R.string.stats2);
        }
    }

    public void openAddTrialFragment2() {
        Button b = findViewById(R.id.add_trial_button);
        openAddTrialFragment(b);
    }

    public void openAddTrialFragment(View view) {
        currentLocation();
        if (experiment instanceof BinomialExperiment) {
            AddBinomialTrialFragment newFragment = AddBinomialTrialFragment.newInstance(exp_id);
            newFragment.show(getSupportFragmentManager(), "ADD_TRIAL");

        } else if (experiment instanceof NonNegativeCountExperiment &&
                !(experiment instanceof IntegerCountExperiment)) {
            AddNNCountTrialFragment newFragment = AddNNCountTrialFragment.newInstance(exp_id);
            newFragment.show(getSupportFragmentManager(), "ADD_TRIAL");

        } else if (experiment instanceof IntegerCountExperiment) {
            if (((IntegerCountExperiment) experiment).userHasTrial(ObjectContext.userDatabaseId)) {
                EditCountTrialFragment newFragment = EditCountTrialFragment.newInstance(exp_id);
                newFragment.show(getSupportFragmentManager(), "EDIT_TRIAL");
            } else {
                AddCountTrialFragment newFragment = AddCountTrialFragment.newInstance(exp_id);
                newFragment.show(getSupportFragmentManager(), "ADD_TRIAL");
            }
        } else {
            AddMeasureTrialFragment newFragment = AddMeasureTrialFragment.newInstance(exp_id);
            newFragment.show(getSupportFragmentManager(), "ADD_TRIAL");
        }

    }

    public void openHistogramFragment(View view) {
        HistogramFragment newFragment = HistogramFragment.newInstance(exp_id);
        newFragment.show(getSupportFragmentManager(), "VIEW_HISTOGRAM");
    }

    public void openTimePlotFragment(View view) {
        TimePlotFragment newFragment = TimePlotFragment.newInstance(exp_id);
        newFragment.show(getSupportFragmentManager(), "VIEW_TIME_PLOT");
    }

    public void openSettingsFragment(View view) {
        ExperimentSettingsFragment newFragment = ExperimentSettingsFragment.newInstance(exp_id);
        newFragment.show(getSupportFragmentManager(), "SETTINGS");
    }

    /**
     * open the map of trial locations
     * */
    public void openMapFragment(View view) {
        //Initialize fragment
        /*Fragment fragment = new MapFragment();

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.map_linear_layout,fragment)
                .commit();*/
        Intent intent = new Intent(this, MapActivity.class);
        intent.putExtra("whichExperiment", Integer.toString(exp_id));
        startActivity(intent);
    }

    /**
     * add the currentLocation of device as trial location
     */
    public void currentLocation() {
        FusedLocationProviderClient fusedLocationProviderClient;

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        if (ActivityCompat.checkSelfPermission(ViewExperimentActivity.this,
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

            fusedLocationProviderClient.getCurrentLocation(PRIORITY_HIGH_ACCURACY, new CancellationToken() {
                @Override
                public boolean isCancellationRequested() {
                    return false;
                }

                @NonNull
                @Override
                public CancellationToken onCanceledRequested(@NonNull OnTokenCanceledListener onTokenCanceledListener) {
                    return null;
                }
            }).addOnCompleteListener(new OnCompleteListener<Location>() {
                @Override
                public void onComplete(@NonNull Task<Location> task) {
                    Location location = task.getResult();
                    if (location != null) {
                        try {
                            Geocoder geocoder = new Geocoder(ViewExperimentActivity.this, Locale.getDefault());

                            List<Address> addressList = geocoder.getFromLocation
                                    (location.getLatitude(),location.getLongitude(),1);
                            double locationLatitude = addressList.get(0).getLatitude();
                            double locationLongitude = addressList.get(0).getLongitude();
                            Log.d("getLocation","locationLatitude" + locationLatitude);
                            Log.d("getLocation", "locationLongitude" + locationLongitude);

                            ObjectContext.location[0] = locationLatitude;
                            ObjectContext.location[1] = locationLongitude;
                        }catch (IOException e){
                            e.printStackTrace();
                        }

                    }
                }
            });

        } else {
            ActivityCompat.requestPermissions(ViewExperimentActivity.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }

    }

    private void addListeners() {
        Button btnNewPost = findViewById(R.id.new_post_button);
        btnNewPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText txtNewPostContent = findViewById(R.id.new_post_content);
                String content = txtNewPostContent.getText().toString();
                if(content.length() > 0) {
                    SimpleDateFormat dateSdf = new SimpleDateFormat("yyyy/MM/dd");
                    SimpleDateFormat timeSdf = new SimpleDateFormat("HH:mm aa");

                    String date = dateSdf.format(new Date());
                    String time = timeSdf.format(new Date());

                    Message message = new Message(ObjectContext.userDatabaseId, content, date, time);
                    ObjectContext.addMessage(message, experiment);

                    items.add(message);
                    adapter3.notifyDataSetChanged();

                    /*
                    Map<String, Object> docData = new HashMap<>();
                    docData.put("content", content);
                    docData.put("user_id", (long) ObjectContext.userDatabaseId);
                    docData.put("date", date);
                    docData.put("time", time);
                    docData.put("exp_id", exp_id);

                    db.collection("posts")
                            .document(Integer.toString(ObjectContext.nextPostId))
                            .set(docData)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    System.out.println("Post Added");
                                    ObjectContext.nextPostId++;

                                    HashMap<String, Object> map = new HashMap<>();
                                    map.put("nextPostId", ObjectContext.nextPostId);
                                    map.put("nextId", ObjectContext.nextId);
                                    DatabaseHandler.pushData("globals", "globals", map);
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    System.out.println("Post Addition Failed");
                                }
                            });
                    items.add(docData);
                    System.out.println("Added: " + docData.get("content").toString()); */

                }
            }
        });

    }
}