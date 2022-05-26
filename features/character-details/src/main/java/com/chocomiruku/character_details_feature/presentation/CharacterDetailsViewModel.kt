package com.chocomiruku.character_details_feature.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.chocomiruku.character_details_feature.domain.usecase.GetCharacterUseCase
import com.chocomiruku.core.domain.Character
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CharacterDetailsViewModel(
    private val characterId: Int,
    private val getCharacterUseCase: GetCharacterUseCase
) : ViewModel() {

    val character = getCharacterUseCase(characterId).asLiveData()
}