package com.maraba.app.trigger

import com.maraba.app.trigger.base.TriggerEvent
import com.maraba.app.trigger.base.TriggerHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import timber.log.Timber
import javax.inject.Inject

/**
 * AppTriggerTracker — tracks foreground app changes via AccessibilityService.
 * TODO: implement AccessibilityEvent → app open/close detection
 */
class AppTriggerTracker @Inject constructor() : TriggerHandler {

    override fun observe(scope: CoroutineScope): Flow<TriggerEvent> {
        // TODO: listen to MarabaAccessibilityService for window state changes
        Timber.d("AppTriggerTracker observe started")
        return emptyFlow()
    }

    override fun stop() {
        Timber.d("AppTriggerTracker stopped")
    }
}
