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

/**
 *Custom array adapter to display experiment information.
 */
public class BarcodeAdapter extends ArrayAdapter<Barcode> {

    private ArrayList<Barcode> barcodes;
    private Context context;

    public BarcodeAdapter(Context context, ArrayList<Barcode> barcodes) {
        super(context,0, barcodes);
        this.barcodes = barcodes;
        this.context = context;
    }

    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;

        if (view == null){
            view = LayoutInflater.from(context).inflate(R.layout.barcode_list_item,parent,false);
        }

        Barcode barcode = barcodes.get(position);

        TextView desc = view.findViewById(R.id.code_exp_desc_text);
        TextView action = view.findViewById(R.id.code_action_text);

        desc.setText(ObjectContext.getExperimentById(barcode.getExperiment()).getDescription());

        String actionEncoded = barcode.getAction();

        switch(actionEncoded.substring(0, 4)) {

            case "succ":
                action.setText("Add Success");
                break;

            case "fail":
                action.setText("Add Failure");
                break;

            case "incr":

                action.setText("Increment");
                break;

            default:

                action.setText(actionEncoded.substring(0, 4));
                break;

        }

        return view;

    }
}
