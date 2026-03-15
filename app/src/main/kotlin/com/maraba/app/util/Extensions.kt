package com.maraba.app.util

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * Extensions — common Kotlin extension functions used throughout the app.
 */

/** Format epoch milliseconds to human-readable time string */
fun Long.toTimeString(): String {
    val sdf = SimpleDateFormat("HH:mm", Locale.getDefault())
    return sdf.format(Date(this))
}

/** Format epoch milliseconds to human-readable date-time string */
fun Long.toDateTimeString(): String {
    val sdf = SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault())
    return sdf.format(Date(this))
}

/** Return relative time description (e.g., "5 dakika önce") */
fun Long.toRelativeTime(): String {
    val diff = System.currentTimeMillis() - this
    return when {
        diff < 60_000 -> "Az önce"
        diff < 3_600_000 -> "${diff / 60_000} dakika önce"
        diff < 86_400_000 -> "${diff / 3_600_000} saat önce"
        else -> toDateTimeString()
    }
}

/** Safe cast returning null instead of throwing */
inline fun <reified T> Any?.safeCast(): T? = this as? T
