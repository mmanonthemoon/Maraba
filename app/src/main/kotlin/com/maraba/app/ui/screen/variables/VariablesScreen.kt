package com.maraba.app.ui.screen.variables

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
 * VariablesScreen — manage built-in and user-defined variables (ADR-007).
 * TODO: implement variable list, add/edit/delete user variables
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VariablesScreen(
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier,
        topBar = { TopAppBar(title = { Text(stringResource(R.string.variables_title)) }) }
    ) { innerPadding ->
        // TODO: built-in variables section, user variables section, FAB to add
        Text("TODO: VariablesScreen", modifier = Modifier.padding(innerPadding))
    }
}
