package com.maraba.app.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Intent
import androidx.lifecycle.LifecycleService
import androidx.lifecycle.lifecycleScope
import com.maraba.app.domain.engine.TriggerEngine
import com.maraba.app.domain.engine.VariableManager
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import javax.inject.Inject

/**
 * MarabaForegroundService — the always-running main service (ADR-006, service-developer).
 *
 * Rules:
 * - startForeground() must be called within 10 seconds (ANR risk)
 * - Persistent notification: macro count, last run, pause button
 * - onTaskRemoved(): restart service if user swipes away task
 * - Battery optimization whitelist should be requested on first launch
 *
 * TODO: implement full TriggerEngine lifecycle, notifications, binder
 */
@AndroidEntryPoint
class MarabaForegroundService : LifecycleService() {

    @Inject lateinit var triggerEngine: TriggerEngine
    @Inject lateinit var variableManager: VariableManager

    companion object {
        const val NOTIFICATION_ID = 1001
        const val CHANNEL_ID = "maraba_service"
        const val ACTION_PAUSE = "com.maraba.app.ACTION_PAUSE"
        const val ACTION_RESUME = "com.maraba.app.ACTION_RESUME"
    }

    override fun onCreate() {
        super.onCreate()
        Timber.d("MarabaForegroundService onCreate")
        createNotificationChannel()
        startForeground(NOTIFICATION_ID, buildNotification())
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)
        // TODO: handle ACTION_PAUSE / ACTION_RESUME intents
        triggerEngine.start(lifecycleScope)
        return START_STICKY
    }

    override fun onDestroy() {
        triggerEngine.stop()
        super.onDestroy()
        Timber.d("MarabaForegroundService onDestroy")
    }

    override fun onTaskRemoved(rootIntent: Intent?) {
        // Restart service when user swipes away from recents
        val restartIntent = Intent(applicationContext, MarabaForegroundService::class.java)
        startService(restartIntent)
        super.onTaskRemoved(rootIntent)
    }

    private fun createNotificationChannel() {
        val channel = NotificationChannel(
            CHANNEL_ID,
            getString(com.maraba.app.R.string.notification_channel_service_name),
            NotificationManager.IMPORTANCE_LOW
        ).apply {
            description = getString(com.maraba.app.R.string.notification_channel_service_description)
            setShowBadge(false)
        }
        val manager = getSystemService(NotificationManager::class.java)
        manager.createNotificationChannel(channel)
    }

    private fun buildNotification(): Notification {
        // TODO: build full notification with macro count and control actions
        return Notification.Builder(this, CHANNEL_ID)
            .setContentTitle(getString(com.maraba.app.R.string.service_notification_title))
            .setSmallIcon(android.R.drawable.ic_menu_manage)
            .setOngoing(true)
            .build()
    }
}
