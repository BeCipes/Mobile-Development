package com.development.gocipes.core.domain.repository

import com.development.gocipes.core.data.remote.response.analysis.IngridientItem
import com.development.gocipes.core.utils.Result
import kotlinx.coroutines.flow.Flow

interface IngridientRepository {
    fun getAllIngridient(): Flow<Result<List<IngridientItem>>>

    fun getIngridientById(id: Int): Flow<Result<IngridientItem>>
}