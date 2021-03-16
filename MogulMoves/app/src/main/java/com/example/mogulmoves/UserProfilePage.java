package com.example.mogulmoves;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class UserProfilePage extends AppCompatActivity {
    TextView fullName, email, phone;
    User user;

    String currentName = "another name";
    String currentEmail = "another email";
    String currentPhoneNumber = "another phone";

    public static final String EXTRA_NAME = "com.example.mogulmoves.EXTRA_NAME";
    public static final String EXTRA_EMAIL = "com.example.mogulmoves.EXTRA_EMAIL";
    public static final String EXTRA_PHONE = "com.example.mogulmoves.EXTRA_PHONE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_profile);


        fullName = findViewById(R.id.full_name);
        email = findViewById(R.id.email_address);
        phone = findViewById(R.id.phone_number);

        fullName.setText(currentName);
        email.setText(currentEmail);
        phone.setText(currentPhoneNumber);

    }

    public void switch_to_edit(View view) {
        TextView editButton = findViewById(R.id.edit_profile_button);
        switchToEditUserProfile(currentName,currentEmail,currentPhoneNumber);
    }

    public void switchToEditUserProfile(String CN, String CE, String CF){
        Intent intent = new Intent(this, EditProfilePage.class);
        intent.putExtra(EXTRA_NAME, CN);
        intent.putExtra(EXTRA_EMAIL, CE);
        intent.putExtra(EXTRA_PHONE, CF);
        startActivity(intent);
    }
}