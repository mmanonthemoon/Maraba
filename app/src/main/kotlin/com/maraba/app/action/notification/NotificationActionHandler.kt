package com.maraba.app.action.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat
import com.maraba.app.action.base.ActionContext
import com.maraba.app.action.base.ActionHandler
import com.maraba.app.action.base.ActionResult
import com.maraba.app.data.model.Action
import dagger.hilt.android.qualifiers.ApplicationContext
import timber.log.Timber
import javax.inject.Inject

/**
 * NotificationActionHandler — shows, dismisses, or replies to notifications.
 * TODO: implement DismissNotification and ReplyNotification
 */
class NotificationActionHandler @Inject constructor(
    @ApplicationContext private val context: Context
) : ActionHandler<Action.ShowNotification> {

    override suspend fun execute(action: Action.ShowNotification, context: ActionContext): ActionResult {
        ensureChannel(action.channelId)
        val resolvedTitle = context.resolveVariable(action.title)
        val resolvedText = context.resolveVariable(action.text)

        val notification = NotificationCompat.Builder(this.context, action.channelId)
            .setContentTitle(resolvedTitle)
            .setContentText(resolvedText)
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setAutoCancel(true)
            .build()

        val manager = this.context.getSystemService(NotificationManager::class.java)
        val notifId = (System.currentTimeMillis() % Int.MAX_VALUE).toInt()
        manager.notify(notifId, notification)
        Timber.d("ShowNotificationAction: title=$resolvedTitle")
        return ActionResult.Success
    }

    private fun ensureChannel(channelId: String) {
        val manager = context.getSystemService(NotificationManager::class.java)
        if (manager.getNotificationChannel(channelId) == null) {
            val channel = NotificationChannel(
                channelId,
                context.getString(com.maraba.app.R.string.notification_channel_macro_name),
                NotificationManager.IMPORTANCE_DEFAULT
            )
            manager.createNotificationChannel(channel)
        }
    }
}
