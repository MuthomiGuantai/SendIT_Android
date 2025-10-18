package com.example.sendit.utils

import android.content.Context
import android.content.SharedPreferences

class SessionManager(context: Context) {
    private val pref: SharedPreferences = context.getSharedPreferences("SendITPrefs", Context.MODE_PRIVATE)
    private val editor: SharedPreferences.Editor = pref.edit()
    private val KEY_TOKEN = "token"
    private val KEY_IS_LOGGED_IN = "isLoggedIn"

    fun saveToken(token: String) {
        editor.putString(KEY_TOKEN, token)
        editor.putBoolean(KEY_IS_LOGGED_IN, true)
        editor.apply()
    }

    fun getToken(): String? = pref.getString(KEY_TOKEN, null)

    fun isLoggedIn(): Boolean = pref.getBoolean(KEY_IS_LOGGED_IN, false)

    fun logout() {
        editor.clear()
        editor.apply()
    }
}