package com.maraba.app.ui.screen.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.maraba.app.R
import com.maraba.app.ui.component.MacroCard

/**
 * HomeScreen — macro list (ADR-007, ui-developer).
 * TODO: implement empty state illustration, macro toggle interaction
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onCreateMacro: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(title = { Text(stringResource(R.string.home_title)) })
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onCreateMacro) {
                Icon(Icons.Default.Add, contentDescription = stringResource(R.string.home_add_macro))
            }
        }
    ) { innerPadding ->
        if (uiState.macros.isEmpty()) {
            // TODO: replace with EmptyStateView composable
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(stringResource(R.string.home_empty_title))
                    Text(stringResource(R.string.home_empty_description))
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(innerPadding)
            ) {
                items(uiState.macros, key = { it.id }) { macro ->
                    MacroCard(
                        macro = macro,
                        onToggle = { enabled -> viewModel.toggleMacro(macro.id, enabled) },
                        onClick = { /* TODO: navigate to detail */ }
                    )
                }
            }
        }
    }
}
