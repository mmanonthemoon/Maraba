package com.maraba.app.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Action sealed class — ADR-003
 * Her aksiyon tipi kendi ActionHandler'ına sahiptir.
 * when() expression'larda else branch kullanılmaz.
 */
@Serializable
sealed class Action {

    abstract val id: String

    // ══════════════════════════════════════════════════════════════════════
    // UI Aksiyonları (Accessibility Service gerektirir)
    // ══════════════════════════════════════════════════════════════════════

    @Serializable
    @SerialName("tap")
    data class Tap(
        override val id: String,
        val x: Float,
        val y: Float,
        val durationMs: Long = 100
    ) : Action()

    @Serializable
    @SerialName("swipe")
    data class Swipe(
        override val id: String,
        val startX: Float,
        val startY: Float,
        val endX: Float,
        val endY: Float,
        val durationMs: Long = 300
    ) : Action()

    @Serializable
    @SerialName("type_text")
    data class TypeText(
        override val id: String,
        val text: String            // %VARIABLE interpolation desteklenir
    ) : Action()

    @Serializable
    @SerialName("scroll")
    data class Scroll(
        override val id: String,
        val direction: ScrollDirection,
        val amount: Int = 3         // adım sayısı
    ) : Action()

    @Serializable
    @SerialName("long_press")
    data class LongPress(
        override val id: String,
        val x: Float,
        val y: Float,
        val durationMs: Long = 1000
    ) : Action()

    @Serializable
    @SerialName("key_event")
    data class KeyEvent(
        override val id: String,
        val keyCode: KeyEventCode
    ) : Action()

    // ══════════════════════════════════════════════════════════════════════
    // Uygulama Aksiyonları
    // ══════════════════════════════════════════════════════════════════════

    @Serializable
    @SerialName("launch_app")
    data class LaunchApp(
        override val id: String,
        val packageName: String
    ) : Action()

    @Serializable
    @SerialName("kill_app")
    data class KillApp(
        override val id: String,
        val packageName: String
    ) : Action()

    @Serializable
    @SerialName("open_settings")
    data class OpenSettings(
        override val id: String,
        val settingsAction: String  // Settings.ACTION_* sabitlerinden biri
    ) : Action()

    @Serializable
    @SerialName("open_url")
    data class OpenUrl(
        override val id: String,
        val url: String
    ) : Action()

    // ══════════════════════════════════════════════════════════════════════
    // Sistem Aksiyonları
    // ══════════════════════════════════════════════════════════════════════

    @Serializable
    @SerialName("set_volume")
    data class SetVolume(
        override val id: String,
        val stream: VolumeStream,
        val level: Int,             // 0–15 (genellikle)
        val showUI: Boolean = false
    ) : Action()

    @Serializable
    @SerialName("set_brightness")
    data class SetBrightness(
        override val id: String,
        val level: Int,             // 0–255
        val isAuto: Boolean = false
    ) : Action()

    @Serializable
    @SerialName("set_ringer_mode")
    data class SetRingerMode(
        override val id: String,
        val mode: RingerModeType
    ) : Action()

    @Serializable
    @SerialName("wifi_action")
    data class WifiAction(
        override val id: String,
        val enable: Boolean
    ) : Action()

    @Serializable
    @SerialName("bluetooth_action")
    data class BluetoothAction(
        override val id: String,
        val enable: Boolean
    ) : Action()

    @Serializable
    @SerialName("flashlight_action")
    data class FlashlightAction(
        override val id: String,
        val enable: Boolean
    ) : Action()

    @Serializable
    @SerialName("take_screenshot")
    data class TakeScreenshot(
        override val id: String,
        val saveToGallery: Boolean = true,
        val saveToVariable: String?         // değişken adı
    ) : Action()

    @Serializable
    @SerialName("toggle_airplane_mode")
    data class ToggleAirplaneMode(
        override val id: String,
        val enable: Boolean
        // Not: Root gerektirir
    ) : Action()

    // ══════════════════════════════════════════════════════════════════════
    // Bildirim Aksiyonları
    // ══════════════════════════════════════════════════════════════════════

    @Serializable
    @SerialName("show_notification")
    data class ShowNotification(
        override val id: String,
        val title: String,
        val text: String,
        val channelId: String = "macro_notifications"
    ) : Action()

    @Serializable
    @SerialName("dismiss_notification")
    data class DismissNotification(
        override val id: String,
        val packageName: String?    // null = tüm bildirimler
    ) : Action()

    @Serializable
    @SerialName("reply_notification")
    data class ReplyNotification(
        override val id: String,
        val packageName: String,
        val replyText: String
    ) : Action()

    // ══════════════════════════════════════════════════════════════════════
    // İletişim Aksiyonları
    // ══════════════════════════════════════════════════════════════════════

    @Serializable
    @SerialName("send_sms")
    data class SendSms(
        override val id: String,
        val phoneNumber: String,
        val message: String         // %VARIABLE interpolation desteklenir
    ) : Action()

    @Serializable
    @SerialName("make_call")
    data class MakeCall(
        override val id: String,
        val phoneNumber: String
    ) : Action()

    @Serializable
    @SerialName("send_email")
    data class SendEmail(
        override val id: String,
        val to: String,
        val subject: String,
        val body: String
    ) : Action()

    // ══════════════════════════════════════════════════════════════════════
    // Veri Aksiyonları
    // ══════════════════════════════════════════════════════════════════════

    @Serializable
    @SerialName("http_request")
    data class HttpRequest(
        override val id: String,
        val method: HttpMethod,
        val url: String,
        val headers: Map<String, String> = emptyMap(),
        val body: String? = null,
        val responseVariable: String?   // yanıtı bu değişkene yaz
    ) : Action()

    @Serializable
    @SerialName("parse_json")
    data class ParseJson(
        override val id: String,
        val sourceVariable: String,
        val jsonPath: String,           // örn. "$.data.name"
        val targetVariable: String
    ) : Action()

    @Serializable
    @SerialName("set_variable")
    data class SetVariable(
        override val id: String,
        val variableName: String,
        val value: String               // %VARIABLE interpolation desteklenir
    ) : Action()

    @Serializable
    @SerialName("write_file")
    data class WriteFile(
        override val id: String,
        val fileName: String,
        val content: String,
        val appendMode: Boolean = false
    ) : Action()

    // ══════════════════════════════════════════════════════════════════════
    // Medya Aksiyonları
    // ══════════════════════════════════════════════════════════════════════

    @Serializable
    @SerialName("play_sound")
    data class PlaySound(
        override val id: String,
        val filePath: String,           // app-private dosya yolu
        val volume: Int = 80            // 0–100
    ) : Action()

    @Serializable
    @SerialName("text_to_speech")
    data class TextToSpeech(
        override val id: String,
        val text: String,               // %VARIABLE interpolation desteklenir
        val language: String = "tr-TR",
        val speed: Float = 1.0f
    ) : Action()

    @Serializable
    @SerialName("media_control")
    data class MediaControl(
        override val id: String,
        val command: MediaCommand
    ) : Action()

    // ══════════════════════════════════════════════════════════════════════
    // Akış Kontrol Aksiyonları
    // ══════════════════════════════════════════════════════════════════════

    @Serializable
    @SerialName("if_else")
    data class IfElse(
        override val id: String,
        val condition: Condition,
        val thenActions: List<Action>,
        val elseActions: List<Action> = emptyList()
    ) : Action()

    @Serializable
    @SerialName("wait")
    data class Wait(
        override val id: String,
        val durationMs: Long        // milisaniye, min 100ms
    ) : Action()

    @Serializable
    @SerialName("repeat")
    data class Repeat(
        override val id: String,
        val count: Int,
        val actions: List<Action>,
        val delayBetweenMs: Long = 0
    ) : Action()

    @Serializable
    @SerialName("stop_macro")
    data class StopMacro(
        override val id: String
    ) : Action()

    @Serializable
    @SerialName("run_macro")
    data class RunMacro(
        override val id: String,
        val macroId: String
    ) : Action()

    // ══════════════════════════════════════════════════════════════════════
    // Root Aksiyonları (Root gerektirir)
    // ══════════════════════════════════════════════════════════════════════

    @Serializable
    @SerialName("shell_command")
    data class ShellCommand(
        override val id: String,
        val command: String,
        val outputVariable: String?     // çıktıyı bu değişkene yaz
    ) : Action()

    @Serializable
    @SerialName("simulate_input")
    data class SimulateInput(
        override val id: String,
        val x: Float,
        val y: Float
        // Root: /dev/input inject
    ) : Action()

    @Serializable
    @SerialName("system_settings_action")
    data class SystemSettingsAction(
        override val id: String,
        val settingKey: String,
        val settingValue: String
    ) : Action()
}

// ── Yardımcı Enum'lar ─────────────────────────────────────────────────────

@Serializable
enum class ScrollDirection {
    @SerialName("up") UP,
    @SerialName("down") DOWN,
    @SerialName("left") LEFT,
    @SerialName("right") RIGHT
}

@Serializable
enum class KeyEventCode {
    @SerialName("back") BACK,
    @SerialName("home") HOME,
    @SerialName("recents") RECENTS,
    @SerialName("volume_up") VOLUME_UP,
    @SerialName("volume_down") VOLUME_DOWN,
    @SerialName("power") POWER,
    @SerialName("media_play_pause") MEDIA_PLAY_PAUSE,
    @SerialName("media_next") MEDIA_NEXT,
    @SerialName("media_prev") MEDIA_PREV
}

@Serializable
enum class VolumeStream {
    @SerialName("ring") RING,
    @SerialName("media") MEDIA,
    @SerialName("alarm") ALARM,
    @SerialName("notification") NOTIFICATION
}

@Serializable
enum class HttpMethod {
    @SerialName("GET") GET,
    @SerialName("POST") POST,
    @SerialName("PUT") PUT,
    @SerialName("DELETE") DELETE,
    @SerialName("PATCH") PATCH
}

@Serializable
enum class MediaCommand {
    @SerialName("play") PLAY,
    @SerialName("pause") PAUSE,
    @SerialName("play_pause") PLAY_PAUSE,
    @SerialName("next") NEXT,
    @SerialName("previous") PREVIOUS,
    @SerialName("stop") STOP
}
