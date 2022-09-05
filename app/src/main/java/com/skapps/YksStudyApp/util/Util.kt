package com.skapps.YksStudyApp.util

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import java.util.*
private lateinit var calendar: Calendar
fun NavController.safeNavigate(direction: NavDirections) {
    Log.d("safe", "Click happened")
    currentDestination?.getAction(direction.actionId)?.run {
        Log.d("safe", "Click Propagated")
        navigate(direction)
    }
}

fun NavController.clearBackNavigate(){

}

@SuppressLint("Range")
fun timepic(){
    val picker = MaterialTimePicker.Builder()
        .setTimeFormat(TimeFormat.CLOCK_24H)
        .setMinute(10)
        .setTitleText("Select Appointment time")
        .build()
    //picker.show(requireActivity().supportFragmentManager, picker.toString())

    picker.addOnPositiveButtonClickListener {
        calendar = Calendar.getInstance()
        calendar[Calendar.HOUR_OF_DAY] = picker.hour
        calendar[Calendar.MINUTE] = picker.minute
        calendar[Calendar.SECOND] = 0
        calendar[Calendar.MILLISECOND] =0
        val hour=picker.hour
        var total=hour*60*1000*1000*60
        val minute=picker.minute
    }

}