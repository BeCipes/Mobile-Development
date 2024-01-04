package com.development.gocipes.core.data.repository

import com.development.gocipes.core.data.local.LocalDataSource
import com.development.gocipes.core.data.remote.RemoteDataSource
import com.development.gocipes.core.data.remote.response.category.CategoryItem
import com.development.gocipes.core.data.remote.response.food.FoodItem
import com.development.gocipes.core.data.remote.response.step.StepItem
import com.development.gocipes.core.domain.repository.FoodRepository
import com.development.gocipes.core.utils.Result
import com.development.gocipes.core.utils.TokenHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FoodRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val local: LocalDataSource,
) : FoodRepository {
    override fun getALlFood(): Flow<Result<List<FoodItem>>> = flow {
        emit(Result.Loading())
        try {
            val token = TokenHelper.generateToken(local.getToken())
            val response = remoteDataSource.getAllFood(token)
            val result = response.data
            if (result != null)
                emit(Result.Success(result))
        } catch (e: HttpException) {
            emit(Result.Error(e.message()))
        }
    }.flowOn(Dispatchers.IO)

    override fun getCategoryFood(): Flow<Result<List<CategoryItem>>> = flow {
        emit(Result.Loading())
        try {
            val token = TokenHelper.generateToken(local.getToken())
            val response = remoteDataSource.getCategoryFood(token)
            val result = response.data
            if (result != null)
                emit(Result.Success(result))
        } catch (e: HttpException) {
            emit(Result.Error(e.message()))
        } catch (e: IOException) {
            emit(Result.Error(e.message))
        }
    }.flowOn(Dispatchers.IO)

    override fun getFoodById(id: Int): Flow<Result<CategoryItem>> = flow {
        emit(Result.Loading())
        try {
            val token = TokenHelper.generateToken(local.getToken())
            val response = remoteDataSource.getFoodById(token, id)
            val result = response.data
            if (result != null)
                emit(Result.Success(result))
        } catch (e: Exception) {
            emit(Result.Error(e.message))
        }
    }.flowOn(Dispatchers.IO)

    override fun getStep(id: Int): Flow<Result<List<StepItem>>> = flow {
        emit(Result.Loading())
        try {
            val token = TokenHelper.generateToken(local.getToken())
            val response = remoteDataSource.getStepByRecipeId(token, id)
            val result = response.data
            if (result != null) emit(Result.Success(result))
        } catch (e: Exception) {
            emit(Result.Error(e.message))
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
}