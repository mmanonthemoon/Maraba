package com.maraba.app.ui

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * MacroEditorTest — Compose UI instrumentation test for macro editor.
 * TODO: implement tests for trigger/condition/action picker navigation
 */
@RunWith(AndroidJUnit4::class)
class MacroEditorTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun macroEditor_navigatesFromHome() {
        // TODO: implement — tap FAB, verify editor opens
    }

    @Test
    fun macroEditor_savesWithValidData() {
        // TODO: implement — fill name, add trigger, save
    }
}
