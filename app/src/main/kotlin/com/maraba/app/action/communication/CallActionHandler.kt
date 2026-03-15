package com.maraba.app.action.communication

import android.content.Context
import android.content.Intent
import android.net.Uri
import com.maraba.app.action.base.ActionContext
import com.maraba.app.action.base.ActionHandler
import com.maraba.app.action.base.ActionResult
import com.maraba.app.data.model.Action
import dagger.hilt.android.qualifiers.ApplicationContext
import timber.log.Timber
import javax.inject.Inject

/**
 * CallActionHandler — initiates a phone call.
 * Uses ACTION_CALL intent (requires CALL_PHONE permission).
 */
class CallActionHandler @Inject constructor(
    @ApplicationContext private val context: Context
) : ActionHandler<Action.MakeCall> {

    override suspend fun execute(action: Action.MakeCall, context: ActionContext): ActionResult {
        val resolvedNumber = context.resolveVariable(action.phoneNumber)
        val intent = Intent(Intent.ACTION_CALL, Uri.parse("tel:$resolvedNumber")).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        this.context.startActivity(intent)
        Timber.d("CallAction: initiating call (number not logged)")
        return ActionResult.Success
    }
}
