package com.chocomiruku.character_details_feature.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chocomiruku.character_details_feature.domain.usecase.GetCharacterUseCase
import com.chocomiruku.core.data.Resource
import com.chocomiruku.core.domain.Character
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CharacterDetailsViewModel(
    private val characterId: Int,
    private val getCharacterUseCase: GetCharacterUseCase
) : ViewModel() {

    private val _characterDetailsStateFlow: MutableStateFlow<Resource<Character>> =
        MutableStateFlow(Resource.Loading)

    val characterDetailsStateFlow: StateFlow<Resource<Character>> = _characterDetailsStateFlow

    init {
        getCharacter()
    }

    private fun getCharacter() {
        viewModelScope.launch {
            getCharacterUseCase(characterId)
                .collect {
                    _characterDetailsStateFlow.value = it
                }
        }
    }
}