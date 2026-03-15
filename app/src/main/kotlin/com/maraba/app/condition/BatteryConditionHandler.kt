package com.maraba.app.condition

import com.maraba.app.condition.base.ConditionHandler
import com.maraba.app.data.model.BuiltInVariables
import com.maraba.app.data.model.Condition
import com.maraba.app.domain.engine.VariableManager
import timber.log.Timber
import javax.inject.Inject

/**
 * BatteryConditionHandler — checks battery level threshold.
 */
class BatteryConditionHandler @Inject constructor(
    private val variableManager: VariableManager
) : ConditionHandler<Condition.BatteryLevel> {

    override suspend fun evaluate(condition: Condition.BatteryLevel): Boolean {
        val batteryStr = variableManager.get(BuiltInVariables.BATTERY) ?: "0"
        val battery = batteryStr.toIntOrNull() ?: 0
        val meetsThreshold = if (condition.isAboveThreshold) battery >= condition.threshold
                             else battery <= condition.threshold
        val result = if (condition.isNegated) !meetsThreshold else meetsThreshold
        Timber.d("BatteryCondition: $battery ${if (condition.isAboveThreshold) ">=" else "<="} ${condition.threshold} → $result")
        return result
    }
}
