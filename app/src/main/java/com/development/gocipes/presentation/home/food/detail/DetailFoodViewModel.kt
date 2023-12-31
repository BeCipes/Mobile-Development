package com.development.gocipes.presentation.home.food.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.development.gocipes.core.domain.repository.FoodRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailFoodViewModel @Inject constructor(
    private val foodRepository: FoodRepository
) : ViewModel() {

    fun getFoodById(id: Int) = foodRepository.getFoodById(id).asLiveData()
}