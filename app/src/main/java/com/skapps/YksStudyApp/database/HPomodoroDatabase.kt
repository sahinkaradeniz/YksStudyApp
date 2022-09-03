package com.skapps.YksStudyApp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.skapps.YksStudyApp.Model.HistoryPomodoro
import com.skapps.YksStudyApp.dao.HistoryPomodoroDao

@Database(entities = arrayOf(HistoryPomodoro::class), version = 1)
abstract class HPomodoroDatabase :RoomDatabase(){
    abstract fun historyDao():HistoryPomodoroDao

    companion object{
        @Volatile private  var instance :HPomodoroDatabase?=null // herhangi bir property volatile olarak tanımlandığında farklı tharedlarda görülebilir hale gelir
        private val lock =Any()
        operator fun invoke (context: Context) = instance?: synchronized(lock){
                instance?: makeDatabase(context).also {
                    instance=it
                }
        }

        private fun makeDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,HPomodoroDatabase::class.java,"pomodoro"
        ).build()
    }
}