package com.maraba.app.data.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.maraba.app.data.db.entity.MacroEntity
import kotlinx.coroutines.flow.Flow

/**
 * MacroDao — Room DAO for macro CRUD operations.
 * Flow-returning queries are reactive; suspend funs are one-shot.
 */
@Dao
interface MacroDao {

    @Query("SELECT * FROM macros ORDER BY priority DESC, created_at DESC")
    fun observeAll(): Flow<List<MacroEntity>>

    @Query("SELECT * FROM macros WHERE is_enabled = 1 ORDER BY priority DESC")
    fun observeEnabled(): Flow<List<MacroEntity>>

    @Query("SELECT * FROM macros WHERE id = :id")
    fun observeById(id: String): Flow<MacroEntity?>

    @Query("SELECT * FROM macros WHERE id = :id")
    suspend fun getById(id: String): MacroEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(macro: MacroEntity)

    @Update
    suspend fun update(macro: MacroEntity)

    @Delete
    suspend fun delete(macro: MacroEntity)

    @Query("DELETE FROM macros WHERE id = :id")
    suspend fun deleteById(id: String)

    @Query("UPDATE macros SET is_enabled = :enabled WHERE id = :id")
    suspend fun setEnabled(id: String, enabled: Boolean)

    @Query("UPDATE macros SET last_executed_at = :timestamp, execution_count = execution_count + 1 WHERE id = :id")
    suspend fun updateExecution(id: String, timestamp: Long)

    @Query("SELECT COUNT(*) FROM macros WHERE is_enabled = 1")
    fun observeEnabledCount(): Flow<Int>
}
