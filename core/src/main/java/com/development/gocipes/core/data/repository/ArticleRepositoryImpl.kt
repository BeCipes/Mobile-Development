package com.development.gocipes.core.data.repository

import com.development.gocipes.core.data.local.LocalDataSource
import com.development.gocipes.core.data.remote.RemoteDataSource
import com.development.gocipes.core.domain.model.article.Article
import com.development.gocipes.core.domain.repository.ArticleRepository
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
class ArticleRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val local: LocalDataSource
) : ArticleRepository {
    override fun getAllArticle(): Flow<Result<List<Article>>> = flow {
        emit(Result.Loading())
        try {
            val token = TokenHelper.generateToken(local.getToken())
            val response = remoteDataSource.getAllArticle(token)
            val result = response.data.map { it.toDomain() }
            emit(Result.Success(result))
        } catch (e: Exception) {
            emit(Result.Error(e.message))
        }
    }.flowOn(Dispatchers.IO)
}