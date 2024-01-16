package com.development.gocipes.core.domain.repository

import com.development.gocipes.core.data.remote.response.favorite.DeleteFavoriteResponse
import com.development.gocipes.core.data.remote.response.favorite.GetFavoriteItem
import com.development.gocipes.core.data.remote.response.favorite.InsertFavoriteItem
import com.development.gocipes.core.data.remote.response.food.FoodItem
import com.development.gocipes.core.utils.Result
import kotlinx.coroutines.flow.Flow

interface FavoriteRepository {

    fun getFavoriteUser(): Flow<Result<List<GetFavoriteItem>>>

    fun getRecipeById(id: Int): Flow<Result<FoodItem>>

    fun addFavorite(id: Int): Flow<Result<InsertFavoriteItem>>

    fun deleteFavorite(id: Int): Flow<Result<DeleteFavoriteResponse>>
}