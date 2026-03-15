package com.maraba.app.ui.screen.editor

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.maraba.app.R
import com.maraba.app.data.model.Trigger

/**
 * TriggerPickerScreen — visual card list for selecting a trigger type.
 * TODO: implement categorized card list with search
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TriggerPickerScreen(
    onTriggerSelected: (Trigger) -> Unit,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier,
        topBar = { TopAppBar(title = { Text(stringResource(R.string.trigger_picker_title)) }) }
    ) { innerPadding ->
        // TODO: categorized trigger list (time, app, notification, etc.)
        Text("TODO: TriggerPickerScreen", modifier = Modifier.padding(innerPadding))
    }
}
