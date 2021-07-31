package com.example.reachmobi_casestudy.view.ui

/**
 *OnBoarding class file
 *
 * Author: Nikhil Surya Peteti
 * email: peteti.nikhil@gmail.com
 */
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.edit
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.reachmobi_casestudy.R
import com.example.reachmobi_casestudy.model.OnboardingItem
import com.example.reachmobi_casestudy.view.adapter.OnBoardngItemsAdapter
import com.google.android.gms.ads.*
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.google.android.material.button.MaterialButton
import kotlin.math.log

class Onboarding: AppCompatActivity() {
    
    val TAG = "demo"
    private lateinit var onBoardingAdapter: OnBoardngItemsAdapter
    private lateinit var indicatorContainer: LinearLayout
    private var mInterstitialAd: InterstitialAd? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.walk_through_screen)
        loadInterAd()
        setOnboardingItems()
        setupIndicators()
        setCurrentIndicator(0)
    }


    override fun onStop() {
        PreferenceManager.getDefaultSharedPreferences(this).apply {
            this.edit {
                putBoolean("OnBoardingUsageFlag", true)
            }
        }
        super.onStop()
    }


    private fun showInterAd() {

        Log.d(TAG, "showInterAd: ")

        if(mInterstitialAd != null){
            mInterstitialAd?.fullScreenContentCallback = object : FullScreenContentCallback(){
                override fun onAdFailedToShowFullScreenContent(p0: AdError) {
                    super.onAdFailedToShowFullScreenContent(p0)
                    Log.d(TAG, "onAdDismissedFullScreenContent: ")
                }

                override fun onAdShowedFullScreenContent() {
                    super.onAdShowedFullScreenContent()
                    Log.d(TAG, "onAdFailedToShowFullScreenContent: ")
                }

                override fun onAdDismissedFullScreenContent() {
                    super.onAdDismissedFullScreenContent()
                    navigateToHomeScreen()
                    Log.d(TAG, "onAdImpression: ")
                }

                override fun onAdImpression() {
                    super.onAdImpression()
                    Log.d(TAG, "onAdShowedFullScreenContent: ")
                }

            }
            mInterstitialAd?.show(this)
        }else{
            navigateToHomeScreen()
        }
    }

    private fun loadInterAd() {

        MobileAds.initialize(this){}
        var adRequest = AdRequest.Builder().build()

        InterstitialAd.load(this,"ca-app-pub-3940256099942544/1033173712", adRequest, object : InterstitialAdLoadCallback() {

            override fun onAdFailedToLoad(adError: LoadAdError) {
                mInterstitialAd = null
            }

            override fun onAdLoaded(interstitialAd: InterstitialAd) {
                mInterstitialAd = interstitialAd
            }
        })
    }



    private fun setOnboardingItems(){

        onBoardingAdapter = OnBoardngItemsAdapter(
            listOf(
                OnboardingItem(
                    R.drawable.first_image_walkthrough,
                    "Welcome to SportZBuzz",
                    "A Platform to track your favorite sports on event basis"),
                OnboardingItem(
                        R.drawable.second_image_walkthrough,
                "Join the Others",
                "Come and Join along with others on your most loved sport"),
                OnboardingItem(
                    R.drawable.third_image_walkthrough,
                    "Rate us Play Store",
                    "Please take a minute for feedback, we would like to enhance because your experience is more than anythning"),
                )
        )
        findViewById<TextView>(R.id.textSkip).setOnClickListener {
            showInterAd()
        }

        findViewById<MaterialButton>(R.id.buttonGetStarted).setOnClickListener {
            showInterAd()
        }


        val onBoardingViewPager = findViewById<ViewPager2>(R.id.onBoardngViewpager)
        onBoardingViewPager.adapter = onBoardingAdapter
        onBoardingViewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                setCurrentIndicator(position)
            }
        })
        (onBoardingViewPager.getChildAt(0) as RecyclerView).overScrollMode = RecyclerView.OVER_SCROLL_NEVER
        findViewById<ImageView>(R.id.imageNext).setOnClickListener {
            if(onBoardingViewPager.currentItem +1 < onBoardingAdapter.itemCount){
                onBoardingViewPager.currentItem+=1
            }else{
                showInterAd()
            }
        }
    }

    private fun navigateToHomeScreen(){
        startActivity(Intent(applicationContext, MainActivity::class.java))
        finish()
    }

    private fun setupIndicators(){

        indicatorContainer = findViewById(R.id.indicatorContainer)
        val indicators = arrayOfNulls<ImageView>(onBoardingAdapter.itemCount)
        val layoutparams: LinearLayout.LayoutParams =
            LinearLayout.LayoutParams(WRAP_CONTENT,WRAP_CONTENT)
        layoutparams.setMargins(8,0,8,0)
        for (i in indicators.indices){
            indicators[i] = ImageView(applicationContext)
            indicators[i]?.let {
                it.setImageDrawable(ContextCompat.getDrawable(applicationContext, R.drawable.indicator_inactive_background))
            it.layoutParams = layoutparams
            indicatorContainer.addView(it)
            }
        }
    }

    private fun setCurrentIndicator(position:Int){
        val childCount = indicatorContainer.childCount
        for (i in 0 until childCount){
            val imageView = indicatorContainer.getChildAt(i) as ImageView
            if(i==position){
                imageView.setImageDrawable(
                    ContextCompat.getDrawable(applicationContext, R.drawable.indicator_active_background)
                )
            }else{
                imageView.setImageDrawable(
                    ContextCompat.getDrawable(applicationContext, R.drawable.indicator_inactive_background)
                )
            }
        }
    }
}