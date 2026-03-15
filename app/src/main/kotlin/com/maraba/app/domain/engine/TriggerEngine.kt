package com.maraba.app.domain.engine

import com.maraba.app.trigger.base.TriggerEvent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow

/**
 * TriggerEngine — coordinates all trigger handlers (ADR-002, engine-developer).
 *
 * Architecture:
 * - Each TriggerHandler emits Flow<TriggerEvent>
 * - TriggerEngine merges all flows via merge()
 * - On event: ConditionEvaluator → MacroExecutor chain (coroutine scope)
 * - One handler's failure must NOT stop others (catch + log + continue)
 *
 * TODO: implement start(), stop(), event routing
 */
interface TriggerEngine {

    /** Start all registered trigger handlers */
    fun start(scope: CoroutineScope)

    /** Stop all trigger handlers and cancel observation */
    fun stop()

    /** Merged flow of all trigger events — for testing/debugging */
    fun observeEvents(): Flow<TriggerEvent>
}
