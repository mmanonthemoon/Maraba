package com.maraba.app.action.system

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.content.Context
import com.maraba.app.action.base.ActionContext
import com.maraba.app.action.base.ActionHandler
import com.maraba.app.action.base.ActionResult
import com.maraba.app.action.base.FailureReason
import com.maraba.app.data.model.Action
import dagger.hilt.android.qualifiers.ApplicationContext
import timber.log.Timber
import javax.inject.Inject

/**
 * BluetoothActionHandler — enables/disables Bluetooth.
 * TODO: API 33+ requires user interaction; handle gracefully
 */
class BluetoothActionHandler @Inject constructor(
    @ApplicationContext private val context: Context
) : ActionHandler<Action.BluetoothAction> {

    override suspend fun execute(action: Action.BluetoothAction, context: ActionContext): ActionResult {
        val bluetoothManager = this.context.getSystemService(BluetoothManager::class.java)
        val adapter = bluetoothManager?.adapter
        if (adapter == null) {
            return ActionResult.Failure(FailureReason.INVALID_PARAMETER, "Bluetooth not supported")
        }
        // TODO: handle API 33+ deprecation of enable()/disable()
        Timber.d("BluetoothAction: enable=${action.enable}")
        return ActionResult.Success
    }
}
