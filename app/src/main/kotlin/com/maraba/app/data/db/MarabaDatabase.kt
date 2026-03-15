package com.maraba.app.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.maraba.app.data.db.dao.ExecutionLogDao
import com.maraba.app.data.db.dao.MacroDao
import com.maraba.app.data.db.dao.VariableDao
import com.maraba.app.data.db.entity.ExecutionLogEntity
import com.maraba.app.data.db.entity.MacroEntity
import com.maraba.app.data.db.entity.VariableEntity

/**
 * MarabaDatabase — Room database definition.
 *
 * Schema değişikliği olduğunda:
 * 1. version numarasını artır
 * 2. Migration(from, to) sınıfı ekle (DatabaseModule'de ekle)
 * 3. Hiçbir zaman fallbackToDestructiveMigration kullanma (data loss!)
 */
@Database(
    entities = [
        MacroEntity::class,
        VariableEntity::class,
        ExecutionLogEntity::class
    ],
    version = 1,
    exportSchema = true
)
@TypeConverters(MarabaTypeConverters::class)
abstract class MarabaDatabase : RoomDatabase() {

    abstract fun macroDao(): MacroDao
    abstract fun variableDao(): VariableDao
    abstract fun executionLogDao(): ExecutionLogDao

    companion object {
        const val DATABASE_NAME = "maraba.db"
    }
}
