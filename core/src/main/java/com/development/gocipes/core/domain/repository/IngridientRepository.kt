package com.development.gocipes.core.domain.repository

import com.development.gocipes.core.data.remote.response.analysis.IngridientItem
import kotlinx.coroutines.flow.Flow
import com.development.gocipes.core.utils.Result

interface IngridientRepository {
    fun getAllIngridient():Flow<Result<List<IngridientItem>>>
}