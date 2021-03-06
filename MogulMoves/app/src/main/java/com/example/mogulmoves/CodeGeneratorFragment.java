package com.example.mogulmoves;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.util.ArrayList;

/**
 * Fragment that allows the user to select an experiment and some trial data to make a QR code for.
 */
public class CodeGeneratorFragment extends DialogFragment {

    private Experiment experiment;
    private boolean isQR;
    ExperimentList adapter;

    public CodeGeneratorFragment(boolean isQR) {
        this.isQR = isQR;
    }

    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        ListView experimentList;
        RadioButton successBox;
        RadioButton failBox;
        TextView countText;
        EditText countInput;

        View view = LayoutInflater.from(getActivity()).inflate(R.layout.code_generate_fragment, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        experimentList = view.findViewById(R.id.experiment_list);                       // still gotta make a selected element highlighted
        successBox = view.findViewById(R.id.succBox);
        failBox = view.findViewById(R.id.failBox);
        countText = view.findViewById(R.id.countText);
        countInput = view.findViewById(R.id.editCount);

        ArrayList<Experiment> experiments = new ArrayList<>();

        for(int expId: ObjectContext.getUserById(ObjectContext.userDatabaseId).getSubscribed()) {
            Experiment exp = ObjectContext.getExperimentById(expId);

            if(exp.getVisible() && exp.getActive() && !(exp instanceof MeasureExperiment)) {
                experiments.add(exp);
            }
        }

        adapter = new ExperimentList(getContext(), experiments);
        experimentList.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        countText.setVisibility(View.INVISIBLE);
        countInput.setVisibility(View.INVISIBLE);
        successBox.setVisibility(View.INVISIBLE);
        failBox.setVisibility(View.INVISIBLE);

        experimentList.setOnItemClickListener((new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                experiment = (Experiment) experimentList.getItemAtPosition(position);

                if (experiment instanceof BinomialExperiment) {
                    countText.setVisibility(View.INVISIBLE);
                    countInput.setVisibility(View.INVISIBLE);
                    successBox.setVisibility(View.VISIBLE);
                    failBox.setVisibility(View.VISIBLE);
                } else if (experiment instanceof IntegerCountExperiment) {
                    countText.setVisibility(View.INVISIBLE);
                    countInput.setVisibility(View.INVISIBLE);
                    successBox.setVisibility(View.INVISIBLE);
                    failBox.setVisibility(View.INVISIBLE);
                } else {
                    countText.setVisibility(View.VISIBLE);
                    countInput.setVisibility(View.VISIBLE);
                    successBox.setVisibility(View.INVISIBLE);
                    failBox.setVisibility(View.INVISIBLE);
                }

            }
        }));

        return builder
                .setView(view)
                .setTitle("Generate QR/Bar Code")
                .setNegativeButton("Cancel", null)
                .setPositiveButton("Generate", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        if (experiment == null) { return; }

                        String action;
                        String id = "" + experiment.getId();

                        if (experiment instanceof BinomialExperiment) {

                            if(successBox.isChecked()) {
                                action = "succ" + id;
                            } else {
                                action = "fail" + id;
                            }

                        } else if (experiment instanceof IntegerCountExperiment) {

                            action = "incr" + id;

                        } else {

                            action = String.format("%04d", Integer.parseInt((countInput).getText().toString())) + id;

                        }

                        if (isQR) {
                            ((CodeActivity) getActivity()).showQR(action);

                        }else {
                            ((CodeActivity) getActivity()).registerCode(experiment.getId(), action);

                        }
                    }
                }).create();
    }

    static CodeGeneratorFragment newInstance(boolean isQR) {
        Bundle args = new Bundle();
        args.putSerializable("isQR", isQR);

        CodeGeneratorFragment fragment = new CodeGeneratorFragment(isQR);
        fragment.setArguments(args);

        return fragment;
    }

}
