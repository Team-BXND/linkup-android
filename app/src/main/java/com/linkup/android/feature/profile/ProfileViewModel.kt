package com.linkup.android.feature.profile

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.linkup.android.network.profile.MyAnswer
import com.linkup.android.network.profile.MyQuestion
import com.linkup.android.network.profile.ProfileService
import com.linkup.android.network.profile.UserProfile
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ProfileState(
    val userProfile: UserProfile? = null,
    val myAnswers: List<MyAnswer> = emptyList(),
    val myQuestions: List<MyQuestion> = emptyList(),
    val answersPage: Int = 1,
    val questionsPage: Int = 1,
    val hasNextAnswers: Boolean = true,
    val hasNextQuestions: Boolean = true,
    val isLoading: Boolean = false,
    val error: String? = null
)

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val profileService: ProfileService
) : ViewModel() {

    private val _state = mutableStateOf(ProfileState())
    val state: State<ProfileState> = _state

    fun getProfile() {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true, error = null)
            try {
                val response = profileService.getProfile()
                if (response.isSuccessful) {
                    _state.value = _state.value.copy(
                        userProfile = response.body()?.data,
                        isLoading = false
                    )
                } else {
                    _state.value = _state.value.copy(error = "Failed to fetch profile", isLoading = false)
                }
            } catch (e: Exception) {
                _state.value = _state.value.copy(error = e.message ?: "An unexpected error occurred", isLoading = false)
            }
        }
    }

    fun getMyAnswers(initialFetch: Boolean = false) {
        if (!state.value.hasNextAnswers && !initialFetch) return

        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true, error = null)
            val pageToFetch = if (initialFetch) 1 else state.value.answersPage

            try {
                val response = profileService.getMyAnswers(pageToFetch)
                if (response.isSuccessful) {
                    val newAnswers = response.body()?.data ?: emptyList()
                    val meta = response.body()?.meta
                    _state.value = _state.value.copy(
                        myAnswers = if (initialFetch) newAnswers else state.value.myAnswers + newAnswers,
                        answersPage = pageToFetch + 1,
                        hasNextAnswers = meta?.hasNext ?: false,
                        isLoading = false
                    )
                } else {
                    _state.value = _state.value.copy(error = "Failed to fetch my answers", isLoading = false)
                }
            } catch (e: Exception) {
                _state.value = _state.value.copy(error = e.message ?: "An unexpected error occurred", isLoading = false)
            }
        }
    }

    fun getMyQuestions(initialFetch: Boolean = false) {
        if (!state.value.hasNextQuestions && !initialFetch) return

        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true, error = null)
            val pageToFetch = if (initialFetch) 1 else state.value.questionsPage

            try {
                val response = profileService.getMyQuestions(pageToFetch)
                if (response.isSuccessful) {
                    val newQuestions = response.body()?.data ?: emptyList()
                    val meta = response.body()?.meta
                    _state.value = _state.value.copy(
                        myQuestions = if (initialFetch) newQuestions else state.value.myQuestions + newQuestions,
                        questionsPage = pageToFetch + 1,
                        hasNextQuestions = meta?.hasNext ?: false,
                        isLoading = false
                    )
                } else {
                    _state.value = _state.value.copy(error = "Failed to fetch my questions", isLoading = false)
                }
            } catch (e: Exception) {
                _state.value = _state.value.copy(error = e.message ?: "An unexpected error occurred", isLoading = false)
            }
        }
    }
}