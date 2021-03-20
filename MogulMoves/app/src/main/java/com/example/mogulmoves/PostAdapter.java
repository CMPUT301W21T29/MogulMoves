package com.example.mogulmoves;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Map;

//Custom adapter to display forum posts on experiment pages.

class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {
    private static final String TAG = "ListItemAdapter";
    ArrayList<Map<String, Object>> docData;

    public PostAdapter(ArrayList<Map<String, Object>> docData) {
        this.docData = docData;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView txtPostItemUserName;
        public final TextView txtPostItemDate;
        public final TextView txtPostItemTime;
        public final TextView txtPostItemContent;

        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View
            txtPostItemUserName = (TextView) view.findViewById(R.id.txtPostItemUserName);
            txtPostItemDate = (TextView) view.findViewById(R.id.txtPostItemDate);
            txtPostItemTime = (TextView) view.findViewById(R.id.txtPostItemTime);
            txtPostItemContent = (TextView) view.findViewById(R.id.txtPostItemContent);
            // txtPostItemUserName.setText(item.get("username").toString());
            // txtPostItemDate.setText(item.get("date").toString());
            // txtPostItemTime.setText(item.get("time").toString());
            // txtPostItemContent.setText(item.get("content").toString());
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.view_experiment_post_item, viewGroup, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Map<String, Object> item = docData.get(position);
        holder.txtPostItemUserName.setText(item.get("username").toString());
        holder.txtPostItemDate.setText(item.get("date").toString());
        holder.txtPostItemTime.setText(item.get("time").toString());
        holder.txtPostItemContent.setText(item.get("content").toString());
    }

    @Override
    public int getItemCount() {
        return docData.size();
    }
}
