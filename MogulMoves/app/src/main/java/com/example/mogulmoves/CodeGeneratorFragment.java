package com.example.mogulmoves;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;


public class CodeGeneratorFragment extends DialogFragment {

    private Experiment experiment;
    private boolean isQR;

    public CodeGeneratorFragment(boolean isQR) {
        this.isQR = isQR;
    }

    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        ListView experimentList;
        RadioButton successBox;
        RadioButton failBox;
        EditText countText;
        EditText countInput;

        View view = LayoutInflater.from(getActivity()).inflate(R.layout.code_generate_fragment, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        experimentList = view.findViewById(R.id.experiment_list);                                               // still gotta make a selected element highlighted
        successBox = view.findViewById(R.id.succBox);
        failBox = view.findViewById(R.id.failBox);
        countText = view.findViewById(R.id.countText);
        countInput = view.findViewById(R.id.editCount);

        if (experiment instanceof BinomialExperiment) {
            countText.setVisibility(View.INVISIBLE);
            countInput.setVisibility(View.INVISIBLE);
        }
        else if (experiment instanceof NonNegativeCountExperiment) {
            successBox.setVisibility(View.INVISIBLE);
            failBox.setVisibility(View.INVISIBLE);
        }

        experimentList.setOnItemClickListener((new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                experiment = (Experiment) experimentList.getItemAtPosition(position);
            }
        }));

        return builder
                .setView(view)
                .setTitle("Generate QR/Bar Code")
                .setNegativeButton("Cancel", null)
                .setPositiveButton("Generate", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (isQR) {
                            // create new QR display fragment if qr code was requested
                        }
                        else {
                            //associate a given bar code with the trial
                        }
                    }
                }).create();
    }

    static CodeGeneratorFragment newInstance(boolean isQR) {
        Bundle args = new Bundle();
        args.putSerializable("isQR", isQR);

        CodeGeneratorFragment fragment;

        fragment = new CodeGeneratorFragment(isQR);

        fragment.setArguments(args);

        return fragment;
    }

}
