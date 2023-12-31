package com.development.gocipes.auth.presentation.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.development.gocipes.core.domain.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    fun register(firstName: String, lastName: String, email: String, password: String) =
        authRepository.register(firstName, lastName, email, password).asLiveData()
}