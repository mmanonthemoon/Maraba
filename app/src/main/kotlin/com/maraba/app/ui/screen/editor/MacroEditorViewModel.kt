package com.maraba.app.ui.screen.editor

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.maraba.app.data.model.Action
import com.maraba.app.data.model.Condition
import com.maraba.app.data.model.Macro
import com.maraba.app.data.model.Trigger
import com.maraba.app.domain.usecase.macro.CreateMacroUseCase
import com.maraba.app.domain.usecase.macro.DeleteMacroUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

/**
 * MacroEditorViewModel — manages macro creation/editing state.
 * TODO: implement load existing macro, validation, save flow
 */
@HiltViewModel
class MacroEditorViewModel @Inject constructor(
    private val createMacroUseCase: CreateMacroUseCase,
    private val deleteMacroUseCase: DeleteMacroUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(MacroEditorUiState())
    val uiState: StateFlow<MacroEditorUiState> = _uiState.asStateFlow()

    fun setName(name: String) = _uiState.update { it.copy(name = name) }

    fun addTrigger(trigger: Trigger) = _uiState.update { it.copy(triggers = it.triggers + trigger) }

    fun removeTrigger(triggerId: String) = _uiState.update {
        it.copy(triggers = it.triggers.filter { t -> t.id != triggerId })
    }

    fun addCondition(condition: Condition) = _uiState.update { it.copy(conditions = it.conditions + condition) }

    fun addAction(action: Action) = _uiState.update { it.copy(actions = it.actions + action) }

    fun save() {
        viewModelScope.launch {
            val state = _uiState.value
            if (state.name.isBlank()) {
                _uiState.update { it.copy(nameError = true) }
                return@launch
            }
            val macro = Macro(
                id = state.macroId ?: "",
                name = state.name,
                description = state.description,
                triggers = state.triggers,
                conditions = state.conditions,
                actions = state.actions,
                createdAt = System.currentTimeMillis()
            )
            createMacroUseCase(macro).fold(
                onSuccess = { _uiState.update { it.copy(saved = true) } },
                onFailure = { error ->
                    Timber.e(error, "Failed to save macro")
                    _uiState.update { it.copy(errorMessage = error.message) }
                }
            )
        }
    }
}

data class MacroEditorUiState(
    val macroId: String? = null,
    val name: String = "",
    val description: String = "",
    val triggers: List<Trigger> = emptyList(),
    val conditions: List<Condition> = emptyList(),
    val actions: List<Action> = emptyList(),
    val nameError: Boolean = false,
    val saved: Boolean = false,
    val errorMessage: String? = null
)
