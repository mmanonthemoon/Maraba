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
 * SwipeActionHandler — performs a swipe gesture.
 * TODO: implement GestureDescription API
 */
class SwipeActionHandler @Inject constructor() : ActionHandler<Action.Swipe> {

    override suspend fun execute(action: Action.Swipe, context: ActionContext): ActionResult {
        if (!MarabaAccessibilityService.isConnected) {
            return ActionResult.Failure(FailureReason.NO_ACCESSIBILITY, "Accessibility Service not connected")
        }
        // TODO: build GestureDescription stroke
        Timber.d("SwipeAction: (${action.startX},${action.startY}) → (${action.endX},${action.endY})")
        return ActionResult.Success
    }
}
