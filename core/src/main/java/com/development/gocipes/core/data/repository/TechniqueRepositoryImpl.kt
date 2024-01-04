package com.development.gocipes.core.data.repository

import com.development.gocipes.core.data.local.LocalDataSource
import com.development.gocipes.core.data.remote.RemoteDataSource
import com.development.gocipes.core.domain.model.technique.Technique
import com.development.gocipes.core.domain.repository.TechniqueRepository
import com.development.gocipes.core.utils.Result
import com.development.gocipes.core.utils.TokenHelper
import com.development.gocipes.core.utils.toDomain
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TechniqueRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val local: LocalDataSource,
) : TechniqueRepository {
    override fun getAllTechnique(): Flow<Result<List<Technique>>> = flow {
        emit(Result.Loading())
        try {
            val token = TokenHelper.generateToken(local.getToken())
            val response = remoteDataSource.getAllTechnique(token)
            val result = response.data.map { it.toDomain() }
            emit(Result.Success(result))
        } catch (e: Exception) {
            emit(Result.Error(e.message))
        }
    }.flowOn(Dispatchers.IO)

    override fun getTechniqueById(id: Int): Flow<Result<Technique>> = flow {
        emit(Result.Loading())
        try {
            val token = TokenHelper.generateToken(local.getToken())
            val response = remoteDataSource.getTechniqueById(token, id)
            val result = response.data?.toDomain()
            if (result != null) emit(Result.Success(result))
        } catch (e: Exception) {
            emit(Result.Error(e.message))
        }
    }.flowOn(Dispatchers.IO)
}