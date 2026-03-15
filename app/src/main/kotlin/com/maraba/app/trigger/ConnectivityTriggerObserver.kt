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
 * ConnectivityTriggerObserver — Wi-Fi and Bluetooth connect/disconnect events.
 * TODO: implement ConnectivityManager.NetworkCallback and BroadcastReceiver
 */
class ConnectivityTriggerObserver @Inject constructor(
    @ApplicationContext private val context: Context
) : TriggerHandler {

    override fun observe(scope: CoroutineScope): Flow<TriggerEvent> {
        Timber.d("ConnectivityTriggerObserver observe started")
        return emptyFlow()
    }

    override fun stop() {
        Timber.d("ConnectivityTriggerObserver stopped")
    }
}
