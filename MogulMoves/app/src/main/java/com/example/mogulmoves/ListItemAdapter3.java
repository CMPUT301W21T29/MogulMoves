package com.example.mogulmoves;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

class ListItemAdapter3 extends RecyclerView.Adapter<ListItemAdapter3.ViewHolder> {
    private static final String TAG = "ListItemAdapter";
    ArrayList<Message> docData;

    public ListItemAdapter3(ArrayList<Message> docData) {
        this.docData = docData;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView txtPostItemUserName;
        public final TextView txtPostItemDate;
        public final TextView txtPostItemTime;
        public final TextView txtPostItemContent;

        public ViewHolder(View view) {
            super(view);
            txtPostItemUserName = (TextView) view.findViewById(R.id.txtPostItemUserName);
            txtPostItemDate = (TextView) view.findViewById(R.id.txtPostItemDate);
            txtPostItemTime = (TextView) view.findViewById(R.id.txtPostItemTime);
            txtPostItemContent = (TextView) view.findViewById(R.id.txtPostItemContent);
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
        Message message = docData.get(position);
        User poster = ObjectContext.getUserById(message.getUser());
        String username = poster.getUsername();
        if (username.length() <= 0) {
            username = "(ID " + Integer.toString(poster.getId()) + ")";
        }
        holder.txtPostItemUserName.setText(username);
        holder.txtPostItemUserName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), UserProfilePage.class);
                i.putExtra("userID", message.getUser());
                v.getContext().startActivity(i);
            }
        });
        holder.txtPostItemDate.setText(message.getDate());
        holder.txtPostItemTime.setText(message.getTime());
        holder.txtPostItemContent.setText(message.getText());
    }

    @Override
    public int getItemCount() {
        return docData.size();
    }

}