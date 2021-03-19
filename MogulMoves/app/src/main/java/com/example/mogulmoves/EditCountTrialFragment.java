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

public class EditCountTrialFragment extends DialogFragment {
    private TextView count;

    View.OnClickListener incrementOCL =  new View.OnClickListener() {
        public void onClick(View view) {
            count = view.getRootView().findViewById(R.id.display_count);
            int exp_id = (int) getArguments().getSerializable("exp_id");

            IntegerCountTrial trial = fetchTrial(exp_id);

            trial.increment();
            String count_string = "Count: " + Integer.toString(trial.getCount());
            count.setText(count_string);
            ObjectContext.pushTrialData(trial);
            ObjectContext.pushExperimentData((Experiment)ObjectContext.getObjectById(exp_id));

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
    public CustomDialog onCreateDialog (@Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.edit_count_trial_fragment, null);
        count = view.findViewById(R.id.display_count);
        int exp_id = (int) getArguments().getSerializable("exp_id");

        IntegerCountTrial trial = fetchTrial(exp_id);

        String count_string = "Count: " + Integer.toString(trial.getCount());

        count.setText(count_string);

        CustomDialog dialog = new CustomDialog(getContext(), view);

        dialog.setView(view);
        dialog.setTitle("EDIT TRIAL");
        dialog.setPositiveButton("INCREMENT", incrementOCL);
        dialog.setNegativeButton("BACK", backOCL);

        return dialog;

    }

    public IntegerCountTrial fetchTrial (int exp_id) {
        Experiment experiment = (Experiment) ObjectContext.getObjectById(exp_id);
        IntegerCountTrial current_trial = null;

        for(Trial trial: ObjectContext.trials) {
            if (trial.getExperimenter() == ObjectContext.userDatabaseId) {
                current_trial = (IntegerCountTrial) trial;
            }
        }

        return current_trial;
    }

    static EditCountTrialFragment newInstance(int exp_id) {
        Bundle args = new Bundle();
        args.putSerializable("exp_id", exp_id);

        EditCountTrialFragment fragment = new EditCountTrialFragment();
        fragment.setArguments(args);
        return fragment;
    }

}
