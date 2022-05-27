package com.chocomiruku.character_list_feature.di

import com.chocomiruku.character_list_feature.data.CharactersListRepoImpl
import com.chocomiruku.character_list_feature.domain.repo.CharactersListRepo
import com.chocomiruku.character_list_feature.domain.usecase.GetCharactersUseCase
import com.chocomiruku.core.data.api.CharactersApi
import com.chocomiruku.core.data.cache.CharactersDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(ViewModelComponent::class)
class CharacterListDomainModule {
    @Provides
    fun provideUseCase(repo: CharactersListRepo): GetCharactersUseCase {
        return GetCharactersUseCase(repo)
    }
}

@Module
@InstallIn(SingletonComponent::class)
class CharacterListDataModule {
    @Singleton
    @Provides
    fun provideCharacterListRepo(
        service: CharactersApi,
        database: CharactersDatabase
    ): CharactersListRepo {
        return CharactersListRepoImpl(service, database)
    }
}