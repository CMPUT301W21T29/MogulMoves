package com.example.mogulmoves;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import static android.view.View.VISIBLE;

/**
 * Fragment to add a new count trial.
 */
public class AddCountTrialFragment extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog (@Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.count_trial_fragment, null);

        TextView geo = view.findViewById(R.id.geo_message);
        int exp_id = (int) getArguments().getSerializable("exp_id");
        Experiment experiment = ObjectContext.getExperimentById(exp_id);
        if (experiment.getLocationRequired()) {
            geo.setVisibility(VISIBLE);
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        return builder
                .setView(view)
                .setTitle("ADD NEW TRIAL")
                .setNegativeButton("Cancel", null)
                .setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        IntegerCountTrial trial = new IntegerCountTrial(ObjectContext.userDatabaseId, 0);
                        ObjectContext.addTrial(trial, experiment);

                        User self = ObjectContext.getUserById(ObjectContext.userDatabaseId);
                        if (!self.getSubscribed().contains(exp_id)) {
                            ((ViewExperimentActivity)getActivity()).autoSub();
                        }

                        ((ViewExperimentActivity)getActivity()).updateDataDisplay();

                        getDialog().dismiss();
                        ((ViewExperimentActivity)getActivity()).openAddTrialFragment2();

                    }
                })
                .create();
    }

    static AddCountTrialFragment newInstance(int exp_id) {
        Bundle args = new Bundle();
        args.putSerializable("exp_id", exp_id);

        AddCountTrialFragment fragment = new AddCountTrialFragment();
        fragment.setArguments(args);
        return fragment;
    }

}
