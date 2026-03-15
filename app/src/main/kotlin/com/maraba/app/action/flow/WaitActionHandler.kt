package com.maraba.app.action.flow

import com.maraba.app.action.base.ActionContext
import com.maraba.app.action.base.ActionHandler
import com.maraba.app.action.base.ActionResult
import com.maraba.app.data.model.Action
import kotlinx.coroutines.delay
import timber.log.Timber
import javax.inject.Inject

/**
 * WaitActionHandler — suspends execution for a specified duration.
 * Uses coroutine delay() — Thread.sleep() is forbidden.
 */
class WaitActionHandler @Inject constructor() : ActionHandler<Action.Wait> {

    override suspend fun execute(action: Action.Wait, context: ActionContext): ActionResult {
        Timber.d("WaitAction: ${action.durationMs}ms")
        delay(action.durationMs.coerceAtLeast(100L))
        return ActionResult.Success
    }
}
