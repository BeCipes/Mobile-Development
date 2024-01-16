package com.development.gocipes.core.data.repository

import com.development.gocipes.core.data.local.LocalDataSource
import com.development.gocipes.core.data.remote.RemoteDataSource
import com.development.gocipes.core.data.remote.response.favorite.DeleteFavoriteResponse
import com.development.gocipes.core.data.remote.response.favorite.GetFavoriteItem
import com.development.gocipes.core.data.remote.response.favorite.InsertFavoriteItem
import com.development.gocipes.core.data.remote.response.food.FoodItem
import com.development.gocipes.core.domain.repository.FavoriteRepository
import com.development.gocipes.core.utils.Result
import com.development.gocipes.core.utils.TokenHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.HttpException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FavoriteRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val local: LocalDataSource,
) : FavoriteRepository {

    override fun getFavoriteUser(): Flow<Result<List<GetFavoriteItem>>> = flow {
        emit(Result.Loading())
        try {
            val token = TokenHelper.generateToken(local.getToken())
            val response = remoteDataSource.getFavoriteUser(token)
            val result = response.data
            if (result != null)
                emit(Result.Success(result))
        } catch (e: HttpException) {
            emit(Result.Error(e.message()))
        }
    }.flowOn(Dispatchers.IO)

    override fun getRecipeById(id: Int): Flow<Result<FoodItem>> = flow {
        emit(Result.Loading())
        try {
            val token = TokenHelper.generateToken(local.getToken())
            val response = remoteDataSource.getRecipeById(token, id)
            val result = response.data
            if (result != null) emit(Result.Success(result))
        } catch (e: Exception) {
            emit(Result.Error(e.message))
        }
    }.flowOn(Dispatchers.IO)

    override fun addFavorite(id: Int): Flow<Result<InsertFavoriteItem>> = flow {
        emit(Result.Loading())
        try {
            val token = TokenHelper.generateToken(local.getToken())
            val response = remoteDataSource.addFavorite(token, id)
            val result = response.data
            if (result != null) emit(Result.Success(result))
        } catch (e: Exception) {
            emit(Result.Error(e.message))
        }
    }.flowOn(Dispatchers.IO)

    override fun deleteFavorite(id: Int): Flow<Result<DeleteFavoriteResponse>> = flow {
        emit(Result.Loading())
        try {
            val token = TokenHelper.generateToken(local.getToken())
            val response = remoteDataSource.deleteFavorite(token, id)
            emit(Result.Success(response))
        } catch (e: Exception) {
            emit(Result.Error(e.message))
        }
    }.flowOn(Dispatchers.IO)
}