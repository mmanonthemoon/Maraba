package com.maraba.app.domain.usecase.variable

import com.maraba.app.domain.engine.VariableManager
import javax.inject.Inject

/**
 * GetVariableUseCase — retrieves the current value of a variable.
 */
class GetVariableUseCase @Inject constructor(
    private val variableManager: VariableManager
) {
    operator fun invoke(name: String): String? = variableManager.get(name)
}
