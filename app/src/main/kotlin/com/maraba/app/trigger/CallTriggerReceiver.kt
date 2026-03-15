package com.maraba.app.trigger

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

/**
 * CallTriggerReceiver — handles incoming/outgoing call state changes.
 * Registered in AndroidManifest for PHONE_STATE and NEW_OUTGOING_CALL.
 * TODO: parse TelephonyManager states, emit to TriggerEngine via SharedFlow
 */
@AndroidEntryPoint
class CallTriggerReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        Timber.d("CallTriggerReceiver: ${intent.action}")
        when (intent.action) {
            "android.intent.action.PHONE_STATE" -> {
                // TODO: handle RINGING, OFFHOOK, IDLE states
            }
            Intent.ACTION_NEW_OUTGOING_CALL -> {
                // TODO: handle outgoing call
            }
        }
    }
}
