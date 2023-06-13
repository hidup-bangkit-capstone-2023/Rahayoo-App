package com.bangkit.rahayoo.data.local

import android.content.Context
import android.content.SharedPreferences

class Preferences(context: Context) {

    companion object {
        private const val PREFERENCES_KEY = "rahayoo_prefs"
        private const val USER_ID_KEY = "user_id_key"
    }

    private val prefs: SharedPreferences = context.getSharedPreferences(PREFERENCES_KEY, Context.MODE_PRIVATE)

    fun saveUserId(userId: String) {
        prefs.edit().putString(USER_ID_KEY, userId).apply()
    }

    fun getUserId(): String? = prefs.getString(USER_ID_KEY, null)

    fun clearUserData() {
        prefs.edit().clear().apply()
    }
}