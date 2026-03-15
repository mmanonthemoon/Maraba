package com.maraba.app.ui.screen.log

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.maraba.app.data.db.entity.ExecutionLogEntity
import com.maraba.app.data.repository.LogRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * LogViewModel — manages execution log state.
 */
@HiltViewModel
class LogViewModel @Inject constructor(
    private val logRepository: LogRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(LogUiState())
    val uiState: StateFlow<LogUiState> = _uiState.asStateFlow()

    init {
        logRepository.observeRecent()
            .onEach { logs -> _uiState.update { it.copy(logs = logs, isLoading = false) } }
            .launchIn(viewModelScope)
    }

    fun clearAll() {
        viewModelScope.launch { logRepository.clearAll() }
    }
}

data class LogUiState(
    val logs: List<ExecutionLogEntity> = emptyList(),
    val isLoading: Boolean = true
)
