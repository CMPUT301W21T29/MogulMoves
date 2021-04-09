package com.example.mogulmoves;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/*
 */

public class ExperimentSearchActivity extends AppCompatActivity {

    ListView expList;
    ArrayAdapter<Experiment> expAdapter;
    ArrayList<Experiment> results;
    SearchView expSearch;
    InputMethodManager imm;

    final ListView.OnItemClickListener expOCL = new ListView.OnItemClickListener() {
        public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
            toViewExperimentActivity(view, results.get(pos).getId());
        }
    };

    final SearchView.OnQueryTextListener searchOCL = new SearchView.OnQueryTextListener() {
        @Override
        public boolean onQueryTextSubmit(String search) {
            imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(expSearch.getWindowToken(), 0);

            results = ObjectContext.searchExperiments(search);

            expAdapter = new ExperimentList(getApplicationContext(), results);
            expList.setAdapter(expAdapter);
            expList.setOnItemClickListener(expOCL);

            return true;
        }

        @Override
        public boolean onQueryTextChange(String newText) {
            return true;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.experiment_search);

        expSearch = findViewById(R.id.exp_search);
        expSearch.setOnQueryTextListener(searchOCL);

        expList = findViewById(R.id.experiment_list);

    }


    public void toViewExperimentActivity(View v, int exp_id) {
        Intent i = new Intent(getApplicationContext(), ViewExperimentActivity.class);
        i.putExtra("expID", exp_id);
        User self = ObjectContext.getUserById(ObjectContext.userDatabaseId);
        i.putExtra("loggedInUser", self.getUsername());
        startActivity(i);
    }

}