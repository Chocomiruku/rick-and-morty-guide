package com.chocomiruku.character_details_feature.presentation

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.chocomiruku.character_details_feature.data.CharacterDetailsRepoImpl
import com.chocomiruku.character_details_feature.data.local.CharacterDetailsLocalSourceImpl
import com.chocomiruku.character_details_feature.domain.usecase.GetCharacterUseCase
import com.chocomiruku.core.data.data_sources.local.getDatabase

class CharacterDetailsViewModelFactory(
    private val characterId: Int,
    application: Application
) :
    ViewModelProvider.Factory {

    private val database = getDatabase(application)
    private val localSource = CharacterDetailsLocalSourceImpl(database.charactersDao)
    private val charactersRepo = CharacterDetailsRepoImpl(localSource)
    private val getCharacterUseCase = GetCharacterUseCase(charactersRepo)

    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CharacterDetailsViewModel::class.java)) {
            return CharacterDetailsViewModel(
                characterId,
                getCharacterUseCase
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}