import android.content.Context
import android.content.SharedPreferences

class LocalDataManager {
    fun setSharedPreference(context: Context, key: String?, value: String?) {
        val sharedPref: SharedPreferences =
            context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE)
        val edit = sharedPref.edit()
        edit.putString(key, value)
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