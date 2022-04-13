package com.example.carrent;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class UserProfile extends AppCompatActivity {

    TextInputLayout fullName, eMail, phoneNo, passWord;
    TextView fullNameLabel, usernameLabel;

    //  Global variables to hold user's data inside the activity.
    String _USERNAME, _NAME, _EMAIL, _PHONENO, _PASSWORD;

    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        reference = FirebaseDatabase.getInstance().getReference("users");

        //Hooks
        fullName = findViewById(R.id.full_name_profile);
        eMail = findViewById(R.id.email_profile);
        phoneNo = findViewById(R.id.phone_no_profile);
        passWord = findViewById(R.id.password_profile);
        fullNameLabel = findViewById(R.id.fullname_field);
        usernameLabel = findViewById(R.id.username_field);

        //ShowALlData
        showAllUserData();
    }

    private void showAllUserData() {
        Intent intent = getIntent();
        _USERNAME = intent.getStringExtra("username");
        _NAME = intent.getStringExtra("name");
        _EMAIL = intent.getStringExtra("email");
        _PHONENO = intent.getStringExtra("phoneNo");
        _PASSWORD = intent.getStringExtra("password");

        fullNameLabel.setText(_NAME);
        usernameLabel.setText(_USERNAME);
        Objects.requireNonNull(eMail.getEditText()).setText(_EMAIL);
        Objects.requireNonNull(phoneNo.getEditText()).setText(_PHONENO);
        Objects.requireNonNull(fullName.getEditText()).setText(_NAME);
        Objects.requireNonNull(passWord.getEditText()).setText(_PASSWORD);
    }

    public void update(View view) {
        if (isNameChanged() || isPasswordChanged()) {
            Toast.makeText(this, "Data has been updated.", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Data is same and can not be updated.", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean isPasswordChanged() {
        if (!_PASSWORD.equals(Objects.requireNonNull(passWord.getEditText()).getText().toString())) {
            reference.child(_USERNAME).child("password").setValue(passWord.getEditText().getText().toString());
            _PASSWORD = passWord.getEditText().getText().toString();
            return true;
        } else {
            return false;
        }
    }

    private boolean isNameChanged() {
        if (!_NAME.equals(Objects.requireNonNull(fullName.getEditText()).getText().toString())) {
            reference.child(_USERNAME).child("name").setValue(fullName.getEditText().getText().toString());
            _NAME = fullName.getEditText().getText().toString();
            return true;
        } else {
            return false;
        }
    }

}