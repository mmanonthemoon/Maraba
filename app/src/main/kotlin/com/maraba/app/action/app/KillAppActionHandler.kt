package com.maraba.app.action.app

import com.maraba.app.action.base.ActionContext
import com.maraba.app.action.base.ActionHandler
import com.maraba.app.action.base.ActionResult
import com.maraba.app.action.base.FailureReason
import com.maraba.app.data.model.Action
import com.maraba.app.service.MarabaAccessibilityService
import timber.log.Timber
import javax.inject.Inject

/**
 * KillAppActionHandler — closes an app via Accessibility global action.
 * TODO: navigate to recents, find app card and dismiss; or use performGlobalAction
 */
class KillAppActionHandler @Inject constructor() : ActionHandler<Action.KillApp> {

    override suspend fun execute(action: Action.KillApp, context: ActionContext): ActionResult {
        if (!MarabaAccessibilityService.isConnected) {
            return ActionResult.Failure(FailureReason.NO_ACCESSIBILITY, "Accessibility Service not connected")
        }
        // TODO: implement app kill via recents or root am force-stop
        Timber.d("KillAppAction: ${action.packageName}")
        return ActionResult.Success
    }
}
