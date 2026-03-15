package com.maraba.app.ui.screen.editor

import androidx.compose.foundation.layout.Column
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

/**
 * MacroEditorScreen — create or edit a macro (ADR-007).
 * TODO: implement full editor with trigger/condition/action pickers
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MacroEditorScreen(
    macroId: String? = null,
    onSave: () -> Unit,
    onDiscard: () -> Unit,
    modifier: Modifier = Modifier
) {
    val title = if (macroId == null) stringResource(R.string.editor_title_new)
                else stringResource(R.string.editor_title_edit)

    Scaffold(
        modifier = modifier,
        topBar = { TopAppBar(title = { Text(title) }) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            // TODO: MacroNameField
            // TODO: TriggerListSection
            // TODO: ConditionListSection
            // TODO: ActionListSection
            // TODO: SaveButton
            Text("TODO: MacroEditorScreen")
        }
    }
}
