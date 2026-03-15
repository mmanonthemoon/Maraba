package com.maraba.app.condition

import com.maraba.app.condition.base.ConditionHandler
import com.maraba.app.data.model.BuiltInVariables
import com.maraba.app.data.model.Condition
import com.maraba.app.domain.engine.VariableManager
import timber.log.Timber
import javax.inject.Inject
import kotlin.math.cos
import kotlin.math.pow
import kotlin.math.sqrt

/**
 * LocationConditionHandler — checks if device is inside a defined area.
 * Uses simple Euclidean distance approximation.
 * TODO: use proper Haversine formula or Location.distanceBetween
 */
class LocationConditionHandler @Inject constructor(
    private val variableManager: VariableManager
) : ConditionHandler<Condition.InsideArea> {

    override suspend fun evaluate(condition: Condition.InsideArea): Boolean {
        val locationStr = variableManager.get(BuiltInVariables.LOCATION) ?: ""
        if (locationStr.isEmpty()) {
            return if (condition.isNegated) true else false
        }
        val parts = locationStr.split(",")
        if (parts.size != 2) return false

        val lat = parts[0].toDoubleOrNull() ?: return false
        val lon = parts[1].toDoubleOrNull() ?: return false

        // TODO: replace with Location.distanceBetween
        val distance = approximateDistanceMeters(lat, lon, condition.latitude, condition.longitude)
        val isInside = distance <= condition.radiusMeters
        val result = if (condition.isNegated) !isInside else isInside
        Timber.d("LocationCondition: distance=${distance}m radius=${condition.radiusMeters}m → $result")
        return result
    }

    private fun approximateDistanceMeters(lat1: Double, lon1: Double, lat2: Double, lon2: Double): Double {
        val earthRadius = 6_371_000.0
        val dLat = Math.toRadians(lat2 - lat1)
        val dLon = Math.toRadians(lon2 - lon1)
        val x = dLon * cos(Math.toRadians((lat1 + lat2) / 2))
        return earthRadius * sqrt(dLat.pow(2) + x.pow(2))
    }
}
