package com.example.mogulmoves;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;
import static com.google.android.gms.common.internal.safeparcel.SafeParcelable.NULL;

//Custom array adapter to display experiment information.

public class CodeExperimentList extends ArrayAdapter<Barcode> {

    private ArrayList<Barcode> barcodes;
    private Context context;
    private String effectStr;

    public CodeExperimentList(Context context, ArrayList<Barcode> barcodes) {
        super(context,0,barcodes);
        this.barcodes = barcodes;
        this.context = context;

    }

    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;

        if (view == null){
            view = LayoutInflater.from(context).inflate(R.layout.qr_experiment_list_item,parent,false);
        }

        Barcode barcode = barcodes.get(position);
        Experiment experiment = (Experiment) ObjectContext.getObjectById(barcode.getExperiment());

        TextView desc = view.findViewById(R.id.exp_list_item_desc);
        TextView effect = view.findViewById(R.id.exp_list_item_effect);

        desc.setText(experiment.getDescription());

        switch (barcode.getAction()) {
            case "succ":
                effectStr = "Success";
                break;
            case "fail":
                effectStr = "Failure";
                break;
            case "incr":
                effectStr = "Increment";
                break;
            default:
                String stringToParse = barcode.getAction().substring(0, 4);
                int intToParse = Integer.valueOf(stringToParse);
                effectStr = Integer.toString(intToParse);
        }
        effect.setText(effectStr);

        return view;
    }
}
