package com.maraba.app.condition

import com.maraba.app.condition.base.ConditionHandler
import com.maraba.app.data.model.Condition
import com.maraba.app.domain.engine.VariableManager
import com.maraba.app.data.model.BuiltInVariables
import timber.log.Timber
import javax.inject.Inject

/**
 * AppStateConditionHandler — checks if an app is in foreground.
 * TODO: implement via AccessibilityService foreground app tracking
 */
class AppStateConditionHandler @Inject constructor(
    private val variableManager: VariableManager
) : ConditionHandler<Condition.AppForeground> {

    override suspend fun evaluate(condition: Condition.AppForeground): Boolean {
        val lastApp = variableManager.get(BuiltInVariables.LAST_APP) ?: ""
        val isForeground = lastApp == condition.packageName
        val result = if (condition.isNegated) !isForeground else isForeground
        Timber.d("AppStateCondition: $lastApp == ${condition.packageName} → $result")
        return result
    }
}
