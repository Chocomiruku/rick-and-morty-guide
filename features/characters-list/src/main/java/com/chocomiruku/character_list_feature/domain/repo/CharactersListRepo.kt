package com.chocomiruku.character_list_feature.domain.repo

import androidx.paging.PagingData
import com.chocomiruku.core.domain.Character
import kotlinx.coroutines.flow.Flow

interface CharactersListRepo {
    fun getCharacters(searchQuery: String): Flow<PagingData<Character>>
}