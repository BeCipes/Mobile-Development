package com.development.gocipes.core.data.repository

import com.development.gocipes.core.data.local.LocalDataSource
import com.development.gocipes.core.data.remote.RemoteDataSource
import com.development.gocipes.core.data.remote.response.analysis.IngridientItem
import com.development.gocipes.core.data.remote.response.detail.DetailIngridientItems
import com.development.gocipes.core.domain.repository.IngridientRepository
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
class IngridientRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val local: LocalDataSource,
) : IngridientRepository {
    override fun getAllIngridient(): Flow<Result<List<IngridientItem>>> = flow{
        emit(Result.Loading())
        try {
            val token = TokenHelper.generateToken(local.getToken())
            val response = remoteDataSource.getAllIngridient(token)
            val result = response.data
            if (result != null)
                emit(Result.Success(result))
        } catch (e: HttpException) {
            emit(Result.Error(e.message()))
        }
    }.flowOn(Dispatchers.IO)

    override fun getIngridientById(id: Int): Flow<Result<DetailIngridientItems>> = flow{
        emit(Result.Loading())
        try {
            val token = TokenHelper.generateToken(local.getToken())
            val response = remoteDataSource.getIngridientById(token, id)
            val result = response.data
            if (result != null)
                emit(Result.Success(result))
        } catch (e: HttpException) {
            emit(Result.Error(e.message()))
        }
    }.flowOn(Dispatchers.IO)
}