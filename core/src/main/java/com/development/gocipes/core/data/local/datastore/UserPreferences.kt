package com.development.gocipes.core.data.local.datastore

import kotlinx.coroutines.flow.Flow

interface UserPreferences {

    fun isLoggedIn(): Flow<Boolean>

    fun getToken(): Flow<String?>

    fun getUserId(): Flow<String>

    fun getEmail(): Flow<String>

    fun getPassword(): Flow<String>

    suspend fun saveLogInStatus(isLogin: Boolean)

    suspend fun saveToken(token: String)

    suspend fun saveUserId(userId: String)

    suspend fun saveEmail(email: String)

    suspend fun savePassword(password: String)

    suspend fun deleteToken()
}