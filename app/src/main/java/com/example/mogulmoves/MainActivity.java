package com.example.mogulmoves;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<User> users = new ArrayList<>();
    private ArrayList<Experiment> experiments = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void publishExperiment(Experiment experiment){
        experiments.add(experiment);
    }
}