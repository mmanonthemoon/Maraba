package com.maraba.app.action.system

import android.content.Context
import android.provider.Settings
import com.maraba.app.action.base.ActionContext
import com.maraba.app.action.base.ActionHandler
import com.maraba.app.action.base.ActionResult
import com.maraba.app.action.base.FailureReason
import com.maraba.app.data.model.Action
import dagger.hilt.android.qualifiers.ApplicationContext
import timber.log.Timber
import javax.inject.Inject

/**
 * BrightnessActionHandler — sets screen brightness.
 * Requires WRITE_SETTINGS permission (user must grant from Settings).
 * TODO: check Settings.System.canWrite permission
 */
class BrightnessActionHandler @Inject constructor(
    @ApplicationContext private val context: Context
) : ActionHandler<Action.SetBrightness> {

    override suspend fun execute(action: Action.SetBrightness, context: ActionContext): ActionResult {
        if (!Settings.System.canWrite(this.context)) {
            return ActionResult.Failure(FailureReason.NO_PERMISSION, "WRITE_SETTINGS permission required")
        }
        val mode = if (action.isAuto) Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC
                   else Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL
        Settings.System.putInt(this.context.contentResolver, Settings.System.SCREEN_BRIGHTNESS_MODE, mode)
        if (!action.isAuto) {
            Settings.System.putInt(this.context.contentResolver, Settings.System.SCREEN_BRIGHTNESS, action.level)
        }
        Timber.d("BrightnessAction: level=${action.level} auto=${action.isAuto}")
        return ActionResult.Success
    }
}
