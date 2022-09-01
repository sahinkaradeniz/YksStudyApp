package com.skapps.YksStudyApp.view.Pomodoro

import android.content.Context
import android.os.CountDownTimer
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.skapps.YksStudyApp.util.LocalDatabase

class PomodoroViewModel:ViewModel() {
    private lateinit var localDatabase:LocalDatabase
     var time = MutableLiveData<Long>()
    private val cDF=100
    var cTimer: CountDownTimer? = null
    private var destroyTime:Long = 0


// viewLifecycleOwner.lifecycleScope.launchWhenStarted{
//            viewModel.viewModelScope.launch {
//                viewModel.data.collect{
//               //     binding!!.choronometre.text=it.toString()
//                    destroyTime=it
//                }
//            }
//        }


    //suspend fun timer(){
    //        GlobalScope.launch {
    //            flow<Int> {
    //                var counter =cDF
    //                emit(cDF)
    //                while (counter>0){
    //                    delay(999)
    //                    counter--
    //                    emit(counter.toInt())
    //                }
    //            }
    //        }
    //    }

    //start timer function
    fun startTimer(minute :Long,context: Context){
        localDatabase= LocalDatabase(context)
        cTimer = object : CountDownTimer(minute, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                time.value=millisUntilFinished
            }
            override fun onFinish() {
               localDatabase.removeSharedPreference(context.applicationContext,"pauseTime")
            }
        }
        (cTimer as CountDownTimer).start()
    }

    //cancel timer
    fun cancelTimer() {
        if (cTimer != null) cTimer!!.cancel()
    }
}