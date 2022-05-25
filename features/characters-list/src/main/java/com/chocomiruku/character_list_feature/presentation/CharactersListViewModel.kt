package com.chocomiruku.character_list_feature.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chocomiruku.character_list_feature.domain.usecase.GetCharactersUseCase
import com.chocomiruku.character_list_feature.domain.usecase.SearchCharactersUseCase
import com.chocomiruku.core.data.Resource
import com.chocomiruku.core.domain.Character
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.lang.Exception

class CharactersListViewModel(
    private val getCharactersUseCase: GetCharactersUseCase,
    private val searchCharactersUseCase: SearchCharactersUseCase
) : ViewModel() {

    private val _charactersStateFlow: MutableStateFlow<Resource<List<Character>>> =
        MutableStateFlow(Resource.Loading)

    val charactersStateFlow: StateFlow<Resource<List<Character>>> = _charactersStateFlow

    init {
        try {
            getCharacters()
        } catch (e: Exception) {
            e.message?.let { Log.e("qq", it) }
        }

    }

    private fun getCharacters() {
        viewModelScope.launch {
            getCharactersUseCase()
                .collect {
                    _charactersStateFlow.value = it
                }
        }
    }

    fun searchCharacters(query: String) {
        viewModelScope.launch {
            searchCharactersUseCase(query)
                .collect {
                    _charactersStateFlow.value = it
                }
        }
    }
}