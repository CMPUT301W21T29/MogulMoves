package com.example.mogulmoves;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<User> users = new ArrayList<>();
    private ArrayList<Experiment> experiments = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //******************************TEST
        Button testButton = findViewById(R.id.test_button);
        testButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToUserProfile();
            }
        });
        //******************************TEST
    }

    //******************************TEST
    public void switchToUserProfile(){
        Intent intent = new Intent(this,UserProfilePage.class);
        startActivity(intent);
    }
    //******************************TEST

    public void publishExperiment(Experiment experiment){
        experiments.add(experiment);
    }
}