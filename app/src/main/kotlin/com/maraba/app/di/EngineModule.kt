package com.maraba.app.di

import com.maraba.app.domain.engine.TriggerEngine
import com.maraba.app.domain.engine.TriggerEngineImpl
import com.maraba.app.trigger.AppTriggerTracker
import com.maraba.app.trigger.BroadcastTriggerReceiver
import com.maraba.app.trigger.CallTriggerReceiver
import com.maraba.app.trigger.ConnectivityTriggerObserver
import com.maraba.app.trigger.LocationTriggerManager
import com.maraba.app.trigger.NotificationTriggerListener
import com.maraba.app.trigger.SMSTriggerReceiver
import com.maraba.app.trigger.ScreenTriggerObserver
import com.maraba.app.trigger.SensorTriggerObserver
import com.maraba.app.trigger.TimeTriggerScheduler
import com.maraba.app.trigger.VariableTriggerObserver
import com.maraba.app.trigger.base.TriggerHandler
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet
import javax.inject.Singleton

/**
 * EngineModule — provides TriggerEngine and binds all TriggerHandlers into a Set.
 * When a new trigger type is added: bind its handler here with @IntoSet.
 */
@Module
@InstallIn(SingletonComponent::class)
abstract class EngineModule {

    @Binds
    @Singleton
    abstract fun bindTriggerEngine(impl: TriggerEngineImpl): TriggerEngine

    // ── TriggerHandler set bindings ──────────────────────────────────────

    @Binds @IntoSet
    abstract fun bindTimeTriggerScheduler(handler: TimeTriggerScheduler): TriggerHandler

    @Binds @IntoSet
    abstract fun bindAppTriggerTracker(handler: AppTriggerTracker): TriggerHandler

    @Binds @IntoSet
    abstract fun bindNotificationTriggerListener(handler: NotificationTriggerListener): TriggerHandler

    @Binds @IntoSet
    abstract fun bindLocationTriggerManager(handler: LocationTriggerManager): TriggerHandler

    @Binds @IntoSet
    abstract fun bindScreenTriggerObserver(handler: ScreenTriggerObserver): TriggerHandler

    @Binds @IntoSet
    abstract fun bindConnectivityTriggerObserver(handler: ConnectivityTriggerObserver): TriggerHandler

    @Binds @IntoSet
    abstract fun bindSensorTriggerObserver(handler: SensorTriggerObserver): TriggerHandler

    @Binds @IntoSet
    abstract fun bindVariableTriggerObserver(handler: VariableTriggerObserver): TriggerHandler
}
