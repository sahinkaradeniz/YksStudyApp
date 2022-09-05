package com.skapps.YksStudyApp.view.Pomodoro

import android.content.Context
import android.os.CountDownTimer
import androidx.lifecycle.MutableLiveData
import com.skapps.YksStudyApp.util.LocalDatabase

class CoutDown {
    private  var localDatabase= LocalDatabase()
    var time = MutableLiveData<Long>()
    var cTimer: CountDownTimer? = null

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

    //cancel timer
    fun cancelTimer() {
        if (cTimer != null) cTimer!!.cancel()
    }
    fun onCreatePause(context: Context, pauseTime:Int){
        cancelTimer()
        if (pauseTime != 0){
            startTimer(pauseTime.toLong(),context.applicationContext)
        }else{
            startTimer(25*1000*60,context.applicationContext)
        }
    }
}