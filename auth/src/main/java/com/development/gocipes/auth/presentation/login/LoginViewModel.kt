package com.development.gocipes.auth.presentation.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.development.gocipes.core.domain.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository,
) : ViewModel() {

    private val _isCredentialSaved = MutableLiveData(false)
    val isCredentialSaved: LiveData<Boolean> get() = _isCredentialSaved

    fun login(email: String, password: String) = authRepository.login(email, password).asLiveData()

    fun getEmail() = authRepository.getEmail().asLiveData()

    fun getPassword() = authRepository.getPassword().asLiveData()

    fun saveCredentials(isLogin: Boolean, token: String) = viewModelScope.launch {
        authRepository.saveToken(token)
        authRepository.setLoginStatus(isLogin)
        _isCredentialSaved.value = true
    }
}