package com.example.googlenews.util

import android.content.Context
import com.example.googlenews.App

class PreferenceHelper {

        fun setBooleanPreference(key: String, value: Boolean): Boolean {
            val pref = App().getInstance().applicationContext.getSharedPreferences("myPref", Context.MODE_PRIVATE)
            val editor = pref.edit()
            editor.putBoolean(key, value)
            editor.apply()
            return false
        }

        fun getBooleanPreference(key: String): Boolean {
            val pref = App().getInstance().applicationContext.getSharedPreferences("myPref", Context.MODE_PRIVATE)
            val isDarkMode = pref.getBoolean(key, false)
            return isDarkMode
        }

}