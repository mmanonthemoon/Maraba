package com.maraba.app.data.repository

import com.maraba.app.data.db.dao.MacroDao
import com.maraba.app.data.db.entity.MacroEntity
import com.maraba.app.data.model.Macro
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import javax.inject.Inject
import javax.inject.Singleton

/**
 * MacroRepositoryImpl — concrete Room-backed implementation.
 * TODO: implement mapper functions (entity ↔ domain model)
 */
@Singleton
class MacroRepositoryImpl @Inject constructor(
    private val macroDao: MacroDao,
    private val json: Json
) : MacroRepository {

    override fun observeAll(): Flow<List<Macro>> =
        macroDao.observeAll().map { entities -> entities.map { it.toDomain(json) } }

    override fun observeEnabled(): Flow<List<Macro>> =
        macroDao.observeEnabled().map { entities -> entities.map { it.toDomain(json) } }

    override fun observeById(id: String): Flow<Macro?> =
        macroDao.observeById(id).map { it?.toDomain(json) }

    override suspend fun getById(id: String): Macro? =
        macroDao.getById(id)?.toDomain(json)

    override suspend fun save(macro: Macro) =
        macroDao.insert(macro.toEntity(json))

    override suspend fun delete(macroId: String) =
        macroDao.deleteById(macroId)

    override suspend fun setEnabled(macroId: String, enabled: Boolean) =
        macroDao.setEnabled(macroId, enabled)

    override suspend fun recordExecution(macroId: String, timestamp: Long) =
        macroDao.updateExecution(macroId, timestamp)

    override fun observeEnabledCount(): Flow<Int> =
        macroDao.observeEnabledCount()
}

// ── Mapper extensions ─────────────────────────────────────────────────────

private fun MacroEntity.toDomain(json: Json): Macro {
    // TODO: implement full mapper
    return Macro(
        id = id,
        name = name,
        description = description,
        isEnabled = isEnabled,
        triggers = json.decodeFromString(triggersJson),
        conditions = json.decodeFromString(conditionsJson),
        actions = json.decodeFromString(actionsJson),
        createdAt = createdAt,
        lastExecutedAt = lastExecutedAt,
        executionCount = executionCount,
        priority = priority,
        stopOnFailure = stopOnFailure,
        maxExecutionTimeMs = maxExecutionTimeMs
    )
}

private fun Macro.toEntity(json: Json): MacroEntity {
    // TODO: implement full mapper
    return MacroEntity(
        id = id,
        name = name,
        description = description,
        isEnabled = isEnabled,
        triggersJson = json.encodeToString(triggers),
        conditionsJson = json.encodeToString(conditions),
        actionsJson = json.encodeToString(actions),
        createdAt = createdAt,
        lastExecutedAt = lastExecutedAt,
        executionCount = executionCount,
        priority = priority,
        stopOnFailure = stopOnFailure,
        maxExecutionTimeMs = maxExecutionTimeMs
    )
}
