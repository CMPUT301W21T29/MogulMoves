package com.example.mogulmoves;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class EditProfilePage extends AppCompatActivity {
    EditText NameEditing, EmailEditing, PhoneEditing;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_user_profile);

        Intent intent = getIntent();
        String originalName = intent.getStringExtra(UserProfilePage.EXTRA_NAME);
        String originalEmail = intent.getStringExtra(UserProfilePage.EXTRA_EMAIL);
        String originalPhone = intent.getStringExtra(UserProfilePage.EXTRA_PHONE);

        NameEditing = findViewById(R.id.edit_name_field);
        EmailEditing = findViewById(R.id.edit_email_field);
        PhoneEditing = findViewById(R.id.edit_phone_field);
        NameEditing.setText(originalName);
        EmailEditing.setText(originalEmail);
        PhoneEditing.setText(originalPhone);

        Button EditProfileSaveButton = findViewById(R.id.edit_user_profile_save_button);
        EditProfileSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}