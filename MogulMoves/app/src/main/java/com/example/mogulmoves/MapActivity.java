package com.example.mogulmoves;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;

/**
 *Adapter to display a map of trial locations.
 */
public class MapActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_adapter);
        Intent intent = getIntent();
        int expId = Integer.parseInt(intent.getStringExtra("whichExperiment"));
        Experiment exp = ObjectContext.getExperimentById(expId);

        //Initialize fragment
        if (exp instanceof BinomialExperiment) {
            Fragment fragment = new MapFragment((BinomialExperiment) exp);

            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.map_frame_layout, fragment)
                    .commit();
        }
        else if (exp instanceof IntegerCountExperiment) {
            Fragment fragment = new MapFragment((IntegerCountExperiment) exp);

            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.map_frame_layout, fragment)
                    .commit();
        }
        else if (exp instanceof NonNegativeCountExperiment) {
            Fragment fragment = new MapFragment((NonNegativeCountExperiment) exp);

            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.map_frame_layout, fragment)
                    .commit();
        }
        else if (exp instanceof MeasureExperiment) {
            Fragment fragment = new MapFragment((MeasureExperiment) exp);

            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.map_frame_layout, fragment)
                    .commit();
        }
    }
}