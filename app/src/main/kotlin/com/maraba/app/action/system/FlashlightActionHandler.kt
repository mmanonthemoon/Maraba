package com.maraba.app.action.system

import android.content.Context
import android.hardware.camera2.CameraManager
import com.maraba.app.action.base.ActionContext
import com.maraba.app.action.base.ActionHandler
import com.maraba.app.action.base.ActionResult
import com.maraba.app.action.base.FailureReason
import com.maraba.app.data.model.Action
import dagger.hilt.android.qualifiers.ApplicationContext
import timber.log.Timber
import javax.inject.Inject

/**
 * FlashlightActionHandler — toggles the device flashlight via CameraManager.
 */
class FlashlightActionHandler @Inject constructor(
    @ApplicationContext private val context: Context
) : ActionHandler<Action.FlashlightAction> {

    override suspend fun execute(action: Action.FlashlightAction, context: ActionContext): ActionResult {
        val cameraManager = this.context.getSystemService(CameraManager::class.java)
        val cameraId = cameraManager?.cameraIdList?.firstOrNull()
            ?: return ActionResult.Failure(FailureReason.INVALID_PARAMETER, "No camera found")

        cameraManager.setTorchMode(cameraId, action.enable)
        Timber.d("FlashlightAction: enable=${action.enable}")
        return ActionResult.Success
    }
}
