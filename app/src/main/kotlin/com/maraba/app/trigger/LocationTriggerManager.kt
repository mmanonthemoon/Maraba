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
 * LocationTriggerManager — Geofencing API based location triggers.
 * TODO: implement Geofencing API, FusedLocationProviderClient
 */
class LocationTriggerManager @Inject constructor(
    @ApplicationContext private val context: Context
) : TriggerHandler {

    override fun observe(scope: CoroutineScope): Flow<TriggerEvent> {
        // TODO: register geofences for all LocationEnter/LocationExit triggers
        Timber.d("LocationTriggerManager observe started")
        return emptyFlow()
    }

    override fun stop() {
        // TODO: remove all geofences
        Timber.d("LocationTriggerManager stopped")
    }
}
