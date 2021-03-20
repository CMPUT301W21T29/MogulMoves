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

public class ExperimentList extends ArrayAdapter<Experiment> {

    private ArrayList<Experiment> experiments;
    private Context context;

    public ExperimentList(Context context, ArrayList<Experiment> experiments) {
        super(context,0,experiments);
        this.experiments = experiments;
        this.context = context;
    }

    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;

        if (view == null){
            view = LayoutInflater.from(context).inflate(R.layout.experiment_list_item,parent,false);
        }

        Experiment experiment = experiments.get(position);

        TextView desc = view.findViewById(R.id.exp_list_item_desc);
        TextView owner = view.findViewById(R.id.exp_list_item_owner);
        TextView trials = view.findViewById(R.id.exp_list_item_trials);
        TextView region = view.findViewById(R.id.exp_list_item_region);
        TextView subbed = view.findViewById(R.id.subbed);

        desc.setText(experiment.getDescription());

        User exp_owner = (User) ObjectContext.getObjectById(experiment.getOwner());
        if(exp_owner.getUsername().length() <= 0 || exp_owner.getUsername() == null) {
            String str = "(ID " + Integer.toString(ObjectContext.userDatabaseId) + ")";
            owner.setText(str);
        } else {
            owner.setText(exp_owner.getUsername());
        }

        String trials_text = "Trials: " + experiment.getNumTrials() + "/" + experiment.getMinTrials();
        trials.setText(trials_text);

        if (experiment.getRegion().equals(NULL)) {
            region.setText("");
        } else {
            region.setText(experiment.getRegion());
        }

        User app_user = (User) ObjectContext.getObjectById(ObjectContext.userDatabaseId);
        if (app_user.getSubscribed().contains(experiment.getId())) {
            subbed.setVisibility(VISIBLE);
        } else {
            subbed.setVisibility(INVISIBLE);
        }

        return view;
    }
}
