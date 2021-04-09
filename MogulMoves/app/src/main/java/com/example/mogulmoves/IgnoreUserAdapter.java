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
    Experiment exp;

    public IgnoreUserAdapter(Experiment exp, ArrayList<Integer> userIDs) {
        this.userIDs = userIDs;
        this.exp = exp;
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
        User user = (User) ObjectContext.getObjectById(userIDs.get(position));
        String username = user.getUsername();
        if (username.length() <= 0) {
            username = "(ID " + Integer.toString(user.getId()) + ")";
        }
        holder.check.setText(username);
        holder.check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    exp.getIgnoredUsers().add(userIDs.get(position));
                } else {
                    exp.getIgnoredUsers().remove(userIDs.get(position));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return userIDs.size();
    }

}