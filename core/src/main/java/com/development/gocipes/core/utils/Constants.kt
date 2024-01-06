package com.development.gocipes.core.utils

import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey

object Constants {

    const val USER_PREFERENCES = "presence_preferences"
    val TOKEN_KEY = stringPreferencesKey("mToken")
    val USER_ID = stringPreferencesKey("user_id")
    val EMAIL_KEY = stringPreferencesKey("email")
    val PASSWORD_KEY = stringPreferencesKey("password")
    val LOGIN_STATUS_KEY = booleanPreferencesKey("login")
}
