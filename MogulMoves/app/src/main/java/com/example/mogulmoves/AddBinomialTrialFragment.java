package com.example.mogulmoves;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import static android.view.View.VISIBLE;

public class AddBinomialTrialFragment extends DialogFragment {
    private RadioButton success;
    private RadioButton failure;

    @NonNull
    @Override
    public Dialog onCreateDialog (@Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.binomial_trial_fragment, null);
        success = view.findViewById(R.id.success);
        failure = view.findViewById(R.id.failure);
        TextView error = view.findViewById(R.id.error_message);

        AlertDialog dialog = new AlertDialog.Builder(getContext())
                .setView(view)
                .setTitle("ADD NEW TRIAL")
                .setNegativeButton("Cancel", null)
                .setPositiveButton("Submit", null)
                .create();

        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                Button button = ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_POSITIVE);
                button.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
                        int exp_id = (int) getArguments().getSerializable("exp_id");

                        if (!success.isChecked() && !failure.isChecked()) {
                            error.setVisibility(VISIBLE);
                        } else {
                            Experiment experiment = (Experiment) ObjectContext.getObjectById(exp_id);

                            boolean bool = success.isChecked();
                            BinomialTrial trial = new BinomialTrial(ObjectContext.userDatabaseId, bool);
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

    static AddBinomialTrialFragment newInstance(int exp_id) {
        Bundle args = new Bundle();
        args.putSerializable("exp_id", exp_id);

        AddBinomialTrialFragment fragment = new AddBinomialTrialFragment();
        fragment.setArguments(args);
        return fragment;
    }

}
