package com.development.gocipes.core.domain.repository

import com.development.gocipes.core.data.remote.response.favorite.GetFavoriteItem
import com.development.gocipes.core.utils.Result
import kotlinx.coroutines.flow.Flow

interface FavoriteRepository {

    fun getFavoriteUser(): Flow<Result<List<GetFavoriteItem>>>
}