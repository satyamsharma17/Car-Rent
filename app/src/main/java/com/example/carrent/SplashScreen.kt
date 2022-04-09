package com.example.carrent

import android.annotation.SuppressLint
import android.app.ActivityOptions
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView

@Suppress("DEPRECATION")
@SuppressLint("CustomSplashScreen")
class SplashScreen : AppCompatActivity() {

    private lateinit var handler: Handler
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//      getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_splash_screen)

        handler = Handler()
        handler.postDelayed({

            val intent = Intent(this,Login::class.java)

            val cartoonCarLogo = findViewById<ImageView>(R.id.cartooncarlogo)
            val pairsImage = android.util.Pair<View,String>(cartoonCarLogo,"logo_image")


            val options = ActivityOptions.makeSceneTransitionAnimation(this,pairsImage)
            startActivity(intent, options.toBundle())
            finish()

        }, 4000) // delaying 4.0 seconds to open main activity!

        val topAnim = AnimationUtils.loadAnimation(this,R.anim.top_animation)
        val bottomAnim = AnimationUtils.loadAnimation(this,R.anim.bottom_animation)

        val cartoonCarLogo = findViewById<ImageView>(R.id.cartooncarlogo)
        val title = findViewById<TextView>(R.id.splashtext)
        val tagline = findViewById<TextView>(R.id.tagline)

        cartoonCarLogo.animation = topAnim
        title.animation = bottomAnim
        tagline.animation = bottomAnim
    }

}