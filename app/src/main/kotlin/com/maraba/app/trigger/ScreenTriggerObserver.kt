package com.maraba.app.trigger

import com.maraba.app.trigger.base.TriggerEvent
import com.maraba.app.trigger.base.TriggerHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import timber.log.Timber
import javax.inject.Inject

/**
 * ScreenTriggerObserver — screen on/off/unlock events from BroadcastReceiver.
 * TODO: bridge BroadcastTriggerReceiver events via SharedFlow
 */
class ScreenTriggerObserver @Inject constructor() : TriggerHandler {

    override fun observe(scope: CoroutineScope): Flow<TriggerEvent> {
        Timber.d("ScreenTriggerObserver observe started")
        return emptyFlow()
    }

    override fun stop() {
        Timber.d("ScreenTriggerObserver stopped")
    }
}
