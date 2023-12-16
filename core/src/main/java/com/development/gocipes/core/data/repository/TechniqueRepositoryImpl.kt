package com.development.gocipes.core.data.repository

import com.development.gocipes.core.data.remote.RemoteDataSource
import com.development.gocipes.core.domain.model.technique.Technique
import com.development.gocipes.core.domain.repository.TechniqueRepository
import com.development.gocipes.core.utils.Result
import com.development.gocipes.core.utils.toDomain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TechniqueRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource
) : TechniqueRepository {
    override fun getAllTechnique(): Flow<Result<List<Technique>>> = flow {
        emit(Result.Loading())
        try {
            val response = remoteDataSource.getAllTechnique()
            val result = response.data.map { it.toDomain() }
            emit(Result.Success(result))
        } catch (e: Exception) {
            emit(Result.Error(e.message))
        }
    }
}