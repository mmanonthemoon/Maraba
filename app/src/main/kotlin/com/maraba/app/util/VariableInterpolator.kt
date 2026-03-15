package com.maraba.app.util

import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

/**
 * VariableInterpolator — replaces %VARIABLE placeholders in strings.
 * Used by ActionHandlers before executing actions with text parameters.
 *
 * Variable names: uppercase + underscore, % prefix.
 * Example: "Pil: %BATTERY%" → "Pil: 87"
 */
@Singleton
class VariableInterpolator @Inject constructor() {

    private val variablePattern = Regex("%[A-Z_][A-Z0-9_]*%?")

    /**
     * Replace all %VARIABLE occurrences with their current values.
     * @param text the template string
     * @param variables map of variable name → value
     * @return interpolated string
     */
    fun interpolate(text: String, variables: Map<String, String>): String {
        if (!text.contains('%')) return text

        var result = text
        val matches = variablePattern.findAll(text)
        matches.forEach { match ->
            val varName = match.value.trimEnd('%')  // handle both %VAR and %VAR%
            val value = variables[varName] ?: variables["$varName%"]
            if (value != null) {
                result = result.replace(match.value, value)
            } else {
                Timber.d("VariableInterpolator: unresolved variable ${match.value}")
            }
        }
        return result
    }
}
