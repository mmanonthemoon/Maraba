package com.maraba.app.data.repository

import com.maraba.app.data.model.Macro
import kotlinx.coroutines.flow.Flow

/**
 * MacroRepository interface.
 * ViewModel/UseCase hiçbir zaman DAO'ya doğrudan erişmez.
 */
interface MacroRepository {

    fun observeAll(): Flow<List<Macro>>

    fun observeEnabled(): Flow<List<Macro>>

    fun observeById(id: String): Flow<Macro?>

    suspend fun getById(id: String): Macro?

    suspend fun save(macro: Macro)

    suspend fun delete(macroId: String)

    suspend fun setEnabled(macroId: String, enabled: Boolean)

    suspend fun recordExecution(macroId: String, timestamp: Long)

    fun observeEnabledCount(): Flow<Int>
}
