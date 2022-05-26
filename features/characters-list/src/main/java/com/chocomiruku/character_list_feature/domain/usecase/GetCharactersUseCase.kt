package com.chocomiruku.character_list_feature.domain.usecase

import androidx.paging.PagingData
import com.chocomiruku.character_list_feature.domain.repo.CharactersListRepo
import com.chocomiruku.core.domain.Character
import kotlinx.coroutines.flow.Flow

class GetCharactersUseCase(
    private val repo: CharactersListRepo
) {
    operator fun invoke(): Flow<PagingData<Character>> {
        return repo.getCharacters()
    }
}