package com.example.carrent

import android.app.ActivityOptions
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.textfield.TextInputLayout

class Login : AppCompatActivity() {
    private var backPressedTime : Long = 0
        private var backToast : Toast?= null
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_login)

            val logoImage = findViewById<ImageView>(R.id.logo_image)
            val logoText: TextView = findViewById(R.id.logo_text)
            val sloganText: TextView = findViewById(R.id.slogan_text)
            val userName: TextInputLayout = findViewById(R.id.username)
            val passWord: TextInputLayout = findViewById(R.id.password)
            val goLoginButton: Button = findViewById(R.id.go_login_btn)
            val signUpButton: Button = findViewById(R.id.callSignUp)

            signUpButton.setOnClickListener {

                val pairsImage = android.util.Pair<View,String>(logoImage,"logo_image")
                val pairsLogoText = android.util.Pair<View,String>(logoText,"logo_text")
                val pairsSloganText = android.util.Pair<View,String>(sloganText,"logo_desc")
                val pairsUserName = android.util.Pair<View,String>(userName,"username_tran")
                val pairsPassWord = android.util.Pair<View,String>(passWord,"password_tran")
                val pairsLoginButton = android.util.Pair<View,String>(goLoginButton,"button_tran")
                val pairsSignUp = android.util.Pair<View,String>(signUpButton,"login_signup_tran")

                val intent = Intent(this, SignUp::class.java)
                val options = ActivityOptions.makeSceneTransitionAnimation(this,pairsImage, pairsLogoText, pairsSloganText, pairsUserName, pairsUserName, pairsPassWord, pairsLoginButton, pairsSignUp)
                startActivity(intent, options.toBundle())
            }
        }

    override fun onBackPressed() {
        if(backPressedTime + 2000 > System.currentTimeMillis()) {
            backToast?.cancel()
            super.onBackPressed()
            finishAffinity()
        } else {
            backToast = Toast.makeText(baseContext,"Press back again to exit!",Toast.LENGTH_SHORT)
            backToast?.show()
        }
        backPressedTime = System.currentTimeMillis()
    }
}