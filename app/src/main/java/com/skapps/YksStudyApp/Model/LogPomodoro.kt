package com.skapps.YksStudyApp.Model
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import java.io.Serializable
import java.text.DateFormat


@Entity
data class LogPomodoro(
    @ColumnInfo(name = "acitivity")
    var activity: String?,
    @ColumnInfo(name = "time")
    var time: Int?,
     @ColumnInfo(name = "date")
    var date:String?):Serializable
{
    @PrimaryKey(autoGenerate = true)
    var uid: Int = 0
}