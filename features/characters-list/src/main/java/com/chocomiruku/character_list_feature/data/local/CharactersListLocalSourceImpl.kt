package com.chocomiruku.character_list_feature.data.local

import com.chocomiruku.core.data.data_sources.local.CharacterEntity
import com.chocomiruku.core.data.data_sources.local.CharactersDao
import kotlinx.coroutines.flow.Flow

class CharactersListLocalSourceImpl(private val charactersDao: CharactersDao) :
    CharactersListLocalSource {
    override suspend fun getCharacters(): List<CharacterEntity> {
        return charactersDao.getAll()
    }

    override suspend fun saveCharacters(characters: List<CharacterEntity>) {
        charactersDao.insertAll(characters)
    }

    override suspend fun searchCharacters(query: String): Flow<List<CharacterEntity>> {
        return charactersDao.searchByName(query)
    }
}