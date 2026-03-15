package com.maraba.app.trigger

import android.content.Context
import com.maraba.app.data.model.Trigger
import com.maraba.app.trigger.base.TriggerEvent
import com.maraba.app.trigger.base.TriggerHandler
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import timber.log.Timber
import javax.inject.Inject

/**
 * TimeTriggerScheduler — WorkManager / AlarmManager based time triggers.
 * TODO: implement schedule registration, WorkManager Worker, AlarmManager for exact alarms
 */
class TimeTriggerScheduler @Inject constructor(
    @ApplicationContext private val context: Context
) : TriggerHandler {

    override fun observe(scope: CoroutineScope): Flow<TriggerEvent> {
        // TODO: implement via WorkManager (interval) and AlarmManager (exact time)
        Timber.d("TimeTriggerScheduler observe started")
        return emptyFlow()
    }

    override fun stop() {
        // TODO: cancel scheduled work
        Timber.d("TimeTriggerScheduler stopped")
    }
}
