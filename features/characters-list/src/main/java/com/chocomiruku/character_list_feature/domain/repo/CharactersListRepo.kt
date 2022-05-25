package com.chocomiruku.character_list_feature.domain.repo

import com.chocomiruku.core.data.Resource
import com.chocomiruku.core.domain.Character
import kotlinx.coroutines.flow.Flow

interface CharactersListRepo {
    suspend fun getCharacters(): Flow<Resource<List<Character>>>
    suspend fun searchCharacters(query: String): Flow<List<Character>>
}