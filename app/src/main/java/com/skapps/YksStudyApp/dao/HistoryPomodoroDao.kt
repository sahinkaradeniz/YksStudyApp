package com.skapps.YksStudyApp.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.skapps.YksStudyApp.Model.HistoryPomodoro
@Dao
interface HistoryPomodoroDao {
    @Insert
    suspend fun insertAll(vararg history:HistoryPomodoro){
    }
    @Query("SELECT * FROM pomodoro")
    suspend fun getAllHistory():List<HistoryPomodoro>

    // @Query("SELECT * FROM pomodoro WHERE id = :pId")
   // suspend fun getHistory(pId : Int):HistoryPomodoro

    @Query("DELETE FROM pomodoro")
    suspend fun deleteAllHistory()

}