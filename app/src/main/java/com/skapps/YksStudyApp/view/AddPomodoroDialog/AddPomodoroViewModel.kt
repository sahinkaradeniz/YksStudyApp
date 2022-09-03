package com.skapps.YksStudyApp.view.AddPomodoroDialog

import android.app.Activity
import android.app.Application
import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.RadioButton
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.Navigation
import com.skapps.YksStudyApp.Base.BaseViewModel
import com.skapps.YksStudyApp.Model.HistoryPomodoro
import com.skapps.YksStudyApp.R
import com.skapps.YksStudyApp.dao.HistoryPomodoroDao
import com.skapps.YksStudyApp.database.HPomodoroDatabase
import com.skapps.YksStudyApp.databinding.FragmentAddPomodoroBinding
import com.warkiz.widget.IndicatorSeekBar
import kotlinx.coroutines.launch

class AddPomodoroViewModel(application: Application):BaseViewModel(application ) {
    val pomodoro = MutableLiveData<List<HistoryPomodoro>>()

    fun refreshData(){
        val pm=HistoryPomodoro("Konu Anlatımı",60L)
        val pm2=HistoryPomodoro("Deneme",160L)
        val pm3=HistoryPomodoro("Test",10L)
        val pomodorolist= arrayListOf<HistoryPomodoro>(pm,pm2,pm3)
        pomodoro.value=pomodorolist
    }

    fun store(pomodoro: HistoryPomodoro){
            launch {
                val dao =HPomodoroDatabase(getApplication<Application>().applicationContext).historyDao()
                dao.insertAll(pomodoro)
            }
     }
    fun onRadioButtonClicked(checkId:Int,context: Context):String{
        var activity:String="Diger"
            when (checkId) {
                R.id.rdiger-> { activity="Diğer" }
                R.id.rkitap -> { activity="Kitap" }
                R.id.rdeneme -> { activity="Deneme" }
                R.id.rkonuanlatim -> { activity="Konu Anlatımı" }
                R.id.rsoru ->{ activity = "Soru Çözümü"}
                R.id.rkonutekrar ->{ activity = "Konu Tekrar"}
                 }
        return activity
    }


}
