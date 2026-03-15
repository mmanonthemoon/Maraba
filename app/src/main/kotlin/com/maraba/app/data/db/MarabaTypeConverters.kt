package com.maraba.app.data.db

import androidx.room.TypeConverter
import com.maraba.app.data.model.Action
import com.maraba.app.data.model.Condition
import com.maraba.app.data.model.Trigger
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

/**
 * Room TypeConverters for sealed class serialization.
 * Uses Kotlinx Serialization (not Gson/Moshi).
 */
class MarabaTypeConverters {

    private val json = Json {
        ignoreUnknownKeys = true
        encodeDefaults = true
        classDiscriminator = "type"
    }

    // ── Trigger List ─────────────────────────────────────────────────────

    @TypeConverter
    fun triggersToJson(triggers: List<Trigger>): String =
        json.encodeToString(triggers)

    @TypeConverter
    fun jsonToTriggers(value: String): List<Trigger> =
        json.decodeFromString(value)

    // ── Condition List ────────────────────────────────────────────────────

    @TypeConverter
    fun conditionsToJson(conditions: List<Condition>): String =
        json.encodeToString(conditions)

    @TypeConverter
    fun jsonToConditions(value: String): List<Condition> =
        json.decodeFromString(value)

    // ── Action List ───────────────────────────────────────────────────────

    @TypeConverter
    fun actionsToJson(actions: List<Action>): String =
        json.encodeToString(actions)

    @TypeConverter
    fun jsonToActions(value: String): List<Action> =
        json.decodeFromString(value)
}
