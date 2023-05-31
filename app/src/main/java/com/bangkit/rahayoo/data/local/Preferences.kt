package com.bangkit.rahayoo.data.local

import android.content.Context
import android.content.SharedPreferences

class Preferences(context: Context) {

    companion object {
        private const val PREFERENCES_KEY = "rahayoo_prefs"
        private const val USER_REFRESH_TOKEN_KEY = "user_refresh_token_key"
    }

    private val prefs: SharedPreferences = context.getSharedPreferences(PREFERENCES_KEY, Context.MODE_PRIVATE)

    fun saveUserRefreshToken(refreshToken: String) {
        prefs.edit().putString(USER_REFRESH_TOKEN_KEY, refreshToken).apply()
    }

    fun getUserRefreshToken(): String? = prefs.getString(USER_REFRESH_TOKEN_KEY, null)

    fun clearUserData() {
        prefs.edit().clear().apply()
    }
}