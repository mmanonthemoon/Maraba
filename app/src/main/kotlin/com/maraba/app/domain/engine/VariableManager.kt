package com.maraba.app.domain.engine

import com.maraba.app.data.model.BuiltInVariables
import com.maraba.app.data.model.MarabaVariable
import com.maraba.app.data.repository.VariableRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

/**
 * VariableManager — manages built-in and user-defined variables (engine-developer ADR).
 *
 * Rules:
 * - Built-in variables updated via StateFlow (real-time)
 * - %VARIABLE interpolation runs on every action parameter
 * - Variable names: uppercase + underscore, % prefix
 *
 * TODO: implement updateBuiltIns(), observe system state changes
 */
@Singleton
class VariableManager @Inject constructor(
    private val variableRepository: VariableRepository
) {

    private val _variables = MutableStateFlow<Map<String, String>>(emptyMap())
    val variables: StateFlow<Map<String, String>> = _variables.asStateFlow()

    /** Resolve %VARIABLE placeholders in a string */
    suspend fun interpolate(text: String): String {
        // TODO: implement full interpolation with regex
        var result = text
        val current = _variables.value
        current.forEach { (name, value) ->
            result = result.replace(name, value)
        }
        return result
    }

    /** Update a built-in variable value */
    suspend fun updateBuiltIn(name: String, value: String) {
        Timber.d("BuiltIn variable updated: $name = $value")
        _variables.value = _variables.value.toMutableMap().apply { put(name, value) }
        variableRepository.setValue(name, value)
    }

    /** Set a user-defined variable */
    suspend fun setUserVariable(name: String, value: String) {
        Timber.d("User variable set: $name = $value")
        _variables.value = _variables.value.toMutableMap().apply { put(name, value) }
        variableRepository.setValue(name, value)
    }

    /** Get current value of a variable */
    fun get(name: String): String? = _variables.value[name]

    /**
     * Initialize from database and start observing system state for built-in updates.
     * Called from MarabaForegroundService.onCreate()
     * TODO: implement system state observation (battery, wifi, etc.)
     */
    suspend fun initialize() {
        // TODO: load all variables from repository, start system state observers
        Timber.d("VariableManager initialized")
    }
}
