package com.maraba.app.ui.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.maraba.app.R

/**
 * PermissionBanner — permission status card with Settings shortcut.
 * Reusable component, no ViewModel dependency.
 */
@Composable
fun PermissionBanner(
    title: String,
    description: String,
    isGranted: Boolean,
    onOpenSettings: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = if (isGranted) stringResource(R.string.permissions_granted)
                       else stringResource(R.string.permissions_denied),
                modifier = Modifier.width(80.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = title, modifier = Modifier.weight(1f))
            if (!isGranted) {
                Spacer(modifier = Modifier.width(8.dp))
                Button(onClick = onOpenSettings) {
                    Text(stringResource(R.string.permissions_open_settings))
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PermissionBannerGrantedPreview() {
    PermissionBanner(
        title = "Erişilebilirlik Servisi",
        description = "Ekran otomasyonu için gerekli",
        isGranted = true,
        onOpenSettings = {}
    )
}

@Preview(showBackground = true)
@Composable
private fun PermissionBannerDeniedPreview() {
    PermissionBanner(
        title = "Erişilebilirlik Servisi",
        description = "Ekran otomasyonu için gerekli",
        isGranted = false,
        onOpenSettings = {}
    )
}
