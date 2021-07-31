package com.example.reachmobi_casestudy.view.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.reachmobi_casestudy.R
import com.example.reachmobi_casestudy.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity(){
    private lateinit var mbinding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        mbinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        supportFragmentManager.beginTransaction().replace(R.id.fragmentContainer, SearchFragment()).commit()
          }
    }