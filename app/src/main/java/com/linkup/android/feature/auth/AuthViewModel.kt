package com.linkup.android.feature.auth

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.linkup.android.data.datastore.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.launch
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    var isLoggedIn by mutableStateOf(false)
        private set

    init {
        viewModelScope.launch {
            userRepository.getAccessTokenSnapshot()
            isLoggedIn = userRepository.getCachedAccessToken() != null
        }
    }

    fun logout() {
        viewModelScope.launch {
            userRepository.clearTokens()
            isLoggedIn = false
        }
    }
}