package com.maraba.app.action.ui

import com.maraba.app.action.base.ActionContext
import com.maraba.app.action.base.ActionHandler
import com.maraba.app.action.base.ActionResult
import com.maraba.app.action.base.FailureReason
import com.maraba.app.data.model.Action
import com.maraba.app.service.MarabaAccessibilityService
import timber.log.Timber
import javax.inject.Inject

/**
 * KeyEventActionHandler — sends key events (back, home, volume, etc.).
 * TODO: map KeyEventCode to GLOBAL_ACTION or performGlobalAction
 */
class KeyEventActionHandler @Inject constructor() : ActionHandler<Action.KeyEvent> {

    override suspend fun execute(action: Action.KeyEvent, context: ActionContext): ActionResult {
        if (!MarabaAccessibilityService.isConnected) {
            return ActionResult.Failure(FailureReason.NO_ACCESSIBILITY, "Accessibility Service not connected")
        }
        // TODO: map action.keyCode to performGlobalAction() or dispatchKeyEvent()
        Timber.d("KeyEventAction: ${action.keyCode}")
        return ActionResult.Success
    }
}
