package com.maraba.app

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

/**
 * MaRabaApplication — Hilt entry point.
 * Initializes Timber logging in debug builds.
 * TODO: initialize VariableManager built-ins, crash reporting
 */
@HiltAndroidApp
class MaRabaApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        initLogging()
    }

    private fun initLogging() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
        // TODO: plant production crash-reporting tree (e.g., Firebase Crashlytics)
    }
}
