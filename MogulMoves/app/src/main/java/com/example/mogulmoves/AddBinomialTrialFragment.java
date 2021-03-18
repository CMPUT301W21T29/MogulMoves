package com.example.mogulmoves;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class AddBinomialTrialFragment extends DialogFragment {
    private RadioButton success;

    @NonNull
    @Override
    public Dialog onCreateDialog (@Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.binomial_trial_fragment, null);
        success = view.findViewById(R.id.success);

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        return builder
                .setView(view)
                .setTitle("ADD NEW TRIAL")
                .setNegativeButton("Cancel", null)
                .setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        int exp_id = (int) getArguments().getSerializable("exp_id");
                        boolean bool = success.isChecked();

                        Experiment experiment = (Experiment) ObjectContext.getObjectById(exp_id);

                        BinomialTrial trial = new BinomialTrial(ObjectContext.userDatabaseId, 0, 0);
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
