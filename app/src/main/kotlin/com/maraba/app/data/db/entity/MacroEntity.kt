package com.maraba.app.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Room entity for Macro domain model.
 * Trigger/Condition/Action lists stored as JSON via TypeConverters.
 */
@Entity(tableName = "macros")
data class MacroEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: String,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "description")
    val description: String,

    @ColumnInfo(name = "is_enabled")
    val isEnabled: Boolean,

    @ColumnInfo(name = "triggers_json")
    val triggersJson: String,       // JSON: List<Trigger>

    @ColumnInfo(name = "conditions_json")
    val conditionsJson: String,     // JSON: List<Condition>

    @ColumnInfo(name = "actions_json")
    val actionsJson: String,        // JSON: List<Action>

    @ColumnInfo(name = "created_at")
    val createdAt: Long,

    @ColumnInfo(name = "last_executed_at")
    val lastExecutedAt: Long?,

    @ColumnInfo(name = "execution_count")
    val executionCount: Int,

    @ColumnInfo(name = "priority")
    val priority: Int,

    @ColumnInfo(name = "stop_on_failure")
    val stopOnFailure: Boolean,

    @ColumnInfo(name = "max_execution_time_ms")
    val maxExecutionTimeMs: Long
)
