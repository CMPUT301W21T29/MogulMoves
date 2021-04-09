package com.example.mogulmoves;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

//Fragment to increment the count of a pre-existing count trial.

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
            DatabaseHandler.pushTrialData(trial);
            DatabaseHandler.pushExperimentData(ObjectContext.getExperimentById(exp_id));

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
    public CustomIncrementDialog onCreateDialog (@Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.edit_count_trial_fragment, null);
        count = view.findViewById(R.id.display_count);
        int exp_id = (int) getArguments().getSerializable("exp_id");

        IntegerCountTrial trial = fetchTrial(exp_id);

        String count_string = "Count: " + Integer.toString(trial.getCount());

        count.setText(count_string);

        CustomIncrementDialog dialog = new CustomIncrementDialog(getContext(), view);

        dialog.setView(view);
        dialog.setTitle("EDIT TRIAL");
        dialog.setPositiveButton("INCREMENT", incrementOCL);
        dialog.setNegativeButton("BACK", backOCL);

        return dialog;

    }

    public IntegerCountTrial fetchTrial (int exp_id) {
        Experiment experiment = ObjectContext.getExperimentById(exp_id);
        IntegerCountTrial current_trial = null;

        for(int trial_id: experiment.getTrials()) {
            Trial trial = ObjectContext.getTrialById(trial_id);
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
