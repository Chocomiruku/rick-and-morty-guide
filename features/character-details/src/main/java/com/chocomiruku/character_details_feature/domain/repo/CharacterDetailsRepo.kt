package com.chocomiruku.character_details_feature.domain.repo

import com.chocomiruku.core.data.Resource
import com.chocomiruku.core.domain.Character
import kotlinx.coroutines.flow.Flow

interface CharacterDetailsRepo {
    suspend fun getCharacter(characterId: Int): Flow<Resource<Character>>
}