package com.example.reachmobi_casestudy.view.ui

/**
 * Splash Screen class
 *
 * Author: Nikhil Surya Peteti
 * email: peteti.nikhil@gmail.com
 */
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.WindowManager
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceManager
import com.example.reachmobi_casestudy.R

class SplashScreen: AppCompatActivity() {

    var SPLASH_SCREEN_TIMEOUT = 2000
    lateinit var splashImage:ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        @Suppress("DEPRECATION")
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.splash_screen)

         splashImage = findViewById(R.id.splashScreenImageId)
         splashImage.alpha = 0f
            splashImage.animate().setDuration(SPLASH_SCREEN_TIMEOUT.toLong()).alpha(1f).withEndAction {

                PreferenceManager.getDefaultSharedPreferences(this).apply {
                    if(!getBoolean("OnBoardingUsageFlag", false)){
                        Log.d("demo", "onCreate: onboarding")
                        startActivity(Intent(this@SplashScreen, Onboarding::class.java))
                    }else{
                        startActivity(Intent(this@SplashScreen, MainActivity::class.java))
                    }
                }
            overridePendingTransition(R.anim.fade_in_left, R.anim.fade_out)
            finish()
            }

    }
}