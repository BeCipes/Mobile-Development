package com.development.gocipes.core.data.remote.retrofit

import com.development.gocipes.core.data.remote.response.analysis.IngridientResponse
import com.development.gocipes.core.data.remote.response.article.ArticleResponse
import com.development.gocipes.core.data.remote.response.auth.ForgotPasswordResponse
import com.development.gocipes.core.data.remote.response.auth.GetUserResponse
import com.development.gocipes.core.data.remote.response.auth.LoginResponse
import com.development.gocipes.core.data.remote.response.auth.RegisterResponse
import com.development.gocipes.core.data.remote.response.category.CategoryResponse
import com.development.gocipes.core.data.remote.response.detail.DetailArticleResponse
import com.development.gocipes.core.data.remote.response.detail.DetailCategoryResponse
import com.development.gocipes.core.data.remote.response.detail.DetailIngridientResponse
import com.development.gocipes.core.data.remote.response.food.FoodResponse
import com.development.gocipes.core.data.remote.response.technique.TechniqueResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {

    @FormUrlEncoded
    @POST("auth/register")
    suspend fun register(
        @Field("first_name") firstName: String,
        @Field("last_name") lastName: String,
        @Field("email") email: String,
        @Field("password") password: String
    ): RegisterResponse

    @FormUrlEncoded
    @POST("auth/login")
    suspend fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): LoginResponse

    @FormUrlEncoded
    @POST("auth/forgot-password")
    suspend fun forgotPassword(
        @Field("email") email: String,
    ): ForgotPasswordResponse

    @GET("user/get-info")
    suspend fun getUserInfo(
        @Header("Authorization") token: String
    ): GetUserResponse

    @GET("data/bahan")
    suspend fun getAllIngridient(
        @Header("Authorization") token: String
    ): IngridientResponse

    @GET("data/teknik")
    suspend fun getAllTechnique(
        @Header("Authorization") token: String
    ): TechniqueResponse

    @GET("data/artikel")
    suspend fun getAllArticle(
        @Header("Authorization") token: String
    ): ArticleResponse

    @GET("data/resep")
    suspend fun getAllFood(
        @Header("Authorization") token: String
    ): FoodResponse

    @GET("data/kategori-resep")
    suspend fun getCategoryFood(
        @Header("Authorization") token: String
    ): CategoryResponse

    @GET("data/kategori-resep/{id}")
    suspend fun getFoodById(
        @Header("Authorization") token: String,
        @Path("id") id: Int
    ): DetailCategoryResponse

    @GET("data/bahan/{bahanId}")
    suspend fun getIngridientById(
        @Header("Authorization") token: String,
        @Path("bahanId") id: Int
    ): DetailIngridientResponse

    @GET("data/artikel/{id}")
    suspend fun getArticleById(
        @Header("Authorization") token: String,
        @Path("id") id: Int
    ): DetailArticleResponse
}