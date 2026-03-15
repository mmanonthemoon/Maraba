package com.maraba.app.di

import com.maraba.app.data.repository.MacroRepository
import com.maraba.app.data.repository.MacroRepositoryImpl
import com.maraba.app.data.repository.VariableRepository
import com.maraba.app.data.repository.VariableRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

/**
 * AppModule — provides application-level dependencies.
 */
@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {

    @Binds
    @Singleton
    abstract fun bindMacroRepository(impl: MacroRepositoryImpl): MacroRepository

    @Binds
    @Singleton
    abstract fun bindVariableRepository(impl: VariableRepositoryImpl): VariableRepository

    companion object {

        @Provides
        @Singleton
        fun provideJson(): Json = Json {
            ignoreUnknownKeys = true
            encodeDefaults = true
            classDiscriminator = "type"
        }

        @Provides
        @Singleton
        fun provideOkHttpClient(): OkHttpClient = OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()
    }
}
