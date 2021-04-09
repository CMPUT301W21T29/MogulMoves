package com.example.mogulmoves;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ToggleButton;

public class CodeActivity extends AppCompatActivity {

    ListView codeList;
    Button qrButton;
    Button barButton;
    ArrayAdapter<Barcode> codeAdapter;
    ToggleButton registerBarCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_code);

        codeList = findViewById(R.id.code_list);
        qrButton = findViewById(R.id.generate_qr_button);
        barButton = findViewById(R.id.register_bar_button);

        codeList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {          // tap item in code view list



            }
        });

        qrButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CodeGeneratorFragment newFragment = CodeGeneratorFragment.newInstance(true);
                newFragment.show(getSupportFragmentManager(), "GENERATE_QR_CODE");
            }
        });

        barButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CodeGeneratorFragment newFragment = CodeGeneratorFragment.newInstance(false);
                newFragment.show(getSupportFragmentManager(), "REGISTER_BAR_CODE");
            }
        });

    }
}