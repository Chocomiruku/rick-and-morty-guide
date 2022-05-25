package com.chocomiruku.character_list_feature.data.local

import com.chocomiruku.core.data.data_sources.local.CharacterEntity
import kotlinx.coroutines.flow.Flow

interface CharactersListLocalSource {
    suspend fun getCharacters(): List<CharacterEntity>
    suspend fun saveCharacters(characters: List<CharacterEntity>)
    suspend fun searchCharacters(query: String): Flow<List<CharacterEntity>>
}