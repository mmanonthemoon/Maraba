package com.maraba.app.data.repository

import com.maraba.app.data.db.dao.ExecutionLogDao
import com.maraba.app.data.db.entity.ActionLogEntry
import com.maraba.app.data.db.entity.ExecutionLogEntity
import com.maraba.app.data.db.entity.ExecutionStatus
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

/**
 * LogRepository — execution log management.
 * Sensitive data (SMS content, notification text) is never written to logs.
 */
@Singleton
class LogRepository @Inject constructor(
    private val executionLogDao: ExecutionLogDao
) {

    fun observeRecent(limit: Int = 100): Flow<List<ExecutionLogEntity>> =
        executionLogDao.observeRecent(limit)

    fun observeForMacro(macroId: String): Flow<List<ExecutionLogEntity>> =
        executionLogDao.observeForMacro(macroId)

    suspend fun log(
        macroId: String,
        macroName: String,
        triggeredAt: Long,
        completedAt: Long?,
        status: ExecutionStatus,
        actionResults: List<ActionLogEntry>,
        errorMessage: String?
    ): Long {
        // TODO: serialize actionResults to JSON
        val entity = ExecutionLogEntity(
            macroId = macroId,
            macroName = macroName,
            triggeredAt = triggeredAt,
            completedAt = completedAt,
            status = status.name,
            actionResultsJson = "[]", // TODO: serialize actionResults
            errorMessage = errorMessage
        )
        return executionLogDao.insert(entity)
    }

    suspend fun clearAll() = executionLogDao.deleteAll()

    suspend fun clearOlderThan(timestampMs: Long) =
        executionLogDao.deleteOlderThan(timestampMs)

    fun observeTotalCount(): Flow<Int> = executionLogDao.observeTotalCount()
}
