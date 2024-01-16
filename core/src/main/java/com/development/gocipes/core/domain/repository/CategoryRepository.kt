package com.development.gocipes.core.domain.repository

import com.development.gocipes.core.data.remote.response.category.CategoryItem
import com.development.gocipes.core.utils.Result
import kotlinx.coroutines.flow.Flow

interface CategoryRepository {
    fun getRecipesByCategory(idCategory: Int): Flow<Result<List<CategoryItem>>>
}