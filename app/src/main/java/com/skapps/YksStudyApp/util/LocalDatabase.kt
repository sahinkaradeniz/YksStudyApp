package com.skapps.YksStudyApp.util

import android.content.Context
import android.content.SharedPreferences

class LocalDatabase(context: Context){

    fun setSharedPreference(context: Context, key: String?, time: Long?) {
        val sharedPref: SharedPreferences = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE)
        val edit = sharedPref.edit()
        edit.putLong(key,time!!)
        edit.commit()
    }

    fun getSharedPreference(context: Context, key: String?, defaultValue: Long?): Long {
        return context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE)
            .getLong(key, defaultValue!!)
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