package com.skapps.YksStudyApp.util

import android.util.Log
import androidx.navigation.NavController
import androidx.navigation.NavDirections

fun NavController.safeNavigate(direction: NavDirections) {
    Log.d("safe", "Click happened")
    currentDestination?.getAction(direction.actionId)?.run {
        Log.d("safe", "Click Propagated")
        navigate(direction)
    }
}