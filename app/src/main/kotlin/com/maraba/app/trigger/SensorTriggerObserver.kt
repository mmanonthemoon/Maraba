package com.maraba.app.trigger

import android.content.Context
import com.maraba.app.trigger.base.TriggerEvent
import com.maraba.app.trigger.base.TriggerHandler
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import timber.log.Timber
import javax.inject.Inject

/**
 * SensorTriggerObserver — shake detection, device flip, proximity sensor events.
 * TODO: implement SensorManager listeners for accelerometer, proximity
 */
class SensorTriggerObserver @Inject constructor(
    @ApplicationContext private val context: Context
) : TriggerHandler {

    override fun observe(scope: CoroutineScope): Flow<TriggerEvent> {
        Timber.d("SensorTriggerObserver observe started")
        return emptyFlow()
    }

    override fun stop() {
        Timber.d("SensorTriggerObserver stopped")
    }
}
