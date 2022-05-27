package com.chocomiruku.core.di

import com.chocomiruku.core.data.api.CharactersApi
import com.chocomiruku.core.data.cache.CharactersDatabase
import com.chocomiruku.core.data.paging.CharactersRemoteMediator
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class PagingModule {
    @Singleton
    @Provides
    fun provideRemoteMediator(
        service: CharactersApi,
        database: CharactersDatabase
    ): CharactersRemoteMediator {
        return CharactersRemoteMediator(service, database)
    }
}