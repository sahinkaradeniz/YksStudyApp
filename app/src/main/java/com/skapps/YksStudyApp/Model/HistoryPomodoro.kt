package com.skapps.YksStudyApp.Model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pomodoro")
data class HistoryPomodoro(
    @ColumnInfo(name="activity")
    var activity:String,
    @ColumnInfo(name = "time")
    var time:Long) {
    @PrimaryKey(autoGenerate = true)
    var id=0
}