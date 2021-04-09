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

import org.w3c.dom.Text;

import java.util.ArrayList;


public class CodeGeneratorFragment extends DialogFragment {

    private Experiment experiment;
    private boolean isQR;
    ArrayAdapter<Experiment> adapter;

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

        experimentList = view.findViewById(R.id.experiment_list);                                               // still gotta make a selected element highlighted
        successBox = view.findViewById(R.id.succBox);
        failBox = view.findViewById(R.id.failBox);
        countText = view.findViewById(R.id.countText);
        countInput = view.findViewById(R.id.editCount);

        ArrayList<Experiment> experiments = new ArrayList<>();

        /*
        for(int expId: ObjectContext.getUserById(ObjectContext.userDatabaseId).getSubscribed()) {
            experiments.add(ObjectContext.getExperimentById(expId));
        }*/
        experiments = ObjectContext.experiments;

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
                    countText.setVisibility(View.VISIBLE);
                    countInput.setVisibility(View.VISIBLE);
                    successBox.setVisibility(View.INVISIBLE);
                    failBox.setVisibility(View.INVISIBLE);
                } else if (experiment instanceof NonNegativeCountExperiment) {
                    countText.setVisibility(View.INVISIBLE);
                    countInput.setVisibility(View.INVISIBLE);
                    successBox.setVisibility(View.VISIBLE);
                    failBox.setVisibility(View.VISIBLE);
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
                        if (isQR) {
                            // create new QR display fragment if qr code was requested
                            QRDisplayFragment newFragment = QRDisplayFragment.newInstance();
                            // newFragment.show(getSupportFragmentManager(), "DISPLAY_CODE");
                        }
                        else {
                            
                            ((CodeActivity) getActivity()).scanCode();

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
