package com.skapps.YksStudyApp.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentManager
import com.skapps.YksStudyApp.R
import com.skapps.YksStudyApp.util.LocalDatabase
import com.skapps.YksStudyApp.view.Pomodoro.PomodoroViewModel
import kotlin.concurrent.fixedRateTimer

class MainActivity : AppCompatActivity() {
    private lateinit var localDatabase:LocalDatabase
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