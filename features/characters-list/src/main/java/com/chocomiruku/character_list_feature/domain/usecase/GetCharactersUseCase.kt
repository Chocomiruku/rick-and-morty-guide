package com.chocomiruku.character_list_feature.domain.usecase

import com.chocomiruku.character_list_feature.domain.repo.CharactersListRepo
import com.chocomiruku.core.data.Resource
import com.chocomiruku.core.domain.Character
import kotlinx.coroutines.flow.Flow

class GetCharactersUseCase(
    private val repo: CharactersListRepo
) {
    suspend operator fun invoke(): Flow<Resource<List<Character>>> {
        return repo.getCharacters()
    }
}