package com.maraba.app.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Trigger sealed class — ADR-002
 * Her trigger tipi kendi handler'ına sahiptir.
 * when() expression'larda else branch kullanılmaz.
 */
@Serializable
sealed class Trigger {

    /** Benzersiz kimlik (her trigger instance için UUID) */
    abstract val id: String

    // ── Zaman Trigger'ları ────────────────────────────────────────────────

    @Serializable
    @SerialName("time_schedule")
    data class TimeSchedule(
        override val id: String,
        val hour: Int,              // 0–23
        val minute: Int,            // 0–59
        val repeatDays: Set<Int>    // 1=Pazartesi … 7=Pazar, boş=her gün
    ) : Trigger()

    @Serializable
    @SerialName("time_interval")
    data class TimeInterval(
        override val id: String,
        val intervalMinutes: Int    // minimum 1 dakika
    ) : Trigger()

    @Serializable
    @SerialName("time_sunrise_sunset")
    data class SunriseSunset(
        override val id: String,
        val isSunrise: Boolean,
        val offsetMinutes: Int = 0  // +/- dakika
    ) : Trigger()

    // ── Uygulama Trigger'ları ─────────────────────────────────────────────

    @Serializable
    @SerialName("app_opened")
    data class AppOpened(
        override val id: String,
        val packageName: String
    ) : Trigger()

    @Serializable
    @SerialName("app_closed")
    data class AppClosed(
        override val id: String,
        val packageName: String
    ) : Trigger()

    // ── Bildirim Trigger'ları ─────────────────────────────────────────────

    @Serializable
    @SerialName("notification_received")
    data class NotificationReceived(
        override val id: String,
        val packageName: String?,       // null = her uygulama
        val titleContains: String?,
        val textContains: String?
    ) : Trigger()

    @Serializable
    @SerialName("notification_dismissed")
    data class NotificationDismissed(
        override val id: String,
        val packageName: String?
    ) : Trigger()

    // ── Konum Trigger'ları ────────────────────────────────────────────────

    @Serializable
    @SerialName("location_enter")
    data class LocationEnter(
        override val id: String,
        val locationName: String,
        val latitude: Double,
        val longitude: Double,
        val radiusMeters: Float
    ) : Trigger()

    @Serializable
    @SerialName("location_exit")
    data class LocationExit(
        override val id: String,
        val locationName: String,
        val latitude: Double,
        val longitude: Double,
        val radiusMeters: Float
    ) : Trigger()

    // ── Ekran Trigger'ları ────────────────────────────────────────────────

    @Serializable
    @SerialName("screen_on")
    data class ScreenOn(
        override val id: String
    ) : Trigger()

    @Serializable
    @SerialName("screen_off")
    data class ScreenOff(
        override val id: String
    ) : Trigger()

    @Serializable
    @SerialName("screen_unlock")
    data class ScreenUnlock(
        override val id: String
    ) : Trigger()

    // ── Bağlantı Trigger'ları ─────────────────────────────────────────────

    @Serializable
    @SerialName("wifi_connected")
    data class WifiConnected(
        override val id: String,
        val ssid: String?   // null = herhangi bir Wi-Fi
    ) : Trigger()

    @Serializable
    @SerialName("wifi_disconnected")
    data class WifiDisconnected(
        override val id: String,
        val ssid: String?
    ) : Trigger()

    @Serializable
    @SerialName("bluetooth_connected")
    data class BluetoothConnected(
        override val id: String,
        val deviceName: String?
    ) : Trigger()

    @Serializable
    @SerialName("bluetooth_disconnected")
    data class BluetoothDisconnected(
        override val id: String,
        val deviceName: String?
    ) : Trigger()

    // ── Sistem Trigger'ları ───────────────────────────────────────────────

    @Serializable
    @SerialName("boot_completed")
    data class BootCompleted(
        override val id: String
    ) : Trigger()

    @Serializable
    @SerialName("charging_started")
    data class ChargingStarted(
        override val id: String
    ) : Trigger()

    @Serializable
    @SerialName("charging_stopped")
    data class ChargingStopped(
        override val id: String
    ) : Trigger()

    @Serializable
    @SerialName("battery_level")
    data class BatteryLevel(
        override val id: String,
        val level: Int,             // 0–100
        val isBelowLevel: Boolean   // true = düştüğünde, false = yükseldiğinde
    ) : Trigger()

    @Serializable
    @SerialName("airplane_mode_changed")
    data class AirplaneModeChanged(
        override val id: String,
        val isEnabled: Boolean
    ) : Trigger()

    // ── Sensör Trigger'ları ───────────────────────────────────────────────

    @Serializable
    @SerialName("shake_detected")
    data class ShakeDetected(
        override val id: String,
        val sensitivityLevel: Int = 2   // 1–5
    ) : Trigger()

    @Serializable
    @SerialName("device_flipped")
    data class DeviceFlipped(
        override val id: String,
        val isFaceDown: Boolean
    ) : Trigger()

    // ── İletişim Trigger'ları ─────────────────────────────────────────────

    @Serializable
    @SerialName("incoming_call")
    data class IncomingCall(
        override val id: String,
        val fromNumber: String?     // null = herhangi bir numara
    ) : Trigger()

    @Serializable
    @SerialName("call_answered")
    data class CallAnswered(
        override val id: String
    ) : Trigger()

    @Serializable
    @SerialName("call_ended")
    data class CallEnded(
        override val id: String
    ) : Trigger()

    @Serializable
    @SerialName("sms_received")
    data class SmsReceived(
        override val id: String,
        val fromNumber: String?,
        val textContains: String?
    ) : Trigger()

    // ── Değişken Trigger'ları ─────────────────────────────────────────────

    @Serializable
    @SerialName("variable_changed")
    data class VariableChanged(
        override val id: String,
        val variableName: String,
        val newValueEquals: String?     // null = herhangi bir değişiklik
    ) : Trigger()

    // ── Webhook Trigger ───────────────────────────────────────────────────

    @Serializable
    @SerialName("webhook_received")
    data class WebhookReceived(
        override val id: String,
        val path: String,               // örn. "/my-webhook"
        val secretToken: String?
    ) : Trigger()
}
