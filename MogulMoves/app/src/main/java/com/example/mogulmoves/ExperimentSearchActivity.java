package com.example.mogulmoves;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;
import java.util.Map;

/*
 */

public class ExperimentSearchActivity extends AppCompatActivity {

    ListView expList;
    ArrayAdapter<Experiment> expAdapter;
    SearchView expSearch;

    final ListView.OnItemClickListener expOCL = new ListView.OnItemClickListener() {
        public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
            toViewExperimentActivity(view, ObjectContext.experiments.get(pos).getId());
        }
    };

    /*final View.OnClickListener searchOCL = new View.OnClickListener() {
        public void onClick(View view) {
            String search = expSearch.getQuery().toString();
        }
    };*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.experiment_search);

        expList = findViewById(R.id.experiment_list);
        expSearch = findViewById(R.id.exp_search);
    }


    public void toViewExperimentActivity(View v, int exp_id) {
        Intent i = new Intent(getApplicationContext(), ViewExperimentActivity.class);
        i.putExtra("expID", exp_id);
        User self = (User)ObjectContext.getObjectById(ObjectContext.userDatabaseId);
        i.putExtra("loggedInUser", self.getUsername());
        startActivity(i);
    }

}