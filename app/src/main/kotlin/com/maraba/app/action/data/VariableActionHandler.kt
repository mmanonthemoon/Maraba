package com.maraba.app.action.data

import com.maraba.app.action.base.ActionContext
import com.maraba.app.action.base.ActionHandler
import com.maraba.app.action.base.ActionResult
import com.maraba.app.data.model.Action
import com.maraba.app.domain.engine.VariableManager
import timber.log.Timber
import javax.inject.Inject

/**
 * VariableActionHandler — sets a user-defined variable.
 */
class VariableActionHandler @Inject constructor(
    private val variableManager: VariableManager
) : ActionHandler<Action.SetVariable> {

    override suspend fun execute(action: Action.SetVariable, context: ActionContext): ActionResult {
        val resolvedValue = context.resolveVariable(action.value)
        variableManager.setUserVariable(action.variableName, resolvedValue)
        Timber.d("SetVariableAction: ${action.variableName} = $resolvedValue")
        return ActionResult.Success
    }
}
