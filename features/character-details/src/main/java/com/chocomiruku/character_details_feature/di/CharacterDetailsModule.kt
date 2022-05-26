package com.chocomiruku.character_details_feature.di

import com.chocomiruku.character_details_feature.data.CharacterDetailsRepoImpl
import com.chocomiruku.character_details_feature.domain.repo.CharacterDetailsRepo
import com.chocomiruku.character_details_feature.domain.usecase.GetCharacterUseCase
import com.chocomiruku.core.data.cache.dao.CharactersDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(ViewModelComponent::class)
class CharacterDetailsDomainModule {
    @Provides
    fun provideUseCase(repo: CharacterDetailsRepo): GetCharacterUseCase {
        return GetCharacterUseCase(repo)
    }
}

@Module
@InstallIn(SingletonComponent::class)
class CharacterDetailsDataModule {
    @Singleton
    @Provides
    fun provideCharacterDetailsRepo(
        charactersDao: CharactersDao
    ): CharacterDetailsRepo {
        return CharacterDetailsRepoImpl(charactersDao)
    }
}