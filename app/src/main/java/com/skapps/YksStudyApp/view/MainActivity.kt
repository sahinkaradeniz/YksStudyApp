package com.skapps.YksStudyApp.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.skapps.YksStudyApp.R
import com.skapps.YksStudyApp.util.LocalDatabase
import com.skapps.YksStudyApp.view.Pomodoro.PomodoroViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var localDatabase:LocalDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onDestroy() {
        super.onDestroy()
        localDatabase= LocalDatabase(context = applicationContext)
        localDatabase.removeSharedPreference(applicationContext.applicationContext,"pauseTime")
    }
}