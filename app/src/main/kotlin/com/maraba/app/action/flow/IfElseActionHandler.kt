package com.maraba.app.action.flow

import com.maraba.app.action.base.ActionContext
import com.maraba.app.action.base.ActionHandler
import com.maraba.app.action.base.ActionResult
import com.maraba.app.data.model.Action
import com.maraba.app.domain.engine.ConditionEvaluator
import timber.log.Timber
import javax.inject.Inject

/**
 * IfElseActionHandler — conditional branch execution.
 * TODO: delegate then/else action lists to MacroExecutor
 */
class IfElseActionHandler @Inject constructor(
    private val conditionEvaluator: ConditionEvaluator
) : ActionHandler<Action.IfElse> {

    override suspend fun execute(action: Action.IfElse, context: ActionContext): ActionResult {
        val result = conditionEvaluator.evaluate(action.condition)
        Timber.d("IfElseAction: condition=${result}")
        // TODO: execute action.thenActions or action.elseActions via MacroExecutor
        return ActionResult.Success
    }
}
