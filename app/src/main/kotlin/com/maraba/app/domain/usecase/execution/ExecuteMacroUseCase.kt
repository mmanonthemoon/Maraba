package com.maraba.app.domain.usecase.execution

import com.maraba.app.data.model.Macro
import com.maraba.app.domain.engine.ConditionEvaluator
import com.maraba.app.domain.engine.MacroExecutor
import com.maraba.app.trigger.base.TriggerEvent
import timber.log.Timber
import javax.inject.Inject

/**
 * ExecuteMacroUseCase — orchestrates condition evaluation + action execution.
 * TODO: add execution logging
 */
class ExecuteMacroUseCase @Inject constructor(
    private val conditionEvaluator: ConditionEvaluator,
    private val macroExecutor: MacroExecutor
) {
    suspend operator fun invoke(macro: Macro, triggerEvent: TriggerEvent) {
        if (!macro.isEnabled) {
            Timber.d("Macro ${macro.name} is disabled, skipping")
            return
        }
        val conditionsMet = conditionEvaluator.evaluateAll(macro.conditions)
        if (!conditionsMet) {
            Timber.d("Macro ${macro.name} conditions not met, skipping")
            return
        }
        Timber.d("Executing macro: ${macro.name}")
        macroExecutor.execute(macro, triggerEvent)
    }
}
