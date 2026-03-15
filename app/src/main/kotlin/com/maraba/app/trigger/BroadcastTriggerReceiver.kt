package com.maraba.app.trigger

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

/**
 * BroadcastTriggerReceiver — handles system broadcast intents.
 * Registered in AndroidManifest for: BOOT_COMPLETED, LOCKED_BOOT_COMPLETED,
 * SCREEN_ON, SCREEN_OFF, AIRPLANE_MODE, BATTERY_LOW, etc.
 * TODO: connect to TriggerEngine SharedFlow, start MarabaForegroundService on boot
 */
@AndroidEntryPoint
class BroadcastTriggerReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        Timber.d("BroadcastTriggerReceiver: ${intent.action}")
        when (intent.action) {
            Intent.ACTION_BOOT_COMPLETED,
            Intent.ACTION_LOCKED_BOOT_COMPLETED -> {
                // TODO: start MarabaForegroundService
            }
            Intent.ACTION_SCREEN_ON -> { /* TODO */ }
            Intent.ACTION_SCREEN_OFF -> { /* TODO */ }
            Intent.ACTION_USER_PRESENT -> { /* TODO */ } // unlock
            Intent.ACTION_POWER_CONNECTED -> { /* TODO */ }
            Intent.ACTION_POWER_DISCONNECTED -> { /* TODO */ }
            Intent.ACTION_AIRPLANE_MODE_CHANGED -> { /* TODO */ }
            Intent.ACTION_BATTERY_LOW -> { /* TODO */ }
            Intent.ACTION_BATTERY_OKAY -> { /* TODO */ }
        }
    }
}
