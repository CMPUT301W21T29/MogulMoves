package com.example.mogulmoves;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

//Custom adapter for listing potential users to ignore in ExperimentSettingsFragment.

class IgnoreUserAdapter extends RecyclerView.Adapter<com.example.mogulmoves.IgnoreUserAdapter.ViewHolder> {
    private static final String TAG = "IgnoreUserAdapter";
    ArrayList<Integer> userIDs;

    public IgnoreUserAdapter(ArrayList<Integer> userIDs) {
        this.userIDs = userIDs;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final CheckBox check;

        public ViewHolder(View view) {
            super(view);
            check = (CheckBox) view.findViewById(R.id.ignore_check);
        }
    }

    @Override
    public com.example.mogulmoves.IgnoreUserAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.ignore_user_item, viewGroup, false);

        return new com.example.mogulmoves.IgnoreUserAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(com.example.mogulmoves.IgnoreUserAdapter.ViewHolder holder, int position) {
        Trial trial = ObjectContext.getTrialById(userIDs.get(position));
        User user = ObjectContext.getUserById(trial.getExperimenter());
        String username = user.getUsername();
        if (username.length() <= 0) {
            username = "(ID " + Integer.toString(user.getId()) + ")";
        }
        holder.check.setText(username);
        holder.check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {

                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return userIDs.size();
    }

}