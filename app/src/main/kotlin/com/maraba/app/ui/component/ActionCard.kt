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
import com.maraba.app.data.model.Action

/**
 * ActionCard — reusable action item in macro editor.
 * TODO: display human-readable action summary based on type
 */
@Composable
fun ActionCard(
    action: Action,
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
            text = actionSummary(action),
            modifier = Modifier.padding(16.dp)
        )
    }
}

fun actionSummary(action: Action): String = when (action) {
    is Action.Tap -> "Dokun (${action.x.toInt()}, ${action.y.toInt()})"
    is Action.Swipe -> "Kaydır"
    is Action.TypeText -> "Metin yaz: ${action.text.take(20)}"
    is Action.Scroll -> "Kaydır: ${action.direction}"
    is Action.LongPress -> "Uzun bas"
    is Action.KeyEvent -> "Tuş: ${action.keyCode}"
    is Action.LaunchApp -> "Uygulamayı aç: ${action.packageName}"
    is Action.KillApp -> "Uygulamayı kapat: ${action.packageName}"
    is Action.OpenSettings -> "Ayarları aç"
    is Action.OpenUrl -> "URL aç: ${action.url.take(30)}"
    is Action.SetVolume -> "Ses ayarla: ${action.stream} → ${action.level}"
    is Action.SetBrightness -> "Parlaklık: ${action.level}"
    is Action.SetRingerMode -> "Ses modu: ${action.mode}"
    is Action.WifiAction -> if (action.enable) "Wi-Fi aç" else "Wi-Fi kapat"
    is Action.BluetoothAction -> if (action.enable) "Bluetooth aç" else "Bluetooth kapat"
    is Action.FlashlightAction -> if (action.enable) "Fener aç" else "Fener kapat"
    is Action.TakeScreenshot -> "Ekran görüntüsü al"
    is Action.ToggleAirplaneMode -> "Uçuş modu"
    is Action.ShowNotification -> "Bildirim göster: ${action.title}"
    is Action.DismissNotification -> "Bildirimi kapat"
    is Action.ReplyNotification -> "Bildirime yanıt"
    is Action.SendSms -> "SMS gönder"
    is Action.MakeCall -> "Arama yap"
    is Action.SendEmail -> "E-posta gönder"
    is Action.HttpRequest -> "HTTP ${action.method}: ${action.url.take(30)}"
    is Action.ParseJson -> "JSON ayrıştır"
    is Action.SetVariable -> "${action.variableName} = ${action.value}"
    is Action.WriteFile -> "Dosyaya yaz: ${action.fileName}"
    is Action.PlaySound -> "Ses çal"
    is Action.TextToSpeech -> "Sesli oku: ${action.text.take(20)}"
    is Action.MediaControl -> "Medya: ${action.command}"
    is Action.IfElse -> "Koşullu dal"
    is Action.Wait -> "Bekle: ${action.durationMs}ms"
    is Action.Repeat -> "${action.count}x tekrar"
    is Action.StopMacro -> "Makroyu durdur"
    is Action.RunMacro -> "Makro çalıştır"
    is Action.ShellCommand -> "Kabuk komutu (root)"
    is Action.SimulateInput -> "Giriş simüle et (root)"
    is Action.SystemSettingsAction -> "Sistem ayarı (root)"
}

@Preview(showBackground = true)
@Composable
private fun ActionCardPreview() {
    // TODO: preview
}
