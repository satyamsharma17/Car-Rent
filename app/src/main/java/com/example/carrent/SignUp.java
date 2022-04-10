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

    ImageView logoImage;
    TextView logoText;
    TextView sloganText;
    TextInputLayout userName;
    TextInputLayout passWord;
    TextInputLayout registrationName;
    TextInputLayout registrationEmail;
    TextInputLayout registrationPhoneNo;
    Button loginNowButton;
    Button createAccountButton;

    FirebaseDatabase rootNode;
    DatabaseReference reference;
    FirebaseDatabase collection;

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

            pairs[0] = new Pair<View, String>(logoImage,"logo_image");
            pairs[1] = new Pair<View, String>(logoText,"logo_text");
            pairs[2] = new Pair<View, String>(sloganText,"logo_desc");
            pairs[3] = new Pair<View, String>(userName,"username_tran");
            pairs[4] = new Pair<View, String>(passWord,"password_tran");
            pairs[5] = new Pair<View, String>(createAccountButton,"button_tran");
            pairs[6] = new Pair<View, String>(loginNowButton,"login_signup_tran");

            Intent loginNowActivity = new Intent(SignUp.this, Login.class);
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(SignUp.this, pairs);
            startActivity(loginNowActivity, options.toBundle());

        });

        createAccountButton.setOnClickListener(v -> {
            rootNode = FirebaseDatabase.getInstance();
            reference = rootNode.getReference("users");

        String name = registrationName.getEditText().getText().toString();
        String username = userName.getEditText().getText().toString();
        String email = registrationEmail.getEditText().getText().toString();
        String phoneNo = registrationPhoneNo.getEditText().getText().toString();
        String password = passWord.getEditText().getText().toString();

        UserHelperClass helperClass = new UserHelperClass(name, username, email, phoneNo, password);
        reference.child(username).setValue(helperClass);
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
}