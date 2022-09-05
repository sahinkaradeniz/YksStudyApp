package com.skapps.YksStudyApp.Adapter

import android.view.View
import com.skapps.YksStudyApp.Model.Pomodoro

interface HistoryClickListener {
    fun onHistoryClicked(pomodoro: Pomodoro)
}