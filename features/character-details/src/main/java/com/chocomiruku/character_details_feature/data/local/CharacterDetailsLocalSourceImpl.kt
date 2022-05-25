package com.chocomiruku.character_details_feature.data.local

import com.chocomiruku.core.data.data_sources.local.CharacterEntity
import com.chocomiruku.core.data.data_sources.local.CharactersDao

class CharacterDetailsLocalSourceImpl(private val charactersDao: CharactersDao) : CharacterDetailsLocalSource {
    override suspend fun getCharacter(characterId: Int): CharacterEntity {
        return charactersDao.getCharacter(characterId)
    }
}