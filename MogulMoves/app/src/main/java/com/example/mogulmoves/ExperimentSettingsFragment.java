package com.example.mogulmoves;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.util.ArrayList;

//Fragment to increment the count of a pre-existing count trial.

public class ExperimentSettingsFragment extends DialogFragment {
    private TextView count;

    View.OnClickListener incrementOCL =  new View.OnClickListener() {
        public void onClick(View view) {
            count = view.getRootView().findViewById(R.id.display_count);
            int exp_id = (int) getArguments().getSerializable("exp_id");

            ((ViewExperimentActivity) getActivity()).updateDataDisplay();
        }
    };

    View.OnClickListener backOCL =  new View.OnClickListener() {
        public void onClick(View view) {
            getDialog().dismiss();
        }
    };

    @NonNull
    @Override
    public CustomSettingsDialog onCreateDialog (@Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.view_experiment_settings, null);
        Experiment exp = ObjectContext.getExperimentById((int) getArguments().getSerializable("exp_id"));

        CustomSettingsDialog dialog = new CustomSettingsDialog(getContext(), view);
        dialog.setView(view);

        Button btnEndExperiment = dialog.findViewById(R.id.btnEndExperiment);
        btnEndExperiment.setEnabled(exp.getActive());
        btnEndExperiment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exp.setActive(false);
                ObjectContext.pushExperimentData(exp);
            }
        });

        ToggleButton btnUnpublishExperiment = dialog.findViewById(R.id.btnUnpublishExperiment);
        btnUnpublishExperiment.setChecked(exp.getVisible());
        btnUnpublishExperiment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exp.toggleVisible();
                ObjectContext.pushExperimentData(exp);
            }
        });

        dialog.setTitle("Experiment Settings");
        dialog.setNegativeButton("BACK", backOCL);

        return dialog;
    }

    public ArrayList<Integer> fetchTrialUsers (int exp_id) {
        Experiment experiment = ObjectContext.getExperimentById(exp_id);
        ArrayList<Integer> userIDs = new ArrayList<Integer>();

        for(int trial_id: experiment.getTrials()) {
            Trial trial = ObjectContext.getTrialById(trial_id);
            if (!userIDs.contains(trial.getExperimenter())) {
                userIDs.add(trial.getExperimenter());
            }
        }

        return userIDs;
    }

    static ExperimentSettingsFragment newInstance(int exp_id) {
        Bundle args = new Bundle();
        args.putSerializable("exp_id", exp_id);

        ExperimentSettingsFragment fragment = new ExperimentSettingsFragment();
        fragment.setArguments(args);
        return fragment;
    }

}