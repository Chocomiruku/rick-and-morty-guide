package com.chocomiruku.character_list_feature.presentation

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.chocomiruku.character_list_feature.data.CharactersListRepoImpl
import com.chocomiruku.character_list_feature.data.local.CharactersListLocalSourceImpl
import com.chocomiruku.character_list_feature.data.remote.CharactersListRemoteSourceImpl
import com.chocomiruku.character_list_feature.domain.usecase.GetCharactersUseCase
import com.chocomiruku.character_list_feature.domain.usecase.SearchCharactersUseCase
import com.chocomiruku.core.data.data_sources.local.getDatabase
import com.chocomiruku.core.data.data_sources.remote.CharactersApi

class CharactersListViewModelFactory(
    application: Application
) :
    ViewModelProvider.Factory {

    private val database = getDatabase(application)
    private val localSource = CharactersListLocalSourceImpl(database.charactersDao)
    private val remoteSource = CharactersListRemoteSourceImpl(CharactersApi)
    private val charactersRepo = CharactersListRepoImpl(remoteSource, localSource)
    private val getCharactersUseCase = GetCharactersUseCase(charactersRepo)
    private val searchCharactersUseCase = SearchCharactersUseCase(charactersRepo)

    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CharactersListViewModel::class.java)) {
            return CharactersListViewModel(
                getCharactersUseCase,
                searchCharactersUseCase
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}