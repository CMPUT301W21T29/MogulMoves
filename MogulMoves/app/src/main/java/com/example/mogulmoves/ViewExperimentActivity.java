package com.example.mogulmoves;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.google.android.gms.common.internal.safeparcel.SafeParcelable.NULL;

class ListItemAdapter extends ArrayAdapter<Map<String, Object>> {
    private final Context context;
    private final ArrayList<Map<String, Object>> docData;

    public ListItemAdapter(Context context, ArrayList<Map<String, Object>> docData) {
        super(context, -1, docData);
        this.context = context;
        this.docData = docData;
    }

    @Override public int getCount() {
        return docData == null ? 0 : docData.size();
    }

    @Override public View getView(int position, View convertView, ViewGroup parent) {
        Map<String, Object> item = docData.get(position);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.view_experiment_post_item, parent, false);

        TextView txtPostItemUserName = (TextView) rowView.findViewById(R.id.txtPostItemUserName);
        txtPostItemUserName.setText(item.get("username").toString());

        TextView txtPostItemDate = (TextView) rowView.findViewById(R.id.txtPostItemDate);
        txtPostItemDate.setText(item.get("date").toString());

        TextView txtPostItemTime = (TextView) rowView.findViewById(R.id.txtPostItemTime);
        txtPostItemTime.setText(item.get("time").toString());

        TextView txtPostItemContent = (TextView) rowView.findViewById(R.id.txtPostItemContent);
        txtPostItemContent.setText(item.get("content").toString());

        System.out.println("Returning View: " + item.get("content"));

        return rowView;
    }
}

class ListItemAdapter2 extends BaseAdapter {
    private final Context context;
    private final ArrayList<Map<String, Object>> docData;

    public ListItemAdapter2(Context context, ArrayList<Map<String, Object>> docData) {
        this.context = context;
        this.docData = docData;
    }

    @Override public int getCount() {
        System.out.println("ADAPTER COUNT: " + docData.size());
        return docData == null ? 0 : docData.size();
    }

    @Override
    public Object getItem(int position) {
        return docData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override public View getView(int position, View convertView, ViewGroup parent) {
        Map<String, Object> item = docData.get(position);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.view_experiment_post_item, parent, false);

        TextView txtPostItemUserName = (TextView) rowView.findViewById(R.id.txtPostItemUserName);
        txtPostItemUserName.setText(item.get("username").toString());

        TextView txtPostItemDate = (TextView) rowView.findViewById(R.id.txtPostItemDate);
        txtPostItemDate.setText(item.get("date").toString());

        TextView txtPostItemTime = (TextView) rowView.findViewById(R.id.txtPostItemTime);
        txtPostItemTime.setText(item.get("time").toString());

        TextView txtPostItemContent = (TextView) rowView.findViewById(R.id.txtPostItemContent);
        txtPostItemContent.setText(item.get("content").toString());

        System.out.println("Adapter 2 Returning View: " + item.get("content"));

        return rowView;
    }
}

class ListItemAdapter3 extends RecyclerView.Adapter<ListItemAdapter3.ViewHolder> {
    private static final String TAG = "ListItemAdapter";
    ArrayList<Map<String, Object>> docData;

    public ListItemAdapter3(ArrayList<Map<String, Object>> docData) {
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

public class ViewExperimentActivity extends AppCompatActivity {

    int exp_id;
    Experiment experiment;
    String loggedInUserName;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    RecyclerView postList;
    ArrayList<Map<String, Object>> items = new ArrayList<>();

    //ArrayAdapter<Map<String, Object>> adapter;
    ListItemAdapter adapter;
    ListItemAdapter2 adapter2;
    ListItemAdapter3 adapter3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_experiment);

        exp_id = getIntent().getIntExtra("expID", -1);
        //defaultValue just set to -1 because it should never call a nonexistent experiment anyway

        postList = (RecyclerView) findViewById(R.id.lstPosts);
        adapter3 = new ListItemAdapter3(items);
        postList.setAdapter(adapter3);
        postList.setLayoutManager(new LinearLayoutManager(getBaseContext(), LinearLayoutManager.VERTICAL, false));


        loggedInUserName = getIntent().getStringExtra("loggedInUser");
        if(loggedInUserName.length() <= 0 || loggedInUserName == null) {
            return;
        }

        experiment = (Experiment) ObjectContext.getObjectById(exp_id);

        updateDataDisplay();
        addListeners();
        loadPosts();
    }

    public void updateDataDisplay() {
        experiment = (Experiment) ObjectContext.getObjectById(exp_id);

        TextView description = findViewById(R.id.experiment_description);
        description.setText(experiment.getDescription());

        TextView trials = findViewById(R.id.experiment_trials);
        String trials_text = "Trials: " + experiment.getNumTrials() + "/" + experiment.getMinTrials();
        trials.setText(trials_text);

        TextView region = findViewById(R.id.experiment_region);
        if (experiment.getRegion().equals(NULL)) {
            region.setText("");
        } else {
            region.setText(experiment.getRegion());
        }
    }

    public void openAddTrialFragment (View view) {
        if (experiment instanceof BinomialExperiment) {
            AddBinomialTrialFragment newFragment = AddBinomialTrialFragment.newInstance(exp_id);
            newFragment.show(getSupportFragmentManager(), "ADD_TRIAL");

        } else if (experiment instanceof NonNegativeCountExperiment &&
                !(experiment instanceof IntegerCountExperiment)) {
            AddNNCountTrialFragment newFragment = AddNNCountTrialFragment.newInstance(exp_id);
            newFragment.show(getSupportFragmentManager(), "ADD_TRIAL");

        } else if(experiment instanceof IntegerCountExperiment) {
            AddCountTrialFragment newFragment = AddCountTrialFragment.newInstance(exp_id);
            newFragment.show(getSupportFragmentManager(), "ADD_TRIAL");

        } else {
            AddMeasureTrialFragment newFragment = AddMeasureTrialFragment.newInstance(exp_id);
            newFragment.show(getSupportFragmentManager(), "ADD_TRIAL");
        }

    }

    public void toProfileActivity (View view)
    {
        Intent i = new Intent(getApplicationContext(), ProfileActivity.class);
        startActivity(i);
    }

    private void loadPosts() {
        db.collection("posts")
            .get()
            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if(task.isSuccessful()) {

                        int i = 0;
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Map<String, Object> docData = document.getData();
                            String id = document.getId();
                            int tmpExpId = Integer.parseInt(docData.get("exp_id").toString());
                            if(tmpExpId == exp_id) {
                                items.add(docData);
                                System.out.println("Added: " + docData.get("content").toString());
                                adapter3.notifyItemInserted(i);
                                i++;
                            }
                        }
                    }
                }
            });

    }

    private void addListeners() {
        Button btnNewPost = findViewById(R.id.new_post_button);
        btnNewPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText txtNewPostContent = findViewById(R.id.new_post_content);
                String content = txtNewPostContent.getText().toString();
                if(content.length() > 0) {
                    SimpleDateFormat dateSdf = new SimpleDateFormat("yyyy/MM/dd");
                    SimpleDateFormat timeSdf = new SimpleDateFormat("HH:mm aa");

                    String date = dateSdf.format(new Date());
                    String time = timeSdf.format(new Date());

                    Map<String, Object> docData = new HashMap<>();
                    docData.put("content", content);
                    docData.put("username", loggedInUserName);
                    docData.put("date", date);
                    docData.put("time", time);
                    docData.put("exp_id", exp_id);

                    db.collection("posts")
                        .add(docData)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                System.out.println("Post Added");
                                finish();
                                startActivity(getIntent());
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                System.out.println("Post Addition Failed");
                            }
                        });
                }
            }
        });
    }
}