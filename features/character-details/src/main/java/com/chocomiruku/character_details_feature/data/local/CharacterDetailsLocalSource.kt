package com.chocomiruku.character_details_feature.data.local

import com.chocomiruku.core.data.data_sources.local.CharacterEntity

interface CharacterDetailsLocalSource {
    suspend fun getCharacter(characterId: Int): CharacterEntity
}