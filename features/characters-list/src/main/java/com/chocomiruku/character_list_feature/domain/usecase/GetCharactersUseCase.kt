package com.chocomiruku.character_list_feature.domain.usecase

import androidx.paging.PagingData
import com.chocomiruku.character_list_feature.domain.repo.CharactersListRepo
import com.chocomiruku.core.domain.Character
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCharactersUseCase @Inject constructor(
    private val repo: CharactersListRepo
) {
    operator fun invoke(searchQuery: String): Flow<PagingData<Character>> {
        return repo.getCharacters(searchQuery)
    }
}