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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

/**
 * Fragment to edit settings of an experiment, including unpublishing or ending it, as well as
 * ignoring trials of certain users.
 */
public class ExperimentSettingsFragment extends DialogFragment {
    RecyclerView toIgnoreList;
    IgnoreUserAdapter adapter;

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

        ArrayList<Integer> userIDs = new ArrayList<Integer>();
        for(int trial: exp.getTrials()) {
            if(!userIDs.contains((ObjectContext.getTrialById(trial)).getExperimenter())) {
                userIDs.add((ObjectContext.getTrialById(trial)).getExperimenter());
            }
        }

        toIgnoreList = dialog.findViewById(R.id.to_ignore_list);
        adapter = new IgnoreUserAdapter(exp, userIDs);
        toIgnoreList.setAdapter(adapter);
        toIgnoreList.setLayoutManager(new LinearLayoutManager(dialog.getContext(), LinearLayoutManager.VERTICAL, false));

        Button btnEndExperiment = dialog.findViewById(R.id.btnEndExperiment);
        btnEndExperiment.setEnabled(exp.getActive());
        btnEndExperiment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exp.setActive(false);
                DatabaseHandler.pushExperimentData(exp);
                btnEndExperiment.setEnabled(false);
                ((ViewExperimentActivity) getActivity()).updateDataDisplay();

                // nukeBarcodes()
                for(Barcode barcode: ObjectContext.barcodes) {
                    if(barcode.getExperiment() == exp.getId()) {
                        ObjectContext.barcodes.remove(barcode);
                        DatabaseHandler.deleteItem("barcodes", Integer.toString(barcode.getId()));
                    }
                }
            }
        });

        TextView trials = dialog.findViewById(R.id.num_trials);
        String trials_text = "Trials: " + exp.getNumTrials() + "/" + exp.getMinTrials();
        trials.setText(trials_text);

        btnEndExperiment.setEnabled(exp.getNumTrials() >= exp.getMinTrials() && exp.getActive());

        ToggleButton btnUnpublishExperiment = dialog.findViewById(R.id.btnUnpublishExperiment);
        btnUnpublishExperiment.setChecked(exp.getVisible());
        btnUnpublishExperiment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exp.toggleVisible();
                DatabaseHandler.pushExperimentData(exp);
            }
        });

        dialog.setTitle("Experiment Settings");
        dialog.setNegativeButton("BACK", backOCL);

        return dialog;
    }

    @Override
    public void onDetach() {
        ((ViewExperimentActivity)getActivity()).updateDataDisplay();
        super.onDetach();
    }

    static ExperimentSettingsFragment newInstance(int exp_id) {
        Bundle args = new Bundle();
        args.putSerializable("exp_id", exp_id);

        ExperimentSettingsFragment fragment = new ExperimentSettingsFragment();
        fragment.setArguments(args);
        return fragment;
    }

}