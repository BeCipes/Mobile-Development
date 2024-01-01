package com.development.gocipes.core.domain.repository

import com.development.gocipes.core.domain.model.article.Article
import com.development.gocipes.core.utils.Result
import kotlinx.coroutines.flow.Flow

interface ArticleRepository {

    fun getAllArticle(): Flow<Result<List<Article>>>

    fun getArticleById(id: Int): Flow<Result<Article>>
}