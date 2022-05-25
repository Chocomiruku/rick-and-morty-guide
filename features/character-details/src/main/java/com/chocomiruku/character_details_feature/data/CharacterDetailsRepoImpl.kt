package com.chocomiruku.character_details_feature.data

import com.chocomiruku.character_details_feature.data.local.CharacterDetailsLocalSource
import com.chocomiruku.character_details_feature.domain.repo.CharacterDetailsRepo
import com.chocomiruku.core.data.NetworkBoundResource
import com.chocomiruku.core.data.Resource
import com.chocomiruku.core.data.data_sources.local.asDomainModel
import com.chocomiruku.core.domain.Character
import kotlinx.coroutines.flow.Flow

class CharacterDetailsRepoImpl(private val localSource: CharacterDetailsLocalSource) :
    CharacterDetailsRepo {
    override suspend fun getCharacter(characterId: Int): Flow<Resource<Character>> {
        return object : NetworkBoundResource<Character>() {
            override suspend fun remoteFetch(): Resource<Character>? {
                return null
            }

            override suspend fun saveFetchResult(data: Character) {
            }

            override suspend fun localFetch(): Character {
                return localSource.getCharacter(characterId).asDomainModel()
            }

            override fun shouldFetch() = false
        }.asFlow()
    }
}