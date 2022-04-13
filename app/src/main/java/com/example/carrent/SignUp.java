package com.example.carrent;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class SignUp extends AppCompatActivity {

    private long pressedTime;

    protected ImageView logoImage;
    protected TextView logoText;
    protected TextView sloganText;
    protected TextInputLayout userName;
    protected TextInputLayout passWord;
    protected TextInputLayout registrationName;
    protected TextInputLayout registrationEmail;
    protected TextInputLayout registrationPhoneNo;
    protected Button loginNowButton;
    protected Button createAccountButton;

    protected FirebaseDatabase rootNode;
    protected DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        logoImage = findViewById(R.id.logo_image);
        logoText = findViewById(R.id.logo_text);
        sloganText = findViewById(R.id.slogan_text);
        registrationName = findViewById(R.id.registration_name);
        registrationEmail = findViewById(R.id.registration_email);
        registrationPhoneNo = findViewById(R.id.registration_phoneNo);
        userName = findViewById(R.id.registration_username);
        passWord = findViewById(R.id.registration_password);
        createAccountButton = findViewById(R.id.create_account_button);
        loginNowButton = findViewById(R.id.login_now_button);

        loginNowButton.setOnClickListener(v -> {

            Pair[] pairs = new Pair[7];

            pairs[0] = new Pair<View, String>(logoImage, "logo_image");
            pairs[1] = new Pair<View, String>(logoText, "logo_text");
            pairs[2] = new Pair<View, String>(sloganText, "logo_desc");
            pairs[3] = new Pair<View, String>(userName, "username_tran");
            pairs[4] = new Pair<View, String>(passWord, "password_tran");
            pairs[5] = new Pair<View, String>(createAccountButton, "button_tran");
            pairs[6] = new Pair<View, String>(loginNowButton, "login_signup_tran");

            Intent loginNowActivity = new Intent(SignUp.this, Login.class);
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(SignUp.this, pairs);
            startActivity(loginNowActivity, options.toBundle());

        });
    }

    @Override
    public void onBackPressed() {
        if (pressedTime + 2000 > System.currentTimeMillis()) {
            super.onBackPressed();
            finishAffinity();
        } else {
            Toast.makeText(getBaseContext(), "Press back again to exit!", Toast.LENGTH_SHORT).show();
        }
        pressedTime = System.currentTimeMillis();
    }

    private Boolean validateName() {
        String val = Objects.requireNonNull(registrationName.getEditText()).getText().toString();

        if (val.isEmpty()) {
            registrationName.setError("Field cannot be empty.");
            return false;
        } else {
            registrationName.setError(null);
            registrationName.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validateUserName() {
        String val = Objects.requireNonNull(userName.getEditText()).getText().toString();
        String noWhiteSpace = "\\A\\w{4,20}\\z";

        if (val.isEmpty()) {
            userName.setError("Field cannot be empty.");
            return false;
        } else if (val.length() >= 15) {
            userName.setError("Username is too long.");
            return false;
        } else if (!val.matches(noWhiteSpace)) {
            userName.setError("White spaces are not allowed.");
            return false;
        } else {
            userName.setError(null);
            userName.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validateEmail() {
        String val = Objects.requireNonNull(registrationEmail.getEditText()).getText().toString();
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        if (val.isEmpty()) {
            registrationEmail.setError("Field cannot be empty.");
            return false;
        } else if (!val.matches(emailPattern)) {
            registrationEmail.setError("Invalid email address!");
            return false;
        } else {
            registrationEmail.setError(null);
            registrationEmail.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validatePhoneNumber() {
        String val = Objects.requireNonNull(registrationPhoneNo.getEditText()).getText().toString();

        if (val.isEmpty()) {
            registrationPhoneNo.setError("Field cannot be empty.");
            return false;
        } else {
            registrationPhoneNo.setError(null);
            registrationPhoneNo.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validatePassword() {
        String val = Objects.requireNonNull(passWord.getEditText()).getText().toString();
        String passwordVal = "^" +
//              "(?=.*[0-9])" + at least 1 digit
//              "(?=.*[a-z])" + at least 1 lower case letter
//              "(?=.*[A-Z])" + at least 1 upper case letter
                "(?=.*[a-zA-Z])" +    // any letter
                "(?=.*[@#$%^&+=])" +  // at least 1 special character
                "(?=\\S+$)" +         // no white spaces
                ".{4,}" +             // at least 4 characters
                "$";

        if (val.isEmpty()) {
            passWord.setError("Field cannot be empty.");
            return false;
        } else if (!val.matches(passwordVal)) {
            passWord.setError("Password is too weak.");
            return false;
        } else {
            passWord.setError(null);
            passWord.setErrorEnabled(false);
            return true;
        }
    }

    public void registerUser(View view) {

        if (!validateName() | !validatePassword() | !validatePhoneNumber() | !validateEmail() | !validateUserName()) {
            return;
        } else {

            rootNode = FirebaseDatabase.getInstance();
            reference = rootNode.getReference("users");

            String name = Objects.requireNonNull(registrationName.getEditText()).getText().toString();
            String username = Objects.requireNonNull(userName.getEditText()).getText().toString();
            String email = Objects.requireNonNull(registrationEmail.getEditText()).getText().toString();
            String phoneNo = Objects.requireNonNull(registrationPhoneNo.getEditText()).getText().toString();
            String password = Objects.requireNonNull(passWord.getEditText()).getText().toString();

            UserHelperClass helperClass = new UserHelperClass(name, username, email, phoneNo, password);
            reference.child(username).setValue(helperClass);

            Intent intent = new Intent(getApplicationContext(), VerifyPhoneNo.class);
            intent.putExtra("phoneNo", phoneNo);
            startActivity(intent);
        }
    }
}