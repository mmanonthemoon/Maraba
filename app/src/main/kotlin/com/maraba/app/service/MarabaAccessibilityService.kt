package com.maraba.app.service

import android.accessibilityservice.AccessibilityService
import android.view.accessibility.AccessibilityEvent
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

/**
 * MarabaAccessibilityService — UI automation service (ADR-006, service-developer).
 *
 * Rules:
 * - onAccessibilityEvent() must return quickly; heavy work via launch(Dispatchers.Default)
 * - AccessibilityNodeInfo.recycle() after every use
 * - Notify TriggerEngine when service connects/disconnects
 *
 * TODO: implement UIActionHandler bridge, AppStateTracker
 */
@AndroidEntryPoint
class MarabaAccessibilityService : AccessibilityService() {

    companion object {
        @Volatile
        var instance: MarabaAccessibilityService? = null
            private set

        val isConnected: Boolean get() = instance != null
    }

    override fun onServiceConnected() {
        instance = this
        Timber.d("MarabaAccessibilityService connected")
        // TODO: notify TriggerEngine → AppTriggerTracker can now work
    }

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        // Must return fast — delegate to background dispatcher
        // TODO: dispatch to AppTriggerTracker, UIActionHandler
    }

    override fun onInterrupt() {
        Timber.w("MarabaAccessibilityService interrupted")
    }

    override fun onDestroy() {
        instance = null
        // TODO: notify TriggerEngine → UI actions disabled
        super.onDestroy()
        Timber.d("MarabaAccessibilityService destroyed")
    }
}
