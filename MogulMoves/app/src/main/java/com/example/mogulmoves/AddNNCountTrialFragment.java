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

public class AddNNCountTrialFragment extends DialogFragment {
    private EditText count;

    @NonNull
    @Override
    public Dialog onCreateDialog (@Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.nn_count_trial_fragment, null);
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
                        int count_int = Integer.parseInt(count.getText().toString());

                        Experiment experiment = (Experiment) ObjectContext.getObjectById(exp_id);

                        NonNegativeCountTrial trial = new NonNegativeCountTrial(ObjectContext.userDatabaseId, count_int);
                        ObjectContext.addTrial(trial, experiment);

                    }
                })
                .create();
    }

    static AddNNCountTrialFragment newInstance(int exp_id) {
        Bundle args = new Bundle();
        args.putSerializable("exp_id", exp_id);

        AddNNCountTrialFragment fragment = new AddNNCountTrialFragment();
        fragment.setArguments(args);
        return fragment;
    }

}
