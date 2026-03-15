package com.maraba.app.trigger.base

import com.maraba.app.data.model.Trigger

/**
 * TriggerEvent — emitted by TriggerHandler implementations when a trigger fires.
 * TriggerEngine merges all flows and routes events to MacroExecutor.
 */
data class TriggerEvent(
    val trigger: Trigger,
    val timestamp: Long = System.currentTimeMillis(),
    /** Optional payload data from the trigger (e.g., SMS sender, notification title) */
    val payload: Map<String, String> = emptyMap()
)
