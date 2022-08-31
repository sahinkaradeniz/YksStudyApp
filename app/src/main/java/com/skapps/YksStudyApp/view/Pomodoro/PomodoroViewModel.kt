package com.skapps.YksStudyApp.view.Pomodoro

import android.content.Context
import android.content.SharedPreferences
import android.os.CountDownTimer
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.skapps.YksStudyApp.util.CustomSharedPreferences
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.text.DateFormat

class PomodoroViewModel:ViewModel() {
    private var customSharedPreferences= CustomSharedPreferences()
    private var time = MutableLiveData<Int>()
    private val cDF=100

    suspend fun timer(){
        GlobalScope.launch {
            flow<Int> {
                var counter =cDF
                emit(cDF)
                while (counter>0){
                    delay(999)
                    counter--
                    emit(counter.toInt())
                }
            }
        }
    }
    val destroytime=customSharedPreferences.getTime()
    val data = flow<Int>() {
        if (destroytime !=null){
            var counter =cDF-destroytime
            emit(cDF)
            while (counter>0){
                delay(999)
                counter--
                emit(counter.toInt())

            }
        }else{
            var counter =cDF
            emit(cDF)
            while (counter>0){
                delay(999)
                counter--
                emit(counter.toInt())

            }

    }
}
    fun setSharedPreference(context: Context, key: String?, time: String?) {
        val sharedPref: SharedPreferences = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE)
        val edit = sharedPref.edit()
        edit.putString(key,time)
        edit.commit()
    }

    fun getSharedPreference(context: Context, key: String?, defaultValue: String?): String {
        return context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE)
            .getString(key, defaultValue).toString()
    }

    fun clearSharedPreference(context: Context) {
        val sharedPref: SharedPreferences =
            context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE)
        val edit = sharedPref.edit()
        edit.clear()
        edit.commit()
    }

    fun removeSharedPreference(context: Context, key: String?) {
        val sharedPref: SharedPreferences =
            context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE)
        val edit = sharedPref.edit()
        edit.remove(key)
        edit.commit()
    }

}