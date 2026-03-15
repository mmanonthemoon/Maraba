package com.maraba.app.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.maraba.app.ui.screen.home.HomeScreen
import com.maraba.app.ui.screen.log.LogScreen
import com.maraba.app.ui.screen.permissions.PermissionsScreen
import com.maraba.app.ui.screen.settings.SettingsScreen
import com.maraba.app.ui.screen.variables.VariablesScreen

/**
 * MarabaNavHost — Compose Navigation (ADR-007).
 * TODO: add MacroEditorScreen, MacroDetailScreen, picker screens
 */
@Composable
fun MarabaNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = NavRoutes.HOME,
        modifier = modifier
    ) {
        composable(NavRoutes.HOME) {
            HomeScreen(onCreateMacro = { navController.navigate(NavRoutes.MACRO_EDITOR_NEW) })
        }
        composable(NavRoutes.MACRO_EDITOR_NEW) {
            // TODO: MacroEditorScreen
        }
        composable(NavRoutes.LOG) {
            LogScreen()
        }
        composable(NavRoutes.VARIABLES) {
            VariablesScreen()
        }
        composable(NavRoutes.SETTINGS) {
            SettingsScreen()
        }
        composable(NavRoutes.PERMISSIONS) {
            PermissionsScreen()
        }
    }
}

object NavRoutes {
    const val HOME = "home"
    const val MACRO_EDITOR_NEW = "macro_editor/new"
    const val MACRO_EDITOR_EDIT = "macro_editor/{macroId}"
    const val MACRO_DETAIL = "macro_detail/{macroId}"
    const val LOG = "log"
    const val VARIABLES = "variables"
    const val SETTINGS = "settings"
    const val PERMISSIONS = "permissions"
}
