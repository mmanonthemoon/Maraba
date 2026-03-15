package com.maraba.app.trigger

import com.maraba.app.trigger.base.TriggerEvent
import com.maraba.app.trigger.base.TriggerHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import timber.log.Timber
import javax.inject.Inject

/**
 * NotificationTriggerListener — listens to MarabaNotificationListener for notification events.
 * TODO: implement SharedFlow bridge from NotificationListenerService
 */
class NotificationTriggerListener @Inject constructor() : TriggerHandler {

    override fun observe(scope: CoroutineScope): Flow<TriggerEvent> {
        // TODO: connect to MarabaNotificationListener via SharedFlow
        Timber.d("NotificationTriggerListener observe started")
        return emptyFlow()
    }

    override fun stop() {
        Timber.d("NotificationTriggerListener stopped")
    }
}
