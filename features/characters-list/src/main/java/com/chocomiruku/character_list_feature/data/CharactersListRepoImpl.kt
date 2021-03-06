package com.chocomiruku.character_list_feature.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.chocomiruku.character_list_feature.domain.repo.CharactersListRepo
import com.chocomiruku.core.data.api.CharactersApi
import com.chocomiruku.core.data.cache.CharactersDatabase
import com.chocomiruku.core.data.cache.entity.asDomainModel
import com.chocomiruku.core.data.paging.CharactersRemoteMediator
import com.chocomiruku.core.domain.Character
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CharactersListRepoImpl @Inject constructor(
    private val service: CharactersApi,
    private val database: CharactersDatabase
) : CharactersListRepo {

    override fun getCharacters(searchQuery: String): Flow<PagingData<Character>> {
        val pagingSourceFactory = { database.charactersDao().getCharactersByName(searchQuery) }
        val remoteMediator = CharactersRemoteMediator(service, database)
        remoteMediator.updateQuery(searchQuery)

        @OptIn(ExperimentalPagingApi::class)
        return Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE,
                enablePlaceholders = true
            ),
            remoteMediator = remoteMediator,
            pagingSourceFactory = pagingSourceFactory
        ).flow.map { pagingData ->
            pagingData.map {
                it.asDomainModel()
            }
        }
    }

    private companion object {
        const val NETWORK_PAGE_SIZE = 20
    }
}