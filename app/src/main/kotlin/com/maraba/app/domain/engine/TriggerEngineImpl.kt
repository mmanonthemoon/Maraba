package com.maraba.app.domain.engine

import com.maraba.app.trigger.base.TriggerEvent
import com.maraba.app.trigger.base.TriggerHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.merge
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

/**
 * TriggerEngineImpl — concrete implementation.
 * TODO: implement start/stop logic, event routing to MacroExecutor
 */
@Singleton
class TriggerEngineImpl @Inject constructor(
    private val handlers: Set<@JvmSuppressWildcards TriggerHandler>,
    private val conditionEvaluator: ConditionEvaluator,
    private val macroExecutor: MacroExecutor
) : TriggerEngine {

    private val _events = MutableSharedFlow<TriggerEvent>(extraBufferCapacity = 64)

    override fun start(scope: CoroutineScope) {
        // TODO: merge all handler flows, route to conditionEvaluator → macroExecutor
        Timber.d("TriggerEngine started with ${handlers.size} handlers")
    }

    override fun stop() {
        // TODO: stop all handlers
        handlers.forEach { it.stop() }
        Timber.d("TriggerEngine stopped")
    }

    override fun observeEvents(): Flow<TriggerEvent> = _events.asSharedFlow()
}
