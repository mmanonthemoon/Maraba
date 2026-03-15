package com.maraba.app.ui.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.maraba.app.data.model.Macro
import com.maraba.app.domain.usecase.macro.GetMacrosUseCase
import com.maraba.app.domain.usecase.macro.ToggleMacroUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

/**
 * HomeViewModel — manages macro list state (ui-developer ADR).
 * Single StateFlow<HomeUiState>, side effects via Channel.
 */
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getMacrosUseCase: GetMacrosUseCase,
    private val toggleMacroUseCase: ToggleMacroUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        getMacrosUseCase()
            .onEach { macros -> _uiState.update { it.copy(macros = macros, isLoading = false) } }
            .launchIn(viewModelScope)
    }

    fun toggleMacro(macroId: String, enabled: Boolean) {
        viewModelScope.launch {
            toggleMacroUseCase(macroId, enabled).onFailure { error ->
                Timber.e(error, "Failed to toggle macro $macroId")
                _uiState.update { it.copy(errorMessage = error.message) }
            }
        }
    }

    fun clearError() {
        _uiState.update { it.copy(errorMessage = null) }
    }
}

data class HomeUiState(
    val macros: List<Macro> = emptyList(),
    val isLoading: Boolean = true,
    val errorMessage: String? = null
)
