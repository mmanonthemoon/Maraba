package com.maraba.app.service

import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

/**
 * MarabaNotificationListener — NotificationListenerService (ADR-006, service-developer).
 *
 * Rules:
 * - Filter by package name — don't process ALL notifications (performance)
 * - PII data (notification text) NOT written to logs — only match result
 *
 * TODO: connect to NotificationTriggerListener, implement filtering
 */
@AndroidEntryPoint
class MarabaNotificationListener : NotificationListenerService() {

    companion object {
        @Volatile
        var instance: MarabaNotificationListener? = null
            private set

        val isConnected: Boolean get() = instance != null
    }

    override fun onListenerConnected() {
        instance = this
        Timber.d("MarabaNotificationListener connected")
    }

    override fun onNotificationPosted(sbn: StatusBarNotification?) {
        // TODO: route to NotificationTriggerListener, filter by registered packages
        // IMPORTANT: do NOT log sbn.notification.extras (PII)
    }

    override fun onNotificationRemoved(sbn: StatusBarNotification?) {
        // TODO: route to NotificationTriggerListener (dismissed trigger)
    }

    override fun onListenerDisconnected() {
        instance = null
        Timber.d("MarabaNotificationListener disconnected")
    }
}
