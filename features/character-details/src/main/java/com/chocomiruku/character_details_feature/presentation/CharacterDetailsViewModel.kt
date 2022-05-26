package com.chocomiruku.character_details_feature.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.chocomiruku.character_details_feature.domain.usecase.GetCharacterUseCase
import com.chocomiruku.core.domain.Character
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharacterDetailsViewModel @Inject constructor(
    private val getCharacterUseCase: GetCharacterUseCase
) : ViewModel() {

    fun getCharacter(characterId: Int) = getCharacterUseCase(characterId).asLiveData()
}