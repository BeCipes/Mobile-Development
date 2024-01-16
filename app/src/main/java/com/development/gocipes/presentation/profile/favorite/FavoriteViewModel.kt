package com.development.gocipes.presentation.profile.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.development.gocipes.core.domain.repository.FavoriteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val repository: FavoriteRepository
): ViewModel() {
    fun getFavoriteUser() = repository.getFavoriteUser().asLiveData()

    fun getRecipeById(id: Int) = repository.getRecipeById(id).asLiveData()

    fun addFavorite(id: Int) = repository.addFavorite(id).asLiveData()

    fun deleteFavorite(id: Int) = repository.deleteFavorite(id).asLiveData()
}