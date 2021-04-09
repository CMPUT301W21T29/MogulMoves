package com.example.mogulmoves;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

/*
 * Activity to view user profile data. A button allows you to edit it, and another allows you to
 * manage QR and bar code data. The latter is not implemented yet.
 */

public class UserProfilePage extends AppCompatActivity {
    TextView fullName, email, phone;
    User currentUser = ObjectContext.getUserById(ObjectContext.userDatabaseId);

    /*String currentName = "test name";
    String currentEmail = "test email";
    String currentPhoneNumber = "test phone";*/

    String editedName, editedEmail, editedPhone;

    //public static final String EXTRA_NAME = "com.example.mogulmoves.EXTRA_NAME";
    //public static final String EXTRA_EMAIL = "com.example.mogulmoves.EXTRA_EMAIL";
    //public static final String EXTRA_PHONE = "com.example.mogulmoves.EXTRA_PHONE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_profile);

        int user_id = getIntent().getIntExtra("userID", -1);
        //defaultValue just set to -1 because it should never call a nonexistent user anyway

        if (user_id != ObjectContext.userDatabaseId) {
            TextView editButton = findViewById(R.id.edit_profile_button);
            editButton.setVisibility(View.INVISIBLE);
        }

        currentUser = ObjectContext.getUserById(user_id);

        fullName = findViewById(R.id.full_name);
        email = findViewById(R.id.email_address);
        phone = findViewById(R.id.phone_number);

        fullName.setText(currentUser.getUsername());
        email.setText(currentUser.getEmail());
        phone.setText(currentUser.getPhone());
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        fullName.setText(currentUser.getUsername());
        email.setText(currentUser.getEmail());
        phone.setText(currentUser.getPhone());
    }

    public void switch_to_edit(View view) {
        User currentUser = ObjectContext.getUserById(ObjectContext.userDatabaseId);

        String username = currentUser.getUsername();
        String email = currentUser.getEmail();
        String phone = currentUser.getPhone();

        switchToEditUserProfile(username, email, phone);
    }

    public void switchToEditUserProfile(String CurrentName, String CurrentEmail, String CurrentPhone){
        Intent intent = new Intent(this, EditProfilePage.class);
        intent.putExtra("EXTRA_NAME", CurrentName);
        intent.putExtra("EXTRA_EMAIL", CurrentEmail);
        intent.putExtra("EXTRA_PHONE", CurrentPhone);
        startActivity(intent);
    }

    public void goBack(View view){
        finish();
    }
}