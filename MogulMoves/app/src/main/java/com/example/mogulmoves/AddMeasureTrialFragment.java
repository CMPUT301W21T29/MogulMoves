package com.example.mogulmoves;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import static android.view.View.VISIBLE;

public class AddMeasureTrialFragment extends DialogFragment {
    private EditText count;

    @NonNull
    @Override
    public Dialog onCreateDialog (@Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.measurement_trial_fragment, null);
        count = view.findViewById(R.id.count);
        TextView error = view.findViewById(R.id.error_message);

        AlertDialog dialog = new AlertDialog.Builder(getContext())
                .setView(view)
                .setTitle("ADD NEW TRIAL")
                .setNegativeButton("Cancel", null)
                .setPositiveButton("Submit", null)
                .create();

        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            //code below adapted from https://stackoverflow.com/a/7636468
            public void onShow(DialogInterface dialog) {
                Button button = ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_POSITIVE);
                button.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
                        int exp_id = (int) getArguments().getSerializable("exp_id");

                        if (count.getText().toString().equals("")) {
                            error.setVisibility(VISIBLE);
                        } else {
                            float count_float = Float.parseFloat(count.getText().toString());

                            Experiment experiment = (Experiment) ObjectContext.getObjectById(exp_id);

                            MeasureTrial trial = new MeasureTrial(ObjectContext.userDatabaseId, count_float);
                            ObjectContext.addTrial(trial, experiment);

                            ((ViewExperimentActivity)getActivity()).updateDataDisplay();

                            dialog.dismiss();
                        }
                    }
                });
            }
        });

        return dialog;
    }

    static AddMeasureTrialFragment newInstance(int exp_id) {
        Bundle args = new Bundle();
        args.putSerializable("exp_id", exp_id);

        AddMeasureTrialFragment fragment = new AddMeasureTrialFragment();
        fragment.setArguments(args);
        return fragment;
    }

}
