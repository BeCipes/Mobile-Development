package com.development.gocipes.core.domain.repository

import com.development.gocipes.core.data.remote.response.category.CategoryItem
import com.development.gocipes.core.data.remote.response.food.FoodItem
import com.development.gocipes.core.utils.Result
import kotlinx.coroutines.flow.Flow

interface FoodRepository {
    fun getALlFood(): Flow<Result<List<FoodItem>>>

    fun getCategoryFood(): Flow<Result<List<CategoryItem>>>

    fun getFoodById(id: Int): Flow<Result<CategoryItem>>
}