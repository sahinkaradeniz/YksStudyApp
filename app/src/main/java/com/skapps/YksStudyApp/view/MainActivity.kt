package com.skapps.YksStudyApp.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.skapps.YksStudyApp.R
import com.skapps.YksStudyApp.database.LocalDatabase

class MainActivity : AppCompatActivity() {
    private lateinit var localDatabase: LocalDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)// STOPSHIP
        setContentView(R.layout.activity_main)
    }

    override fun onDestroy() {
        super.onDestroy()
        localDatabase= LocalDatabase()
        localDatabase.clearSharedPreference(applicationContext)
      localDatabase.removeSharedPreference(applicationContext,"pause")
    }


}