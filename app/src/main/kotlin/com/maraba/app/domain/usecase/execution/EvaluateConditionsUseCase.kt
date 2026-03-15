package com.maraba.app.domain.usecase.execution

import com.maraba.app.data.model.Condition
import com.maraba.app.domain.engine.ConditionEvaluator
import javax.inject.Inject

/**
 * EvaluateConditionsUseCase — evaluates a list of conditions.
 */
class EvaluateConditionsUseCase @Inject constructor(
    private val conditionEvaluator: ConditionEvaluator
) {
    suspend operator fun invoke(conditions: List<Condition>): Boolean =
        conditionEvaluator.evaluateAll(conditions)
}
