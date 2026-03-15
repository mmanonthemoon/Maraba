package com.maraba.app.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

/**
 * Room entity for macro execution log.
 * Each macro run creates one ExecutionLogEntity.
 * Sensitive data (SMS content, notification text) is NOT stored — only match results.
 */
@Entity(
    tableName = "execution_logs",
    indices = [Index(value = ["macro_id"])]
)
data class ExecutionLogEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Long = 0,

    @ColumnInfo(name = "macro_id")
    val macroId: String,

    @ColumnInfo(name = "macro_name")
    val macroName: String,

    @ColumnInfo(name = "triggered_at")
    val triggeredAt: Long,

    @ColumnInfo(name = "completed_at")
    val completedAt: Long?,

    @ColumnInfo(name = "status")
    val status: String,             // ExecutionStatus enum name

    @ColumnInfo(name = "action_results_json")
    val actionResultsJson: String,  // JSON: List<ActionLogEntry>

    @ColumnInfo(name = "error_message")
    val errorMessage: String?
)

/** Execution status enum — logged per macro run */
enum class ExecutionStatus {
    SUCCESS,
    PARTIAL,
    FAILED,
    SKIPPED    // koşul sağlanmadı
}

/** Per-action log entry (serialized to JSON in actionResultsJson) */
data class ActionLogEntry(
    val actionId: String,
    val actionType: String,
    val success: Boolean,
    val durationMs: Long,
    val failureReason: String?
)
