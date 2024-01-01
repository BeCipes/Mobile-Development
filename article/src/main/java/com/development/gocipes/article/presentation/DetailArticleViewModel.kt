package com.development.gocipes.article.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.development.gocipes.core.domain.repository.ArticleRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailArticleViewModel @Inject constructor(
    private val articleRepository: ArticleRepository
): ViewModel() {

    fun getArticleById(id: Int) = articleRepository.getArticleById(id).asLiveData()
}