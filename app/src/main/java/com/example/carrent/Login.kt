package com.example.carrent

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.database.*
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class Login : AppCompatActivity() {
    private var backPressedTime: Long = 0
    private var backToast: Toast? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val logoImage: ImageView = findViewById(R.id.logo_image)
        val logoText: TextView = findViewById(R.id.logo_text)
        val sloganText: TextView = findViewById(R.id.slogan_text)
        val userName: TextInputLayout = findViewById(R.id.username)
        val passWord: TextInputLayout = findViewById(R.id.password)
        val goLoginButton: Button = findViewById(R.id.go_login_btn)
        val signUpButton: Button = findViewById(R.id.callSignUp)

        signUpButton.setOnClickListener {

            val pairsImage = android.util.Pair<View, String>(logoImage, "logo_image")
            val pairsLogoText = android.util.Pair<View, String>(logoText, "logo_text")
            val pairsSloganText = android.util.Pair<View, String>(sloganText, "logo_desc")
            val pairsUserName = android.util.Pair<View, String>(userName, "username_tran")
            val pairsPassWord = android.util.Pair<View, String>(passWord, "password_tran")
            val pairsLoginButton = android.util.Pair<View, String>(goLoginButton, "button_tran")
            val pairsSignUp = android.util.Pair<View, String>(signUpButton, "login_signup_tran")

            val intent = Intent(this, SignUp::class.java)
            val options = ActivityOptions.makeSceneTransitionAnimation(
                this,
                pairsImage,
                pairsLogoText,
                pairsSloganText,
                pairsUserName,
                pairsUserName,
                pairsPassWord,
                pairsLoginButton,
                pairsSignUp
            )
            startActivity(intent, options.toBundle())
        }
    }

    private fun validateUsername(): Boolean {
        val userName: TextInputLayout = findViewById(R.id.username)
        val `val`: String = userName.editText?.text.toString()
        return if (`val`.isEmpty()) {
            userName.error = "Field cannot be empty."
            false
        } else {
            userName.error = null
            userName.isErrorEnabled = false
            true
        }
    }

    private fun validatePassword(): Boolean {
        val passWord: TextInputLayout = findViewById(R.id.password)
        val `val`: String = passWord.editText?.text.toString()
        return if (`val`.isEmpty()) {
            passWord.error = "Field cannot be empty."
            false
        } else {
            passWord.error = null
            passWord.isErrorEnabled = false
            true
        }
    }

    fun loginUser(view: View) {
        if (!validateUsername() or !validatePassword()) {
            return
        } else {
            isUser()
        }
    }

    private fun isUser() {
        val userName: TextInputLayout = findViewById(R.id.username)
        val passWord: TextInputLayout = findViewById(R.id.password)
        val userEnteredUsername = userName.editText!!.text.toString().trim { it <= ' ' }
        val userEnteredPassword = passWord.editText!!.text.toString().trim { it <= ' ' }

        val reference = FirebaseDatabase.getInstance().getReference("users")
        val checkUser = reference.orderByChild("username").equalTo(userEnteredUsername)
        checkUser.addListenerForSingleValueEvent(object : ValueEventListener {

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    userName.error = null
                    userName.isErrorEnabled = false

                    val passwordFromDB: String? =
                        dataSnapshot.child(userEnteredUsername).child("password").getValue(
                            String::class.java
                        )
                    if (passwordFromDB == userEnteredPassword) {
                        userName.error = null
                        userName.isErrorEnabled = false

                        Toast.makeText(this@Login, "You can login now!", Toast.LENGTH_SHORT).show()

                        val nameFromDB: String =
                            dataSnapshot.child(userEnteredUsername).child("name").getValue(
                                String::class.java,
                            )!!
                        val usernameFromDB: String =
                            dataSnapshot.child(userEnteredUsername).child("username").getValue(
                                String::class.java,
                            )!!
                        val phoneNoFromDB: String =
                            dataSnapshot.child(userEnteredUsername).child("phoneNo").getValue(
                                String::class.java,
                            )!!
                        val emailFromDB: String =
                            dataSnapshot.child(userEnteredUsername).child("email").getValue(
                                String::class.java,
                            )!!
                        val intent = Intent(applicationContext, UserProfile::class.java)
                        intent.putExtra("name", nameFromDB)
                        intent.putExtra("username", usernameFromDB)
                        intent.putExtra("password", passwordFromDB)
                        intent.putExtra("phoneNo", phoneNoFromDB)
                        intent.putExtra("email", emailFromDB)
                        startActivity(intent)
                    } else {
                        passWord.error = "Wrong password."
                        passWord.requestFocus()
                    }
                } else {
                    userName.error = "No such user exist."
                    userName.requestFocus()
                }
            }

            override fun onCancelled(error: DatabaseError) {}
        })
    }

    override fun onBackPressed() {
        if (backPressedTime + 2000 > System.currentTimeMillis()) {
            backToast?.cancel()
            super.onBackPressed()
            finishAffinity()
        } else {
            backToast = Toast.makeText(baseContext, "Press back again to exit!", Toast.LENGTH_SHORT)
            backToast?.show()
        }
        backPressedTime = System.currentTimeMillis()
    }
}