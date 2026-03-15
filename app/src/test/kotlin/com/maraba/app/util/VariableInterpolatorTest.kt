package com.maraba.app.util

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

/**
 * VariableInterpolatorTest — unit tests for %VARIABLE interpolation.
 */
class VariableInterpolatorTest {

    private lateinit var interpolator: VariableInterpolator

    @BeforeEach
    fun setUp() {
        interpolator = VariableInterpolator()
    }

    @Test
    fun `text without variables is unchanged`() {
        val result = interpolator.interpolate("Hello World", emptyMap())
        assertEquals("Hello World", result)
    }

    @Test
    fun `single variable is replaced`() {
        val vars = mapOf("%BATTERY" to "87")
        val result = interpolator.interpolate("Pil: %BATTERY%", vars)
        assertEquals("Pil: 87", result)
    }

    @Test
    fun `multiple variables are replaced`() {
        val vars = mapOf("%TIME" to "14:35", "%BATTERY" to "87")
        val result = interpolator.interpolate("%TIME — Pil: %BATTERY", vars)
        assertEquals("14:35 — Pil: 87", result)
    }

    @Test
    fun `unknown variable stays unchanged`() {
        val result = interpolator.interpolate("Test: %UNKNOWN", emptyMap())
        assertEquals("Test: %UNKNOWN", result)
    }

    @Test
    fun `empty string returns empty string`() {
        val result = interpolator.interpolate("", emptyMap())
        assertEquals("", result)
    }
}
