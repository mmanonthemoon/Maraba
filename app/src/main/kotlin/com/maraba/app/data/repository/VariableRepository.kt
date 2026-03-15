package com.maraba.app.data.repository

import com.maraba.app.data.model.MarabaVariable
import kotlinx.coroutines.flow.Flow

interface VariableRepository {

    fun observeAll(): Flow<List<MarabaVariable>>

    fun observeUserDefined(): Flow<List<MarabaVariable>>

    suspend fun getByName(name: String): MarabaVariable?

    suspend fun getValue(name: String): String?

    suspend fun save(variable: MarabaVariable)

    suspend fun setValue(name: String, value: String)

    suspend fun delete(name: String)

    /** Initialize built-in variables on first launch */
    suspend fun initBuiltIns()
}
