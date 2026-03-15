package com.maraba.app.trigger

import com.maraba.app.domain.engine.VariableManager
import com.maraba.app.trigger.base.TriggerEvent
import com.maraba.app.trigger.base.TriggerHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import timber.log.Timber
import javax.inject.Inject

/**
 * VariableTriggerObserver — fires when a user variable changes.
 * TODO: observe VariableManager state, emit events on change
 */
class VariableTriggerObserver @Inject constructor(
    private val variableManager: VariableManager
) : TriggerHandler {

    override fun observe(scope: CoroutineScope): Flow<TriggerEvent> {
        Timber.d("VariableTriggerObserver observe started")
        return emptyFlow()
    }

    override fun stop() {
        Timber.d("VariableTriggerObserver stopped")
    }
}
