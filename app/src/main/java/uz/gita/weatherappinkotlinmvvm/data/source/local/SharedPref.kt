package uz.gita.weatherappinkotlinmvvm.data.source.local

import android.content.Context
import android.content.SharedPreferences

class SharedPref private constructor() {

    companion object {
        private lateinit var sharedPref: SharedPref

        private const val SHARED_PREF = "shared_pref"
        private const val LOCATION = "location"

        private lateinit var pref: SharedPreferences
        private lateinit var editor: SharedPreferences.Editor

        fun getInstance(context: Context): SharedPref {
            pref = context.getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE)
            editor = pref.edit()

            if (!(::sharedPref.isInitialized)) {
                sharedPref = SharedPref()
            }
            return sharedPref
        }
    }

    var location: String
        set(value) = editor.putString(LOCATION, value).apply()
        get() = pref.getString(LOCATION, "").toString()
}