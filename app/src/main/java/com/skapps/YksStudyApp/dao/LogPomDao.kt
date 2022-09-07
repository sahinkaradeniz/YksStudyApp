package com.skapps.YksStudyApp.dao

import androidx.room.*
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.skapps.YksStudyApp.Model.LogPomodoro

@Dao
interface LogPomDao{
    @Insert
    suspend fun insert(history:LogPomodoro)
    @Query("SELECT * FROM logpomodoro")
    suspend fun getAllHistory():List<LogPomodoro>
    @Delete
    suspend fun deleteHistory(historyItem:LogPomodoro)
}