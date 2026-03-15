package com.maraba.app.domain.engine

import com.maraba.app.data.model.Condition
import javax.inject.Inject
import javax.inject.Singleton

/**
 * ConditionEvaluator — evaluates all conditions for a macro (ADR-004).
 * All conditions must return true for actions to run.
 *
 * TODO: dispatch each Condition subtype to its ConditionHandler
 */
@Singleton
class ConditionEvaluator @Inject constructor() {

    /**
     * Evaluate all conditions. Returns true only if all pass.
     * Empty list → always passes.
     */
    suspend fun evaluateAll(conditions: List<Condition>): Boolean {
        if (conditions.isEmpty()) return true
        // TODO: dispatch each condition to its handler
        return conditions.all { evaluate(it) }
    }

    suspend fun evaluate(condition: Condition): Boolean {
        // TODO: implement condition dispatch
        return when (condition) {
            is Condition.TimeRange -> TODO("TimeConditionHandler.evaluate()")
            is Condition.DayOfWeek -> TODO("DayOfWeekConditionHandler.evaluate()")
            is Condition.AppForeground -> TODO("AppStateConditionHandler.evaluate()")
            is Condition.AppInstalled -> TODO("AppStateConditionHandler.evaluate()")
            is Condition.WifiConnected -> TODO("WifiConditionHandler.evaluate()")
            is Condition.BluetoothConnected -> TODO("BluetoothConditionHandler.evaluate()")
            is Condition.BatteryLevel -> TODO("BatteryConditionHandler.evaluate()")
            is Condition.IsCharging -> TODO("ChargingConditionHandler.evaluate()")
            is Condition.InsideArea -> TODO("LocationConditionHandler.evaluate()")
            is Condition.VariableEquals -> TODO("VariableConditionHandler.evaluate()")
            is Condition.VariableContains -> TODO("VariableConditionHandler.evaluate()")
            is Condition.VariableGreaterThan -> TODO("VariableConditionHandler.evaluate()")
            is Condition.VariableLessThan -> TODO("VariableConditionHandler.evaluate()")
            is Condition.HasPendingNotification -> TODO("NotificationConditionHandler.evaluate()")
            is Condition.ScreenOn -> TODO("ScreenConditionHandler.evaluate()")
            is Condition.RingerMode -> TODO("RingerConditionHandler.evaluate()")
        }
    }
}
