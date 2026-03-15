package com.maraba.app.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.maraba.app.R
import com.maraba.app.data.model.Macro

/**
 * MacroCard — reusable macro list item (ui-developer ADR).
 * Displays: name, trigger summary, last run, enabled toggle.
 * No ViewModel dependency — pure composable.
 */
@Composable
fun MacroCard(
    macro: Macro,
    onToggle: (Boolean) -> Unit,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 4.dp)
            .clickable(onClick = onClick)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(text = macro.name)
                Text(
                    text = if (macro.lastExecutedAt != null)
                        stringResource(R.string.home_last_run, macro.lastExecutedAt.toString())
                    else
                        stringResource(R.string.home_never_run)
                )
            }
            Switch(
                checked = macro.isEnabled,
                onCheckedChange = onToggle
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun MacroCardPreview() {
    // TODO: implement preview with sample data
}
