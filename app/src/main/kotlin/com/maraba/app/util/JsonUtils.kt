package com.maraba.app.util

import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import timber.log.Timber

/**
 * JsonUtils — JSON helper functions for ParseJsonAction and data operations.
 */
object JsonUtils {

    /**
     * Extract a value from JSON using a simple dot-notation path.
     * Example: "data.user.name" on {"data": {"user": {"name": "Ali"}}}
     * TODO: implement full JSONPath support ($.data[0].name)
     */
    fun extractValue(jsonString: String, path: String): String? {
        return try {
            val json = Json.parseToJsonElement(jsonString)
            val parts = path.removePrefix("$.").split(".")
            var current: JsonElement = json
            for (part in parts) {
                current = current.jsonObject[part] ?: return null
            }
            current.jsonPrimitive.content
        } catch (e: Exception) {
            Timber.w(e, "JsonUtils: failed to extract path '$path'")
            null
        }
    }
}
