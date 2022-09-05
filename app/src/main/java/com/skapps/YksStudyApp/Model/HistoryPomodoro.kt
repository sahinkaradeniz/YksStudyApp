package com.skapps.YksStudyApp.Model


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity
data class Pomodoro(
    @ColumnInfo(name = "acitivity")
    var activity: String?,
    @ColumnInfo(name = "time")
    var time: Int?)
{

    @PrimaryKey(autoGenerate = true)
    var uid: Int = 0

}