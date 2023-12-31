package com.development.gocipes.core.domain.repository

import com.development.gocipes.core.data.remote.response.auth.ForgotPasswordResponse
import com.development.gocipes.core.data.remote.response.auth.LoginItem
import com.development.gocipes.core.data.remote.response.auth.RegisterItem
import com.development.gocipes.core.data.remote.response.auth.UserResult
import com.development.gocipes.core.utils.Result
import kotlinx.coroutines.flow.Flow

interface AuthRepository {

    fun register(
        firstName: String,
        lastName: String,
        email: String,
        password: String
    ): Flow<Result<RegisterItem>>

    fun login(email: String, password: String): Flow<Result<LoginItem>>

    fun forgotPassword(
        email: String
    ): Flow<Result<ForgotPasswordResponse>>

    fun getUserInfo(): Flow<Result<UserResult>>

    fun isLoggedIn(): Flow<Boolean>

    fun getToken(): Flow<String?>

    fun getEmail(): Flow<String>

    fun getPassword(): Flow<String>

    suspend fun setLoginStatus(isLogin: Boolean)

    suspend fun saveToken(token: String)

    suspend fun saveEmail(email: String)

    suspend fun savePassword(password: String)

    suspend fun deleteToken()
}