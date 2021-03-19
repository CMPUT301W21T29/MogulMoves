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

public class AddNNCountTrialFragment extends DialogFragment {
    private EditText count;

    @NonNull
    @Override
    public Dialog onCreateDialog (@Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.nn_count_trial_fragment, null);
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
                            int count_int = Integer.parseInt(count.getText().toString());

                            Experiment experiment = (Experiment) ObjectContext.getObjectById(exp_id);

                            NonNegativeCountTrial trial = new NonNegativeCountTrial(ObjectContext.userDatabaseId, count_int);
                            ObjectContext.addTrial(trial, experiment);

                            User self = (User) ObjectContext.getObjectById(ObjectContext.userDatabaseId);
                            if (!self.getSubscribed().contains(exp_id)) {
                                ((ViewExperimentActivity)getActivity()).autoSub();
                            }

                            ((ViewExperimentActivity)getActivity()).updateDataDisplay();

                            dialog.dismiss();
                        }
                    }
                });
            }
        });

        return dialog;
    }

    static AddNNCountTrialFragment newInstance(int exp_id) {
        Bundle args = new Bundle();
        args.putSerializable("exp_id", exp_id);

        AddNNCountTrialFragment fragment = new AddNNCountTrialFragment();
        fragment.setArguments(args);
        return fragment;
    }

}
