package com.development.gocipes.presentation.home.food

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.development.gocipes.core.domain.repository.FoodRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FoodViewModel @Inject constructor(
    private val foodRepository: FoodRepository
) : ViewModel() {

    fun getCategoryFood() = foodRepository.getCategoryFood().asLiveData()

    fun getStep(id: Int) = foodRepository.getStep(id).asLiveData()

    fun getRecipeById(id: Int) = foodRepository.getRecipeById(id).asLiveData()
}