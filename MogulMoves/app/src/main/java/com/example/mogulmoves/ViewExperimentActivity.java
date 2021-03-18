package com.example.mogulmoves;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import android.widget.Button;
import android.widget.TextView;

import java.math.BigDecimal;
import java.util.ArrayList;

import static com.google.android.gms.common.internal.safeparcel.SafeParcelable.NULL;

public class ViewExperimentActivity extends AppCompatActivity {

    int exp_id;
    Experiment experiment;
    User self;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_experiment);

        exp_id = getIntent().getIntExtra("expID", -1);
        //defaultValue just set to -1 because it should never call a nonexistent experiment anyway

        experiment = (Experiment) ObjectContext.getObjectById(exp_id);
        self = (User) ObjectContext.getObjectById(ObjectContext.userDatabaseId);

        updateDataDisplay();

    }

    public void subscribe(View view) {
        /*if (self.getSubscribed().contains(exp_id)) {
            self.
        }

        self.addSubscription(exp_id);*/

        updateDataDisplay();
    }


    public void updateDataDisplay() {
        experiment = (Experiment) ObjectContext.getObjectById(exp_id);

        TextView description = findViewById(R.id.experiment_description);
        description.setText(experiment.getDescription());

        TextView trials = findViewById(R.id.experiment_trials);
        String trials_text = "Trials: " + experiment.getNumTrials() + "/" + experiment.getMinTrials();
        trials.setText(trials_text);

        TextView region = findViewById(R.id.experiment_region);
        if (experiment.getRegion().equals(NULL)) {
            region.setText("");
        } else {
            region.setText(experiment.getRegion());
        }

        User self = (User) ObjectContext.getObjectById(ObjectContext.userDatabaseId);
        Button sub_button = findViewById(R.id.subscribe_button);
        if (self.getSubscribed().contains(exp_id)) {
            sub_button.setText("UNSUBSCRIBE");
            sub_button.setBackgroundColor(Color.RED);
        } else {
            sub_button.setText("SUBSCRIBE");
            sub_button.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.purple_500));
        }

        TextView stats = findViewById(R.id.experiment_stats_2);
        //some code below adapted from https://stackoverflow.com/a/154354
        ArrayList<BigDecimal> stats_list = new ArrayList<BigDecimal>();
        stats_list.add(new BigDecimal(String.valueOf(experiment.getMean())).setScale(3, BigDecimal.ROUND_HALF_UP).stripTrailingZeros());
        stats_list.add(new BigDecimal(String.valueOf(experiment.getQuartiles()[0])).setScale(3, BigDecimal.ROUND_HALF_UP).stripTrailingZeros());
        stats_list.add(new BigDecimal(String.valueOf(experiment.getMedian())).setScale(3, BigDecimal.ROUND_HALF_UP).stripTrailingZeros());
        stats_list.add(new BigDecimal(String.valueOf(experiment.getQuartiles()[1])).setScale(3, BigDecimal.ROUND_HALF_UP).stripTrailingZeros());
        stats_list.add(new BigDecimal(String.valueOf(experiment.getStdDev())).setScale(3, BigDecimal.ROUND_HALF_UP).stripTrailingZeros());
        String stats_string = stats_list.get(0) + "\n" + stats_list.get(4) + "\n"
                + stats_list.get(1) + "\n" + stats_list.get(2) + "\n" + stats_list.get(3);
        if (experiment.getNumTrials() > 0) {
            stats.setText(stats_string);
        }


    }

    public void openAddTrialFragment (View view) {
        if (experiment instanceof BinomialExperiment) {
            AddBinomialTrialFragment newFragment = AddBinomialTrialFragment.newInstance(exp_id);
            newFragment.show(getSupportFragmentManager(), "ADD_TRIAL");

        } else if (experiment instanceof NonNegativeCountExperiment &&
                !(experiment instanceof IntegerCountExperiment)) {
            AddNNCountTrialFragment newFragment = AddNNCountTrialFragment.newInstance(exp_id);
            newFragment.show(getSupportFragmentManager(), "ADD_TRIAL");

        } else if(experiment instanceof IntegerCountExperiment) {
            AddCountTrialFragment newFragment = AddCountTrialFragment.newInstance(exp_id);
            newFragment.show(getSupportFragmentManager(), "ADD_TRIAL");

        } else {
            AddMeasureTrialFragment newFragment = AddMeasureTrialFragment.newInstance(exp_id);
            newFragment.show(getSupportFragmentManager(), "ADD_TRIAL");
        }

    }

    public void toProfileActivity (View view)
    {
        Intent i = new Intent(getApplicationContext(), ProfileActivity.class);
        startActivity(i);
    }

}