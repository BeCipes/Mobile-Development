package com.development.gocipes.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.development.gocipes.core.domain.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val authRepository: AuthRepository,
) : ViewModel() {

    fun getUserInfo() = authRepository.getUserInfo().asLiveData()
}