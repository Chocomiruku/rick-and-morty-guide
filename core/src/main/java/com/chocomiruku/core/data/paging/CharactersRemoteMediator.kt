package com.chocomiruku.core.data.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.chocomiruku.core.data.api.CharactersApiService
import com.chocomiruku.core.data.api.asDatabaseModel
import com.chocomiruku.core.data.cache.CharactersDatabase
import com.chocomiruku.core.data.cache.entity.CharacterEntity
import com.chocomiruku.core.data.cache.entity.RemoteKeys
import retrofit2.HttpException
import java.io.IOException

private const val STARTING_PAGE_INDEX = 1

@OptIn(ExperimentalPagingApi::class)
class CharactersRemoteMediator(
    private val service: CharactersApiService,
    private val charactersDatabase: CharactersDatabase
) : RemoteMediator<Int, CharacterEntity>() {

    override suspend fun initialize(): InitializeAction {
        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, CharacterEntity>
    ): MediatorResult {

        val page = when (loadType) {
            LoadType.REFRESH -> {
                val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                remoteKeys?.nextKey?.minus(1) ?: STARTING_PAGE_INDEX
            }
            LoadType.PREPEND -> {
                val remoteKeys = getRemoteKeyForFirstItem(state)
                val prevKey = remoteKeys?.prevKey
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                prevKey
            }
            LoadType.APPEND -> {
                val remoteKeys = getRemoteKeyForLastItem(state)
                val nextKey = remoteKeys?.nextKey
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                nextKey
            }
        }

        try {
            val apiResponse = service.getCharacters(page)

            val characters = apiResponse.results
            val endOfPaginationReached = apiResponse.info.next == null

            charactersDatabase.withTransaction {
                val prevKey = if (apiResponse.info.prev == null) null else page - 1
                val nextKey = if (endOfPaginationReached) null else page + 1
                val keys = characters.map {
                    RemoteKeys(characterId = it.id, prevKey = prevKey, nextKey = nextKey)
                }
                charactersDatabase.remoteKeysDao().insertAll(keys)
                charactersDatabase.charactersDao().insertAll(characters.asDatabaseModel())
            }
            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (exception: IOException) {
            return MediatorResult.Error(exception)
        } catch (exception: HttpException) {
            return MediatorResult.Error(exception)
        }
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, CharacterEntity>): RemoteKeys? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { character ->
                charactersDatabase.remoteKeysDao().remoteKeysCharacterId(character.id)
            }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, CharacterEntity>): RemoteKeys? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { character ->
                charactersDatabase.remoteKeysDao().remoteKeysCharacterId(character.id)
            }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, CharacterEntity>
    ): RemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { characterId ->
                charactersDatabase.remoteKeysDao().remoteKeysCharacterId(characterId)
            }
        }
    }
}