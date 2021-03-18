/*package com.example.mogulmoves;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class QuestionActivity extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        initQAModule();
    }

    private void initQAModule() {
        Spinner expSpinner = findViewById(R.id.spinner);

        EditText txtSubject = findViewById(R.id.txtSubject);
        EditText txtEmail = findViewById(R.id.txtEmail);
        EditText txtQuestion = findViewById(R.id.txtQuestion);

        Button btnQuestionSubmit = findViewById(R.id.btnSubmitQuestion);
        btnQuestionSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String experiment = expSpinner.getSelectedItem().toString();
                String subject = txtSubject.getText().toString();
                String email = txtEmail.getText().toString();
                String question = txtQuestion.getText().toString();

                Map<String, Object> docData = new HashMap<>();
                docData.put("subject", subject);
                docData.put("experiment", experiment);
                docData.put("email", email);
                docData.put("question", question);

                db.collection("questions")
                    .add(docData)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            System.out.println("Added");
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            System.out.println("Addition Failed");
                        }
                    });

            }
        });
    }
}*/