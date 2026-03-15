package com.maraba.app.action.communication

import android.telephony.SmsManager
import com.maraba.app.action.base.ActionContext
import com.maraba.app.action.base.ActionHandler
import com.maraba.app.action.base.ActionResult
import com.maraba.app.action.base.FailureReason
import com.maraba.app.data.model.Action
import timber.log.Timber
import javax.inject.Inject

/**
 * SMSActionHandler — sends an SMS via SmsManager.
 * TODO: check SEND_SMS permission, handle long messages (divideMessage)
 */
class SMSActionHandler @Inject constructor() : ActionHandler<Action.SendSms> {

    override suspend fun execute(action: Action.SendSms, context: ActionContext): ActionResult {
        val resolvedNumber = context.resolveVariable(action.phoneNumber)
        val resolvedMessage = context.resolveVariable(action.message)
        return try {
            val smsManager = SmsManager.getDefault()
            val parts = smsManager.divideMessage(resolvedMessage)
            smsManager.sendMultipartTextMessage(resolvedNumber, null, parts, null, null)
            Timber.d("SMSAction: sent to $resolvedNumber (content not logged)")
            ActionResult.Success
        } catch (e: Exception) {
            Timber.e(e, "SMSAction failed")
            ActionResult.Failure(FailureReason.NO_PERMISSION, e.message ?: "SMS send failed")
        }
    }
}
