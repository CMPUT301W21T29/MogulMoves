package com.example.mogulmoves;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class AddMeasureTrialFragment extends DialogFragment {
    private EditText count;

    @NonNull
    @Override
    public Dialog onCreateDialog (@Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.measurement_trial_fragment, null);
        count = view.findViewById(R.id.count);

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        return builder
                .setView(view)
                .setTitle("ADD NEW TRIAL")
                .setNegativeButton("Cancel", null)
                .setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        int exp_id = (int) getArguments().getSerializable("exp_id");
                        float count_float = Float.parseFloat(count.getText().toString());

                        Experiment experiment = (Experiment) ObjectContext.getObjectById(exp_id);

                        MeasureTrial trial = new MeasureTrial(ObjectContext.userDatabaseId, count_float);
                        ObjectContext.addTrial(trial, experiment);

                    }
                })
                .create();
    }

    static AddMeasureTrialFragment newInstance(int exp_id) {
        Bundle args = new Bundle();
        args.putSerializable("exp_id", exp_id);

        AddMeasureTrialFragment fragment = new AddMeasureTrialFragment();
        fragment.setArguments(args);
        return fragment;
    }

}
