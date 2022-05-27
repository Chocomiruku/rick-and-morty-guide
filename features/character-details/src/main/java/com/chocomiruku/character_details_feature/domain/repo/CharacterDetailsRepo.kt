package com.chocomiruku.character_details_feature.domain.repo

import com.chocomiruku.core.domain.Character
import kotlinx.coroutines.flow.Flow

interface CharacterDetailsRepo {
    fun getCharacter(characterId: Int): Flow<Character>
}