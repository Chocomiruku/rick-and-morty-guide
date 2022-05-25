package com.chocomiruku.character_details_feature.domain.usecase

import com.chocomiruku.character_details_feature.domain.repo.CharacterDetailsRepo
import com.chocomiruku.core.data.Resource
import com.chocomiruku.core.domain.Character
import kotlinx.coroutines.flow.Flow

class GetCharacterUseCase(
    private val repo: CharacterDetailsRepo
) {
    suspend operator fun invoke(characterId: Int): Flow<Resource<Character>> {
        return repo.getCharacter(characterId)
    }
}