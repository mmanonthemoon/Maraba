package com.maraba.app.ui.screen.editor

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.maraba.app.R
import com.maraba.app.data.model.Condition

/**
 * ConditionPickerScreen — visual card list for selecting a condition type.
 * TODO: implement categorized card list
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConditionPickerScreen(
    onConditionSelected: (Condition) -> Unit,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier,
        topBar = { TopAppBar(title = { Text(stringResource(R.string.condition_picker_title)) }) }
    ) { innerPadding ->
        Text("TODO: ConditionPickerScreen", modifier = Modifier.padding(innerPadding))
    }
}
