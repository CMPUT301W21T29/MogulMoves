package com.example.mogulmoves;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

//Activity to edit profile information.

public class EditProfilePage extends AppCompatActivity {
    EditText NameEditing;
    EditText EmailEditing;
    EditText PhoneEditing;

    public static final String BACK_NAME = "com.example.mogulmoves.BACK_NAME";
    public static final String BACK_EMAIL = "com.example.mogulmoves.BACK_EMAIL";
    public static final String BACK_PHONE = "com.example.mogulmoves.BACK_PHONE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_user_profile);

        Intent intent = getIntent();
        String originalName = intent.getStringExtra("EXTRA_NAME");
        String originalEmail = intent.getStringExtra("EXTRA_EMAIL");
        String originalPhone = intent.getStringExtra("EXTRA_PHONE");
        Log.d("wide", originalName);

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
                String newName = NameEditing.getText().toString();
                String newEmail = EmailEditing.getText().toString();
                String newPhone = PhoneEditing.getText().toString();
                switchBackToUserProfile(newName, newEmail, newPhone);
            }
        });

    }

    public void switchBackToUserProfile(String newName, String newEmail, String newPhone){

        // save changes
        User currentUser = (User) ObjectContext.getObjectById(ObjectContext.userDatabaseId);

        currentUser.setUsername(newName);
        currentUser.setEmail(newEmail);
        currentUser.setPhone(newPhone);

        ObjectContext.pushUserData(currentUser);

        Intent intent = new Intent(this, UserProfilePage.class);
        intent.putExtra(BACK_NAME, newName);
        intent.putExtra(BACK_EMAIL, newEmail);
        intent.putExtra(BACK_PHONE, newPhone);
        startActivity(intent);
    }
}