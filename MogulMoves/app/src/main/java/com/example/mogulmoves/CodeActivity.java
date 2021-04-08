package com.example.mogulmoves;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ToggleButton;

public class CodeActivity extends AppCompatActivity {

    ListView codeList;
    ArrayAdapter<Barcode> codeAdapter;
    ToggleButton registerBarCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_code);

        codeList = findViewById(R.id.code_list);

    }
}