package com.development.gocipes.technique.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.development.gocipes.core.domain.repository.TechniqueRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailTechniqueViewModel @Inject constructor(
    private val repository: TechniqueRepository
) : ViewModel() {

    fun getTechniqueById(id: Int) = repository.getTechniqueById(id).asLiveData()
}