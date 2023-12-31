package com.development.gocipes.core.data.local

import com.development.gocipes.core.data.local.datastore.UserPreferences
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalDataSource @Inject constructor(
    private val userPreferences: UserPreferences,
) {
    fun isLoggedIn() = userPreferences.isLoggedIn()

    fun getToken() = userPreferences.getToken()

    fun getEmail() = userPreferences.getEmail()

    fun getPassword() = userPreferences.getPassword()

    suspend fun saveLoginStatus(isLogin: Boolean) = userPreferences.saveLogInStatus(isLogin)

    suspend fun saveToken(token: String) = userPreferences.saveToken(token)

    suspend fun saveEmail(email: String) = userPreferences.saveEmail(email)

    suspend fun savePassword(password: String) = userPreferences.savePassword(password)

    suspend fun deleteToken() = userPreferences.deleteToken()
}