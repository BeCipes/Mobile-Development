package com.development.gocipes.core.domain.repository

import com.development.gocipes.core.domain.model.technique.Technique
import com.development.gocipes.core.utils.Result
import kotlinx.coroutines.flow.Flow

interface TechniqueRepository {

    fun getAllTechnique(): Flow<Result<List<Technique>>>

    fun getTechniqueById(id: Int): Flow<Result<Technique>>
}