package com.maraba.app.di

import android.content.Context
import androidx.room.Room
import com.maraba.app.data.db.MarabaDatabase
import com.maraba.app.data.db.dao.ExecutionLogDao
import com.maraba.app.data.db.dao.MacroDao
import com.maraba.app.data.db.dao.VariableDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * DatabaseModule — provides Room database and DAOs.
 * Schema migrations must be added here (never use fallbackToDestructiveMigration).
 */
@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideMarabaDatabase(
        @ApplicationContext context: Context
    ): MarabaDatabase = Room.databaseBuilder(
        context,
        MarabaDatabase::class.java,
        MarabaDatabase.DATABASE_NAME
    )
        // TODO: add migrations here as schema evolves
        // .addMigrations(MIGRATION_1_2)
        .build()

    @Provides
    fun provideMacroDao(database: MarabaDatabase): MacroDao = database.macroDao()

    @Provides
    fun provideVariableDao(database: MarabaDatabase): VariableDao = database.variableDao()

    @Provides
    fun provideExecutionLogDao(database: MarabaDatabase): ExecutionLogDao = database.executionLogDao()
}
