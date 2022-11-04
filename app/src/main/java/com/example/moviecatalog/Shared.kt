package com.example.moviecatalog

import android.content.Context
import android.content.SharedPreferences

object Shared {
    private const val PREFERENCE_NAME: String = "shared"

    fun init(context: Context) {
        preferences = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)
    }

    lateinit var preferences: SharedPreferences

    fun getString(key: String): String? {
        return preferences.getString(key, "")
    }

    fun setString(key: String, value: String) {
        val editor = preferences.edit()
        editor.putString(key, value)
        editor.apply()
    }
}