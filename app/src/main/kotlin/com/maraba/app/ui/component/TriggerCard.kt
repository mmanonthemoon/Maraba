package com.maraba.app.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.maraba.app.data.model.Trigger

/**
 * TriggerCard — reusable trigger item in macro editor.
 * TODO: display human-readable trigger summary based on type
 */
@Composable
fun TriggerCard(
    trigger: Trigger,
    onRemove: () -> Unit,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 4.dp)
            .clickable(onClick = onClick)
    ) {
        Text(
            text = triggerSummary(trigger),
            modifier = Modifier.padding(16.dp)
        )
    }
}

/**
 * Human-readable summary for a trigger type.
 * TODO: use string resources instead of hardcoded strings
 */
fun triggerSummary(trigger: Trigger): String = when (trigger) {
    is Trigger.TimeSchedule -> "Zaman: ${trigger.hour}:${"%02d".format(trigger.minute)}"
    is Trigger.TimeInterval -> "Her ${trigger.intervalMinutes} dakikada bir"
    is Trigger.SunriseSunset -> if (trigger.isSunrise) "Gün doğumu" else "Gün batımı"
    is Trigger.AppOpened -> "Uygulama açıldı: ${trigger.packageName}"
    is Trigger.AppClosed -> "Uygulama kapandı: ${trigger.packageName}"
    is Trigger.NotificationReceived -> "Bildirim alındı: ${trigger.packageName ?: "herhangi"}"
    is Trigger.NotificationDismissed -> "Bildirim kapatıldı"
    is Trigger.LocationEnter -> "Konuma girildi: ${trigger.locationName}"
    is Trigger.LocationExit -> "Konumdan çıkıldı: ${trigger.locationName}"
    is Trigger.ScreenOn -> "Ekran açıldı"
    is Trigger.ScreenOff -> "Ekran kapandı"
    is Trigger.ScreenUnlock -> "Ekran kilidi açıldı"
    is Trigger.WifiConnected -> "Wi-Fi bağlandı: ${trigger.ssid ?: "herhangi"}"
    is Trigger.WifiDisconnected -> "Wi-Fi kesildi"
    is Trigger.BluetoothConnected -> "Bluetooth bağlandı"
    is Trigger.BluetoothDisconnected -> "Bluetooth kesildi"
    is Trigger.BootCompleted -> "Cihaz açıldı"
    is Trigger.ChargingStarted -> "Şarj başladı"
    is Trigger.ChargingStopped -> "Şarj durdu"
    is Trigger.BatteryLevel -> "Pil seviyesi: ${trigger.level}%"
    is Trigger.AirplaneModeChanged -> "Uçuş modu değişti"
    is Trigger.ShakeDetected -> "Sallama algılandı"
    is Trigger.DeviceFlipped -> "Cihaz ters çevrildi"
    is Trigger.IncomingCall -> "Gelen arama"
    is Trigger.CallAnswered -> "Arama yanıtlandı"
    is Trigger.CallEnded -> "Arama sona erdi"
    is Trigger.SmsReceived -> "SMS alındı"
    is Trigger.VariableChanged -> "Değişken değişti: ${trigger.variableName}"
    is Trigger.WebhookReceived -> "Webhook: ${trigger.path}"
}

@Preview(showBackground = true)
@Composable
private fun TriggerCardPreview() {
    // TODO: preview
}
