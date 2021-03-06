package com.chocomiruku.character_list_feature.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.chocomiruku.character_list_feature.domain.usecase.GetCharactersUseCase
import com.chocomiruku.core.domain.Character
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class CharactersListViewModel @Inject constructor(
    private val getCharactersUseCase: GetCharactersUseCase
) : ViewModel() {

    fun getCharactersFlow(searchQuery: String): Flow<PagingData<Character>> =
        getCharactersUseCase(searchQuery)
            .cachedIn(viewModelScope)
}