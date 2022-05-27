package com.chocomiruku.character_details_feature.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.chocomiruku.character_details_feature.domain.usecase.GetCharacterUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CharacterDetailsViewModel @Inject constructor(
    private val getCharacterUseCase: GetCharacterUseCase
) : ViewModel() {

    fun getCharacter(characterId: Int) = getCharacterUseCase(characterId).asLiveData()
}