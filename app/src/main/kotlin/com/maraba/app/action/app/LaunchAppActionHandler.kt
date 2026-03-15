package com.maraba.app.action.app

import android.content.Context
import com.maraba.app.action.base.ActionContext
import com.maraba.app.action.base.ActionHandler
import com.maraba.app.action.base.ActionResult
import com.maraba.app.action.base.FailureReason
import com.maraba.app.data.model.Action
import dagger.hilt.android.qualifiers.ApplicationContext
import timber.log.Timber
import javax.inject.Inject

/**
 * LaunchAppActionHandler — opens an app by package name.
 * TODO: implement packageManager.getLaunchIntentForPackage
 */
class LaunchAppActionHandler @Inject constructor(
    @ApplicationContext private val context: Context
) : ActionHandler<Action.LaunchApp> {

    override suspend fun execute(action: Action.LaunchApp, context: ActionContext): ActionResult {
        val intent = this.context.packageManager.getLaunchIntentForPackage(action.packageName)
        if (intent == null) {
            return ActionResult.Failure(FailureReason.INVALID_PARAMETER, "App not found: ${action.packageName}")
        }
        intent.addFlags(android.content.Intent.FLAG_ACTIVITY_NEW_TASK)
        this.context.startActivity(intent)
        Timber.d("LaunchAppAction: ${action.packageName}")
        return ActionResult.Success
    }
}
