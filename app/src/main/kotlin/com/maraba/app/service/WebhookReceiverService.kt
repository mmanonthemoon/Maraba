package com.maraba.app.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

/**
 * WebhookReceiverService — local HTTP server for incoming webhook triggers (optional).
 * TODO: implement NanoHTTPD or Ktor-based local server
 */
@AndroidEntryPoint
class WebhookReceiverService : Service() {

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onCreate() {
        super.onCreate()
        Timber.d("WebhookReceiverService created")
        // TODO: start local HTTP server on configurable port (default 8765)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        // TODO: start listening
        return START_STICKY
    }

    override fun onDestroy() {
        // TODO: stop HTTP server
        super.onDestroy()
    }
}
