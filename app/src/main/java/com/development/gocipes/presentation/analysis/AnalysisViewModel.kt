package com.development.gocipes.presentation.analysis

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.development.gocipes.core.domain.repository.IngridientRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AnalysisViewModel @Inject constructor(
    private val repository: IngridientRepository
): ViewModel(){
    fun getAllIngridient()= repository.getAllIngridient().asLiveData()
}