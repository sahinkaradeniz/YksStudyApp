package com.skapps.YksStudyApp.view.Pomodoro

import android.app.Application
import android.content.Context
import android.os.CountDownTimer
import androidx.lifecycle.MutableLiveData
import com.skapps.YksStudyApp.Base.BaseViewModel
import com.skapps.YksStudyApp.database.LocalDatabase

class PomodoroViewModel(application: Application):BaseViewModel(application) {
    private  var localDatabase= LocalDatabase()
     var time = MutableLiveData<Long>()
    var cTimer: CountDownTimer? = null
    var isCounterRunning = false

    fun startTimer(minute :Long,context: Context){
        localDatabase= LocalDatabase()
        cTimer = object : CountDownTimer(minute, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                time.value=millisUntilFinished
            }
            override fun onFinish() {
               localDatabase.removeSharedPreference(context,"pause")
            }
        }
        (cTimer as CountDownTimer).start()
    }
    private fun yourOperation() {
        if (!isCounterRunning) {
            isCounterRunning = true
            cTimer?.start()
        } else {
            cTimer?.cancel() // cancel
            cTimer?.start() // then restart
        }
    }
    //cancel timer
    fun cancelTimer() {
        if (cTimer != null) cTimer!!.cancel()
    }
    fun onCreatePause(context: Context,pauseTime:Int){
        cancelTimer()
        if (pauseTime != 0){
            startTimer(pauseTime.toLong(),context.applicationContext)
        }else{
            startTimer(25*1000*60,context.applicationContext)
        }
    }
}