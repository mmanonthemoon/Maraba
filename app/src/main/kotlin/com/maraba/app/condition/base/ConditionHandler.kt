package com.maraba.app.condition.base

import com.maraba.app.data.model.Condition

/**
 * ConditionHandler interface — evaluate(): Boolean
 * Returns true if the condition is satisfied (considering isNegated).
 */
interface ConditionHandler<T : Condition> {

    /**
     * Evaluate whether the condition is met.
     * @param condition the condition to evaluate
     * @return true if condition passes (with negation applied)
     */
    suspend fun evaluate(condition: T): Boolean
}
