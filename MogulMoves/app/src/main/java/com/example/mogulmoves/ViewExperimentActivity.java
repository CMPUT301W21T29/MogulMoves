package com.example.mogulmoves;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;

import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import java.math.BigDecimal;

import static com.google.android.gms.common.internal.safeparcel.SafeParcelable.NULL;

class ListItemAdapter3 extends RecyclerView.Adapter<ListItemAdapter3.ViewHolder> {
    private static final String TAG = "ListItemAdapter";
    ArrayList<Message> docData;

    public ListItemAdapter3(ArrayList<Message> docData) {
        this.docData = docData;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView txtPostItemUserName;
        public final TextView txtPostItemDate;
        public final TextView txtPostItemTime;
        public final TextView txtPostItemContent;

        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View
            txtPostItemUserName = (TextView) view.findViewById(R.id.txtPostItemUserName);
            txtPostItemDate = (TextView) view.findViewById(R.id.txtPostItemDate);
            txtPostItemTime = (TextView) view.findViewById(R.id.txtPostItemTime);
            txtPostItemContent = (TextView) view.findViewById(R.id.txtPostItemContent);
            // txtPostItemUserName.setText(item.get("username").toString());
            // txtPostItemDate.setText(item.get("date").toString());
            // txtPostItemTime.setText(item.get("time").toString());
            // txtPostItemContent.setText(item.get("content").toString());
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.view_experiment_post_item, viewGroup, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Message message = docData.get(position);
        User poster = (User) ObjectContext.getObjectById(message.getUser());
        String username = poster.getUsername();
        if (username.length() <= 0) {
            username = "(ID " + Integer.toString(poster.getId()) + ")";
        }
        holder.txtPostItemUserName.setText(username);
        holder.txtPostItemUserName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), UserProfilePage.class);
                i.putExtra("userID", message.getUser());
                v.getContext().startActivity(i);
            }
        });
        holder.txtPostItemDate.setText(message.getDate());
        holder.txtPostItemTime.setText(message.getTime());
        holder.txtPostItemContent.setText(message.getText());
    }

    @Override
    public int getItemCount() {
        return docData.size();
    }

}

public class ViewExperimentActivity extends AppCompatActivity {

    int exp_id;
    Experiment experiment;
    RecyclerView postList;
    ArrayList<Integer> items = new ArrayList<>();

    //ArrayAdapter<Map<String, Object>> adapter;
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

        postList = (RecyclerView) findViewById(R.id.lstPosts);
        adapter3 = new ListItemAdapter3(ObjectContext.messages);
        postList.setAdapter(adapter3);
        postList.setLayoutManager(new LinearLayoutManager(getBaseContext(), LinearLayoutManager.VERTICAL, false));

        experiment = (Experiment) ObjectContext.getObjectById(exp_id);
        self = (User) ObjectContext.getObjectById(ObjectContext.userDatabaseId);

        updateDataDisplay();
        addListeners();

    }

    public void autoSub() {
        self.addSubscription(exp_id);

        ObjectContext.pushUserData(self);
        updateDataDisplay();
    }

    public void subscribe(View view) {
        if (self.getSubscribed().contains(exp_id)) {
            self.removeSubscription(exp_id);
        } else {
            self.addSubscription(exp_id);
        }

        ObjectContext.pushUserData(self);
        updateDataDisplay();
    }

    public void goToOwnerProfile(View view) {
        Intent i = new Intent(getApplicationContext(), UserProfilePage.class);
        i.putExtra("userID", experiment.getOwner());
        startActivity(i);
    }


    public void updateDataDisplay() {
        experiment = (Experiment) ObjectContext.getObjectById(exp_id);

        TextView description = findViewById(R.id.experiment_description);
        description.setText(experiment.getDescription());

        TextView owner = findViewById(R.id.exp_list_item_owner);
        User exp_owner = (User) ObjectContext.getObjectById(experiment.getOwner());

        if (exp_owner.getUsername().length() <= 0 || exp_owner.getUsername() == null) {
            String str = "(ID " + Integer.toString(ObjectContext.userDatabaseId) + ")";
            owner.setText(str);
        } else {
            owner.setText(exp_owner.getUsername());
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

        Button trial_button = findViewById(R.id.add_trial_button);
        if (experiment instanceof IntegerCountExperiment) {
            if (((IntegerCountExperiment) experiment).userHasTrial(ObjectContext.userDatabaseId)) {
                trial_button.setText("EDIT TRIAL");
            }
        }

        if (experiment.getNumTrials() > 0) {
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

        }


    }

    public void openAddTrialFragment2() {
        Button b = findViewById(R.id.add_trial_button);
        openAddTrialFragment(b);
    }

    public void openAddTrialFragment(View view) {

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
        Intent intent = new Intent(this, MapAdaptor.class);
        intent.putExtra("whichExperiment", Integer.toString(exp_id));
        startActivity(intent);
    }

    public void toProfileActivity (View view)
    {
        Intent i = new Intent(getApplicationContext(), ProfileActivity.class);
        startActivity(i);
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

                    adapter3.notifyDataSetChanged();
                }
            }
        });

    }
}