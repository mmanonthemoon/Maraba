package com.maraba.app.data.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.maraba.app.data.db.entity.VariableEntity
import kotlinx.coroutines.flow.Flow

/**
 * VariableDao — Room DAO for variable management.
 */
@Dao
interface VariableDao {

    @Query("SELECT * FROM variables ORDER BY is_built_in DESC, name ASC")
    fun observeAll(): Flow<List<VariableEntity>>

    @Query("SELECT * FROM variables WHERE is_built_in = 0 ORDER BY name ASC")
    fun observeUserDefined(): Flow<List<VariableEntity>>

    @Query("SELECT * FROM variables WHERE name = :name")
    suspend fun getByName(name: String): VariableEntity?

    @Query("SELECT value FROM variables WHERE name = :name")
    suspend fun getValue(name: String): String?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(variable: VariableEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(variables: List<VariableEntity>)

    @Update
    suspend fun update(variable: VariableEntity)

    @Query("UPDATE variables SET value = :value WHERE name = :name")
    suspend fun setValue(name: String, value: String)

    @Delete
    suspend fun delete(variable: VariableEntity)

    @Query("DELETE FROM variables WHERE name = :name AND is_built_in = 0")
    suspend fun deleteUserVariable(name: String)
}
