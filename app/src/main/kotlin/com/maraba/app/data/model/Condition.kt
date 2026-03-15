package com.maraba.app.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Condition sealed class — ADR-004
 * Trigger ateşlendikten sonra değerlendirilir.
 * Tüm koşullar true olursa aksiyonlar çalışır.
 * when() expression'larda else branch kullanılmaz.
 */
@Serializable
sealed class Condition {

    abstract val id: String
    abstract val isNegated: Boolean  // true = koşulun tersi

    // ── Zaman Koşulları ───────────────────────────────────────────────────

    @Serializable
    @SerialName("time_range")
    data class TimeRange(
        override val id: String,
        override val isNegated: Boolean = false,
        val startHour: Int,
        val startMinute: Int,
        val endHour: Int,
        val endMinute: Int
    ) : Condition()

    @Serializable
    @SerialName("day_of_week")
    data class DayOfWeek(
        override val id: String,
        override val isNegated: Boolean = false,
        val days: Set<Int>          // 1=Pazartesi … 7=Pazar
    ) : Condition()

    // ── Uygulama Koşulları ────────────────────────────────────────────────

    @Serializable
    @SerialName("app_foreground")
    data class AppForeground(
        override val id: String,
        override val isNegated: Boolean = false,
        val packageName: String
    ) : Condition()

    @Serializable
    @SerialName("app_installed")
    data class AppInstalled(
        override val id: String,
        override val isNegated: Boolean = false,
        val packageName: String
    ) : Condition()

    // ── Bağlantı Koşulları ────────────────────────────────────────────────

    @Serializable
    @SerialName("wifi_connected_condition")
    data class WifiConnected(
        override val id: String,
        override val isNegated: Boolean = false,
        val ssid: String?           // null = herhangi bir Wi-Fi
    ) : Condition()

    @Serializable
    @SerialName("bluetooth_connected_condition")
    data class BluetoothConnected(
        override val id: String,
        override val isNegated: Boolean = false,
        val deviceName: String?
    ) : Condition()

    // ── Pil Koşulları ─────────────────────────────────────────────────────

    @Serializable
    @SerialName("battery_level_condition")
    data class BatteryLevel(
        override val id: String,
        override val isNegated: Boolean = false,
        val threshold: Int,         // 0–100
        val isAboveThreshold: Boolean   // true = üstünde, false = altında
    ) : Condition()

    @Serializable
    @SerialName("is_charging")
    data class IsCharging(
        override val id: String,
        override val isNegated: Boolean = false
    ) : Condition()

    // ── Konum Koşulları ───────────────────────────────────────────────────

    @Serializable
    @SerialName("inside_area")
    data class InsideArea(
        override val id: String,
        override val isNegated: Boolean = false,
        val locationName: String,
        val latitude: Double,
        val longitude: Double,
        val radiusMeters: Float
    ) : Condition()

    // ── Değişken Koşulları ────────────────────────────────────────────────

    @Serializable
    @SerialName("variable_equals")
    data class VariableEquals(
        override val id: String,
        override val isNegated: Boolean = false,
        val variableName: String,
        val expectedValue: String
    ) : Condition()

    @Serializable
    @SerialName("variable_contains")
    data class VariableContains(
        override val id: String,
        override val isNegated: Boolean = false,
        val variableName: String,
        val substring: String
    ) : Condition()

    @Serializable
    @SerialName("variable_greater_than")
    data class VariableGreaterThan(
        override val id: String,
        override val isNegated: Boolean = false,
        val variableName: String,
        val threshold: Double
    ) : Condition()

    @Serializable
    @SerialName("variable_less_than")
    data class VariableLessThan(
        override val id: String,
        override val isNegated: Boolean = false,
        val variableName: String,
        val threshold: Double
    ) : Condition()

    // ── Bildirim Koşulları ────────────────────────────────────────────────

    @Serializable
    @SerialName("has_pending_notification")
    data class HasPendingNotification(
        override val id: String,
        override val isNegated: Boolean = false,
        val packageName: String?
    ) : Condition()

    // ── Ekran Koşulları ───────────────────────────────────────────────────

    @Serializable
    @SerialName("screen_on_condition")
    data class ScreenOn(
        override val id: String,
        override val isNegated: Boolean = false
    ) : Condition()

    // ── Ses Modu Koşulları ────────────────────────────────────────────────

    @Serializable
    @SerialName("ringer_mode")
    data class RingerMode(
        override val id: String,
        override val isNegated: Boolean = false,
        val mode: RingerModeType
    ) : Condition()
}

@Serializable
enum class RingerModeType {
    @SerialName("normal") NORMAL,
    @SerialName("silent") SILENT,
    @SerialName("vibrate") VIBRATE
}
