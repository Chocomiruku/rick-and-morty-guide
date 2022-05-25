package com.chocomiruku.character_list_feature.domain.usecase

import com.chocomiruku.character_list_feature.domain.repo.CharactersListRepo
import com.chocomiruku.core.data.Resource
import com.chocomiruku.core.data.Resource.Success
import com.chocomiruku.core.domain.Character
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SearchCharactersUseCase(
    private val repo: CharactersListRepo
) {
    suspend operator fun invoke(query: String): Flow<Resource<List<Character>>> {
        return repo.searchCharacters(query).map { Success(it) }
    }
}