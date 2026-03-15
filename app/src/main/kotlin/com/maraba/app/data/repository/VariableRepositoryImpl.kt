package com.maraba.app.data.repository

import com.maraba.app.data.db.dao.VariableDao
import com.maraba.app.data.db.entity.VariableEntity
import com.maraba.app.data.model.BuiltInVariables
import com.maraba.app.data.model.MarabaVariable
import com.maraba.app.data.model.VariableType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

/**
 * VariableRepositoryImpl — Room-backed implementation.
 * TODO: implement all methods
 */
@Singleton
class VariableRepositoryImpl @Inject constructor(
    private val variableDao: VariableDao
) : VariableRepository {

    override fun observeAll(): Flow<List<MarabaVariable>> =
        variableDao.observeAll().map { entities -> entities.map { it.toDomain() } }

    override fun observeUserDefined(): Flow<List<MarabaVariable>> =
        variableDao.observeUserDefined().map { entities -> entities.map { it.toDomain() } }

    override suspend fun getByName(name: String): MarabaVariable? =
        variableDao.getByName(name)?.toDomain()

    override suspend fun getValue(name: String): String? =
        variableDao.getValue(name)

    override suspend fun save(variable: MarabaVariable) =
        variableDao.insert(variable.toEntity())

    override suspend fun setValue(name: String, value: String) =
        variableDao.setValue(name, value)

    override suspend fun delete(name: String) =
        variableDao.deleteUserVariable(name)

    override suspend fun initBuiltIns() {
        // TODO: sadece eksik built-in'leri ekle, mevcut değerlere dokunma
        val builtIns = BuiltInVariables.defaultList()
        builtIns.forEach { variable ->
            if (variableDao.getByName(variable.name) == null) {
                variableDao.insert(variable.toEntity())
            }
        }
    }
}

private fun VariableEntity.toDomain(): MarabaVariable = MarabaVariable(
    name = name,
    value = value,
    type = VariableType.valueOf(type),
    isBuiltIn = isBuiltIn
)

private fun MarabaVariable.toEntity(): VariableEntity = VariableEntity(
    name = name,
    value = value,
    type = type.name,
    isBuiltIn = isBuiltIn
)
