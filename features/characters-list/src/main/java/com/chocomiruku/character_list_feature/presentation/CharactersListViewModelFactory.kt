package com.chocomiruku.character_list_feature.presentation

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.chocomiruku.character_list_feature.data.CharactersListRepoImpl
import com.chocomiruku.character_list_feature.domain.usecase.GetCharactersUseCase
import com.chocomiruku.core.data.api.CharactersApi
import com.chocomiruku.core.data.cache.getDatabase

class CharactersListViewModelFactory(
    application: Application
) :
    ViewModelProvider.Factory {

    private val database = getDatabase(application)
    private val charactersRepo = CharactersListRepoImpl(CharactersApi.retrofitService, database)
    private val getCharactersUseCase = GetCharactersUseCase(charactersRepo)

    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CharactersListViewModel::class.java)) {
            return CharactersListViewModel(
                getCharactersUseCase
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}