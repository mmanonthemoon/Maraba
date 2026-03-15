package com.maraba.app.condition

import com.maraba.app.condition.base.ConditionHandler
import com.maraba.app.data.model.BuiltInVariables
import com.maraba.app.data.model.Condition
import com.maraba.app.domain.engine.VariableManager
import timber.log.Timber
import javax.inject.Inject

/**
 * WifiConditionHandler — checks if connected to a specific Wi-Fi SSID.
 */
class WifiConditionHandler @Inject constructor(
    private val variableManager: VariableManager
) : ConditionHandler<Condition.WifiConnected> {

    override suspend fun evaluate(condition: Condition.WifiConnected): Boolean {
        val currentSsid = variableManager.get(BuiltInVariables.WIFI_SSID) ?: ""
        val isConnected = if (condition.ssid == null) {
            currentSsid.isNotEmpty()
        } else {
            currentSsid == condition.ssid
        }
        val result = if (condition.isNegated) !isConnected else isConnected
        Timber.d("WifiCondition: $currentSsid == ${condition.ssid} → $result")
        return result
    }
}
