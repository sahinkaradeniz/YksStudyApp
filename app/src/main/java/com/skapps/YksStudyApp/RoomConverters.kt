package com.skapps.YksStudyApp

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.*

class RoomConverters {
    //for date and time convertions
    @TypeConverter
    fun calendarToDateStamp(calendar: Calendar): Long = calendar.timeInMillis

    @TypeConverter
    fun dateStampToCalendar(value: Long): Calendar =
        Calendar.getInstance().apply { timeInMillis = value }


    /*  for converting List<Double?>?  you can do same with other data type*/
    @TypeConverter
    fun saveDoubleList(listOfString: List<Double>): String? {
        return Gson().toJson(listOfString)
    }

    @TypeConverter
    fun getDoubleList(listOfString: List<Double>): List<Double> {
        return Gson().fromJson(
            listOfString.toString(),
            object : TypeToken<List<Double?>?>() {}.type
        )
    }


}