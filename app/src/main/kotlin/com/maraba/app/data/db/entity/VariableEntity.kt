package com.maraba.app.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Room entity for MarabaVariable.
 * Built-in system variables and user-defined variables.
 */
@Entity(tableName = "variables")
data class VariableEntity(
    @PrimaryKey
    @ColumnInfo(name = "name")
    val name: String,               // %BATTERY, %TIME, kullanıcı tanımlı

    @ColumnInfo(name = "value")
    val value: String,

    @ColumnInfo(name = "type")
    val type: String,               // VariableType enum name

    @ColumnInfo(name = "is_built_in")
    val isBuiltIn: Boolean
)
