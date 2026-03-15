package com.maraba.app.trigger.base

import com.maraba.app.data.model.Trigger
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow

/**
 * TriggerHandler interface — every trigger type has its own implementation.
 *
 * Rules (ADR engine-developer):
 * - Emits Flow<TriggerEvent>
 * - If permission missing: emits nothing, logs silently
 * - Same trigger cannot fire twice within 1 second (debounce enforced by impl)
 * - CoroutineScope is cancelled in onDestroy
 */
interface TriggerHandler {

    /**
     * Start observing for this trigger type.
     * @param scope cancelled when the service is destroyed
     * @return cold flow that emits TriggerEvent when trigger fires
     */
    fun observe(scope: CoroutineScope): Flow<TriggerEvent>

    /** Stop observation and release resources */
    fun stop()
}
