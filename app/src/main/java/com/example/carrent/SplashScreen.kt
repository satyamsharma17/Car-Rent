package com.example.carrent

import android.app.ActivityOptions
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.WindowManager
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView

class SplashScreen : AppCompatActivity() {

    private lateinit var handler: Handler
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//      getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_splash_screen)

        handler = Handler()
        handler.postDelayed({

            val intent = Intent(this,Login::class.java)

            var image = findViewById<ImageView>(R.id.cartooncarlogo)
            var pairsImage = android.util.Pair<View,String>(image,"logo_image")


            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                val options = ActivityOptions.makeSceneTransitionAnimation(this,pairsImage)
                startActivity(intent, options.toBundle())
                finish()
            }

        }, 4500) // delaying 4.5 seconds to open main activity!

        val topAnim = AnimationUtils.loadAnimation(this,R.anim.top_animation)
        val bottomAnim = AnimationUtils.loadAnimation(this,R.anim.bottom_animation)

        val cartoonLogo = findViewById<ImageView>(R.id.cartooncarlogo)
        val title = findViewById<TextView>(R.id.splashtext)
        val tagline = findViewById<TextView>(R.id.tagline)

        cartoonLogo.animation = topAnim
        title.animation = bottomAnim
        tagline.animation = bottomAnim
    }

}