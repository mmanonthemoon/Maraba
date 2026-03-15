package com.maraba.app.condition

import com.maraba.app.condition.base.ConditionHandler
import com.maraba.app.data.model.Condition
import com.maraba.app.domain.engine.VariableManager
import timber.log.Timber
import javax.inject.Inject

/**
 * VariableConditionHandler — checks variable value against criteria.
 * Handles VariableEquals condition; extend for other variable conditions.
 */
class VariableConditionHandler @Inject constructor(
    private val variableManager: VariableManager
) : ConditionHandler<Condition.VariableEquals> {

    override suspend fun evaluate(condition: Condition.VariableEquals): Boolean {
        val value = variableManager.get(condition.variableName) ?: ""
        val isEqual = value == condition.expectedValue
        val result = if (condition.isNegated) !isEqual else isEqual
        Timber.d("VariableCondition: ${condition.variableName} == ${condition.expectedValue} → $result")
        return result
    }
}
