package com.development.gocipes.core.data.repository

import com.development.gocipes.core.data.local.LocalDataSource
import com.development.gocipes.core.data.remote.RemoteDataSource
import com.development.gocipes.core.data.remote.response.category.CategoryItem
import com.development.gocipes.core.domain.repository.CategoryRepository
import com.development.gocipes.core.utils.Result
import com.development.gocipes.core.utils.TokenHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CategoryRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
) : CategoryRepository {
    override fun getRecipesByCategory(idCategory: Int): Flow<Result<List<CategoryItem>>> = flow {
        emit(Result.Loading())
        try {
            val token = TokenHelper.generateToken(localDataSource.getToken())
            val response = remoteDataSource.getRecipesByCategory(token, idCategory)
            val result = response.data
            emit(Result.Success(result))
        } catch (e: Exception) {
            emit(Result.Error(e.message))
        }
    }.flowOn(Dispatchers.IO)
}