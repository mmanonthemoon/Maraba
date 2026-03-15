package com.maraba.app.trigger

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

/**
 * SMSTriggerReceiver — handles incoming SMS.
 * Registered in AndroidManifest for SMS_RECEIVED.
 * IMPORTANT: SMS content is NOT logged — only match result.
 * TODO: extract sender/text, match against registered SmsReceived triggers
 */
@AndroidEntryPoint
class SMSTriggerReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action != "android.provider.Telephony.SMS_RECEIVED") return
        // TODO: extract PDUs, parse sender and body
        // IMPORTANT: body text is checked for match only — never stored/logged
        Timber.d("SMSTriggerReceiver: SMS received (content not logged)")
    }
}
