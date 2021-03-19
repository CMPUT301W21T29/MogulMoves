package com.example.mogulmoves;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import android.widget.TextView;

import static com.google.android.gms.common.internal.safeparcel.SafeParcelable.NULL;

public class ViewExperimentActivity extends AppCompatActivity {

    int exp_id;
    Experiment experiment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_experiment);

        exp_id = getIntent().getIntExtra("expID", -1);
        //defaultValue just set to -1 because it should never call a nonexistent experiment anyway

        experiment = (Experiment) ObjectContext.getObjectById(exp_id);

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