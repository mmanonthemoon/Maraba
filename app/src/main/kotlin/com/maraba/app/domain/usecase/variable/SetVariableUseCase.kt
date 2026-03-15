package com.maraba.app.domain.usecase.variable

import com.maraba.app.domain.engine.VariableManager
import javax.inject.Inject

/**
 * SetVariableUseCase — sets a user-defined variable.
 */
class SetVariableUseCase @Inject constructor(
    private val variableManager: VariableManager
) {
    suspend operator fun invoke(name: String, value: String) =
        variableManager.setUserVariable(name, value)
}
