package com.development.gocipes.presentation.home.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.development.gocipes.core.domain.repository.FoodRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor (
    private val foodRepository: FoodRepository
): ViewModel() {

    fun getAllFood() = foodRepository.getALlFood().asLiveData()

}