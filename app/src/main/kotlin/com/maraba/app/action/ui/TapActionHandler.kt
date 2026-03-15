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
 * TapActionHandler — performs a tap gesture at specified coordinates.
 * Requires AccessibilityService to be connected.
 * TODO: implement GestureDescription API via AccessibilityService
 */
class TapActionHandler @Inject constructor() : ActionHandler<Action.Tap> {

    override suspend fun execute(action: Action.Tap, context: ActionContext): ActionResult {
        if (!MarabaAccessibilityService.isConnected) {
            return ActionResult.Failure(
                reason = FailureReason.NO_ACCESSIBILITY,
                message = "Accessibility Service not connected"
            )
        }
        // TODO: build GestureDescription, dispatchGesture via AccessibilityService
        Timber.d("TapAction: (${action.x}, ${action.y}) duration=${action.durationMs}ms")
        return ActionResult.Success
    }
}
