package com.linkup.android.feature.rank

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.linkup.android.network.rank.RankResponse
import com.linkup.android.network.rank.RankService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


data class RankState(
    val ranking: List<RankResponse> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)

@HiltViewModel
class RankViewModel @Inject constructor(
    private val rankService: RankService
) : ViewModel() {

    private val _state = mutableStateOf(RankState())
    val state: State<RankState> = _state

    fun getRank() {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)
            try {
                val response = rankService.getRank()
                if (response.isSuccessful) {
                    _state.value = _state.value.copy(
                        ranking = response.body()?.data ?: emptyList(),
                        isLoading = false
                    )
                } else {
                    _state.value = _state.value.copy(
                        error = "Failed to fetch ranking",
                        isLoading = false
                    )
                }
            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    error = e.message ?: "An unexpected error occurred",
                    isLoading = false
                )
            }
        }
    }
}
