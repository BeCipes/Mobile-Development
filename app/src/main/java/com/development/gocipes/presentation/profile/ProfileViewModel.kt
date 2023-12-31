package com.development.gocipes.presentation.profile

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
class ProfileViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _isLoggedOut = MutableLiveData(false)
    val isLoggedOut: LiveData<Boolean> get() = _isLoggedOut

    fun getCurrentUser() = authRepository.getUserInfo().asLiveData()
    fun logout() = viewModelScope.launch {
        authRepository.deleteToken()
        authRepository.setLoginStatus(false)
        _isLoggedOut.postValue(true)
    }
}