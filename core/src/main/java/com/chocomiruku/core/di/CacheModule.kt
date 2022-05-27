package com.chocomiruku.core.di

import android.app.Application
import com.chocomiruku.core.data.cache.CharactersDatabase
import com.chocomiruku.core.data.cache.dao.CharactersDao
import com.chocomiruku.core.data.cache.dao.RemoteKeysDao
import com.chocomiruku.core.data.cache.getDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class CacheModule {
    @Singleton
    @Provides
    fun provideDatabaseInstance(application: Application): CharactersDatabase {
        return getDatabase(application.applicationContext)
    }

    @Singleton
    @Provides
    fun provideCharactersDao(database: CharactersDatabase): CharactersDao {
        return database.charactersDao()
    }

    @Singleton
    @Provides
    fun provideRemoteKeysDao(database: CharactersDatabase): RemoteKeysDao {
        return database.remoteKeysDao()
    }
}