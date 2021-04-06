package com.example.mogulmoves;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;

public class MapAdaptor extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_adaptor);
        Intent intent = getIntent();
        int expId = Integer.parseInt(intent.getStringExtra("whichExperiment"));

        //Initialize fragment
        Fragment fragment = new MapFragment();

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.map_frame_layout,fragment)
                .commit();
    }
}