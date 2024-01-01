package com.development.gocipes.core.data.remote

import com.development.gocipes.core.data.remote.retrofit.ApiService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteDataSource @Inject constructor(
    private val apiService: ApiService
) {

    suspend fun register(firstName: String, lastName: String, email: String, password: String) = apiService.register(firstName, lastName, email, password)

    suspend fun login(email: String, password: String) = apiService.login(email, password)

    suspend fun forgotPassword(email: String) = apiService.forgotPassword(email)

    suspend fun getUserInfo(token: String) = apiService.getUserInfo(token)

    suspend fun getAllIngridient(token: String) = apiService.getAllIngridient(token)

    suspend fun getAllTechnique(token: String) = apiService.getAllTechnique(token)

    suspend fun getAllArticle(token: String) = apiService.getAllArticle(token)

    suspend fun getAllFood(token: String) = apiService.getAllFood(token)

    suspend fun getCategoryFood(token: String) = apiService.getCategoryFood(token)

    suspend fun getFoodById(token: String, id: Int) = apiService.getFoodById(token, id)

    suspend fun getIngridientById(token: String, id: Int) = apiService.getIngridientById(token, id)

    suspend fun getArticleById(token: String, id: Int) = apiService.getArticleById(token, id)
}