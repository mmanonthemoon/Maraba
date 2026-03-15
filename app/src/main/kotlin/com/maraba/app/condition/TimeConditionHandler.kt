package com.maraba.app.condition

import com.maraba.app.condition.base.ConditionHandler
import com.maraba.app.data.model.Condition
import timber.log.Timber
import java.util.Calendar
import javax.inject.Inject

/**
 * TimeConditionHandler — checks if current time is within a time range.
 */
class TimeConditionHandler @Inject constructor() : ConditionHandler<Condition.TimeRange> {

    override suspend fun evaluate(condition: Condition.TimeRange): Boolean {
        val calendar = Calendar.getInstance()
        val currentMinutes = calendar.get(Calendar.HOUR_OF_DAY) * 60 + calendar.get(Calendar.MINUTE)
        val startMinutes = condition.startHour * 60 + condition.startMinute
        val endMinutes = condition.endHour * 60 + condition.endMinute

        val inRange = if (startMinutes <= endMinutes) {
            currentMinutes in startMinutes..endMinutes
        } else {
            // Crosses midnight
            currentMinutes >= startMinutes || currentMinutes <= endMinutes
        }

        val result = if (condition.isNegated) !inRange else inRange
        Timber.d("TimeCondition: $currentMinutes in [$startMinutes, $endMinutes] → $result")
        return result
    }
}
