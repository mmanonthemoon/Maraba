package com.maraba.app.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * MarabaVariable — ADR-005
 * Tasker'daki %VARIABLE sistemine benzer.
 * Built-in değişkenler VariableManager tarafından anlık güncellenir.
 */
@Serializable
data class MarabaVariable(
    val name: String,           // %BATTERY, %TIME, kullanıcı tanımlı
    val value: String,
    val type: VariableType,
    val isBuiltIn: Boolean      // sistem değişkeni mi?
)

@Serializable
enum class VariableType {
    @SerialName("string") STRING,
    @SerialName("int") INT,
    @SerialName("float") FLOAT,
    @SerialName("bool") BOOL,
    @SerialName("list") LIST
}

/**
 * Tüm built-in (sistem) değişkenleri.
 * VariableManager bu değişkenleri StateFlow ile yönetir.
 */
object BuiltInVariables {

    // Sistem değişkeni adları (% prefix dahil)
    const val TIME = "%TIME"                // örn. "14:35"
    const val DATE = "%DATE"                // örn. "2024-01-15"
    const val BATTERY = "%BATTERY"          // örn. "87"
    const val WIFI_SSID = "%WIFI_SSID"      // örn. "Ev_Ağı" veya ""
    const val LOCATION = "%LOCATION"        // örn. "39.9208,32.8541"
    const val LAST_APP = "%LAST_APP"        // örn. "com.whatsapp"
    const val SCREEN = "%SCREEN"            // "on" veya "off"
    const val CHARGING = "%CHARGING"        // "true" veya "false"
    const val VOLUME_MEDIA = "%VOLUME_MEDIA"// örn. "60"
    const val VOLUME_RING = "%VOLUME_RING"
    const val RINGER_MODE = "%RINGER_MODE"  // "normal", "silent", "vibrate"
    const val BLUETOOTH = "%BLUETOOTH"      // "on" veya "off"
    const val AIRPLANE_MODE = "%AIRPLANE_MODE"  // "on" veya "off"
    const val DEVICE_MODEL = "%DEVICE_MODEL"    // örn. "Pixel 8"
    const val ANDROID_VERSION = "%ANDROID_VERSION"  // örn. "14"

    /** Tüm built-in değişkenlerin varsayılan MarabaVariable listesi */
    fun defaultList(): List<MarabaVariable> = listOf(
        MarabaVariable(TIME, "", VariableType.STRING, isBuiltIn = true),
        MarabaVariable(DATE, "", VariableType.STRING, isBuiltIn = true),
        MarabaVariable(BATTERY, "0", VariableType.INT, isBuiltIn = true),
        MarabaVariable(WIFI_SSID, "", VariableType.STRING, isBuiltIn = true),
        MarabaVariable(LOCATION, "", VariableType.STRING, isBuiltIn = true),
        MarabaVariable(LAST_APP, "", VariableType.STRING, isBuiltIn = true),
        MarabaVariable(SCREEN, "off", VariableType.STRING, isBuiltIn = true),
        MarabaVariable(CHARGING, "false", VariableType.BOOL, isBuiltIn = true),
        MarabaVariable(VOLUME_MEDIA, "0", VariableType.INT, isBuiltIn = true),
        MarabaVariable(VOLUME_RING, "0", VariableType.INT, isBuiltIn = true),
        MarabaVariable(RINGER_MODE, "normal", VariableType.STRING, isBuiltIn = true),
        MarabaVariable(BLUETOOTH, "off", VariableType.STRING, isBuiltIn = true),
        MarabaVariable(AIRPLANE_MODE, "off", VariableType.STRING, isBuiltIn = true),
        MarabaVariable(DEVICE_MODEL, "", VariableType.STRING, isBuiltIn = true),
        MarabaVariable(ANDROID_VERSION, "", VariableType.STRING, isBuiltIn = true)
    )
}
