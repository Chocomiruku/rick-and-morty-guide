package com.chocomiruku.character_list_feature.data

import com.chocomiruku.character_list_feature.data.local.CharactersListLocalSource
import com.chocomiruku.character_list_feature.data.remote.CharactersListRemoteSource
import com.chocomiruku.character_list_feature.domain.repo.CharactersListRepo
import com.chocomiruku.core.data.NetworkBoundResource
import com.chocomiruku.core.data.Resource
import com.chocomiruku.core.data.data_sources.local.asDomainModel
import com.chocomiruku.core.domain.Character
import com.chocomiruku.core.domain.asDatabaseModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class CharactersListRepoImpl(
    private val remoteSource: CharactersListRemoteSource,
    private val localSource: CharactersListLocalSource,
) : CharactersListRepo {
    override suspend fun getCharacters(): Flow<Resource<List<Character>>> {
        return object : NetworkBoundResource<List<Character>>() {
            override suspend fun remoteFetch(): Resource<List<Character>> {
                return remoteSource.getCharacters()
            }

            override suspend fun saveFetchResult(data: List<Character>) {
                localSource.saveCharacters(data.asDatabaseModel())
            }

            override suspend fun localFetch(): List<Character> {
                return localSource.getCharacters().asDomainModel()
            }

            override fun shouldFetchWithLocalData() = true
        }.asFlow()
    }

    override suspend fun searchCharacters(query: String): Flow<List<Character>> {
        return localSource.searchCharacters(query).map { it.asDomainModel() }
    }
}