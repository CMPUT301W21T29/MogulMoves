package com.example.mogulmoves;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;

public class MapAdapter extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_adaptor);
        Intent intent = getIntent();
        int expId = Integer.parseInt(intent.getStringExtra("whichExperiment"));

        //Initialize fragment
        if (ObjectContext.getObjectById(expId) instanceof BinomialExperiment) {
            Fragment fragment = new MapFragment((BinomialExperiment) ObjectContext.getObjectById(expId));

            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.map_frame_layout, fragment)
                    .commit();
        }
        else if (ObjectContext.getObjectById(expId) instanceof IntegerCountExperiment) {
            Fragment fragment = new MapFragment((IntegerCountExperiment) ObjectContext.getObjectById(expId));

            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.map_frame_layout, fragment)
                    .commit();
        }
        else if (ObjectContext.getObjectById(expId) instanceof NonNegativeCountExperiment) {
            Fragment fragment = new MapFragment((NonNegativeCountExperiment) ObjectContext.getObjectById(expId));

            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.map_frame_layout, fragment)
                    .commit();
        }
        else if (ObjectContext.getObjectById(expId) instanceof MeasureExperiment) {
            Fragment fragment = new MapFragment((MeasureExperiment) ObjectContext.getObjectById(expId));

            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.map_frame_layout, fragment)
                    .commit();
        }
    }
}