package com.example.mogulmoves;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ToggleButton;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.ArrayList;

import static com.google.zxing.integration.android.IntentIntegrator.QR_CODE;

public class CodeActivity extends AppCompatActivity {

    ListView codeList;
    Button qrButton;
    Button barButton;
    ArrayAdapter<Barcode> adapter;

    static int tempExperiment;
    static String tempAction;

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

                // shouldn't do anything i think

            }
        });

        adapter = new BarcodeAdapter(this, ObjectContext.barcodes);
        codeList.setAdapter(adapter);
        adapter.notifyDataSetChanged();

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

    public void showQR(String action) {

        QRDisplayFragment newFragment = QRDisplayFragment.newInstance(action);
        newFragment.show(getSupportFragmentManager(), "DISPLAY_CODE");

    }

    public void registerCode(int experiment, String action) {


        tempExperiment = experiment;
        tempAction = action;

        IntentIntegrator intentIntegrator = new IntentIntegrator(this);
        intentIntegrator.initiateScan();

    }

    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (intentResult != null) {

            String result = intentResult.getContents();

            if (result == null) {

                // no result, do something i guess

            } else {

                int experiment = tempExperiment;
                String action = tempAction;

                Barcode code = new Barcode(experiment, ObjectContext.userDatabaseId, result, action);
                ObjectContext.addBarcode(code, ObjectContext.getUserById(ObjectContext.userDatabaseId));

            }
        }

        super.onActivityResult(requestCode, resultCode, data);

    }
}