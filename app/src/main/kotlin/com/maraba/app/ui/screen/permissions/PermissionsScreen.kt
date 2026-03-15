package com.maraba.app.ui.screen.permissions

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
 * PermissionsScreen — permission guide with explanations (ADR-007, ADR-008).
 * Shows granted/required status for: Accessibility, Notification Access, Location.
 * TODO: implement PermissionBanner for each permission, live status checking
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PermissionsScreen(
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier,
        topBar = { TopAppBar(title = { Text(stringResource(R.string.permissions_title)) }) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            // TODO: PermissionBanner for Accessibility Service
            // TODO: PermissionBanner for Notification Access
            // TODO: PermissionBanner for Location
            // TODO: PermissionBanner for Battery Optimization
            Text("TODO: PermissionsScreen")
        }
    }
}
