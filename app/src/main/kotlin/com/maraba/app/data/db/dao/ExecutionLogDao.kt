package com.maraba.app.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.maraba.app.data.db.entity.ExecutionLogEntity
import kotlinx.coroutines.flow.Flow

/**
 * ExecutionLogDao — Room DAO for execution history.
 */
@Dao
interface ExecutionLogDao {

    @Query("SELECT * FROM execution_logs ORDER BY triggered_at DESC LIMIT :limit")
    fun observeRecent(limit: Int = 100): Flow<List<ExecutionLogEntity>>

    @Query("SELECT * FROM execution_logs WHERE macro_id = :macroId ORDER BY triggered_at DESC")
    fun observeForMacro(macroId: String): Flow<List<ExecutionLogEntity>>

    @Query("SELECT * FROM execution_logs WHERE id = :id")
    suspend fun getById(id: Long): ExecutionLogEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(log: ExecutionLogEntity): Long

    @Query("DELETE FROM execution_logs WHERE triggered_at < :beforeTimestamp")
    suspend fun deleteOlderThan(beforeTimestamp: Long)

    @Query("DELETE FROM execution_logs")
    suspend fun deleteAll()

    @Query("SELECT COUNT(*) FROM execution_logs WHERE macro_id = :macroId AND status = 'SUCCESS'")
    suspend fun getSuccessCount(macroId: String): Int

    @Query("SELECT COUNT(*) FROM execution_logs")
    fun observeTotalCount(): Flow<Int>
}
