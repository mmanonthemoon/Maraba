package com.maraba.app.action.system

import android.content.Context
import android.content.Intent
import android.provider.Settings
import com.maraba.app.action.base.ActionContext
import com.maraba.app.action.base.ActionHandler
import com.maraba.app.action.base.ActionResult
import com.maraba.app.data.model.Action
import dagger.hilt.android.qualifiers.ApplicationContext
import timber.log.Timber
import javax.inject.Inject

/**
 * WifiActionHandler — toggles Wi-Fi (API 29+: redirects to Settings panel).
 * Direct Wi-Fi toggle was removed in API 29 — user must interact with Settings panel.
 */
class WifiActionHandler @Inject constructor(
    @ApplicationContext private val context: Context
) : ActionHandler<Action.WifiAction> {

    override suspend fun execute(action: Action.WifiAction, context: ActionContext): ActionResult {
        // API 29+: open Wi-Fi settings panel
        val intent = Intent(Settings.Panel.ACTION_WIFI).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        this.context.startActivity(intent)
        Timber.d("WifiAction: enable=${action.enable} (Settings panel opened)")
        return ActionResult.Success
    }
}
