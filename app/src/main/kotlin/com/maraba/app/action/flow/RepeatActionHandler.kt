package com.maraba.app.action.flow

import com.maraba.app.action.base.ActionContext
import com.maraba.app.action.base.ActionHandler
import com.maraba.app.action.base.ActionResult
import com.maraba.app.data.model.Action
import kotlinx.coroutines.delay
import timber.log.Timber
import javax.inject.Inject

/**
 * RepeatActionHandler — repeats a set of actions N times.
 * TODO: delegate actions to MacroExecutor
 */
class RepeatActionHandler @Inject constructor() : ActionHandler<Action.Repeat> {

    override suspend fun execute(action: Action.Repeat, context: ActionContext): ActionResult {
        Timber.d("RepeatAction: count=${action.count}")
        repeat(action.count) { iteration ->
            // TODO: execute action.actions via MacroExecutor
            if (action.delayBetweenMs > 0 && iteration < action.count - 1) {
                delay(action.delayBetweenMs)
            }
        }
        return ActionResult.Success
    }
}
