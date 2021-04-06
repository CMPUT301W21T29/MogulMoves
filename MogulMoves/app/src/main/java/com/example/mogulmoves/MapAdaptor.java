package com.example.mogulmoves;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;

public class MapAdaptor extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_adaptor);

        //Initialize fragment
        Object experiment;
        Fragment fragment = new MapFragment(BinomialExperiment experiment);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.map_frame_layout,fragment)
                .commit();
    }
}