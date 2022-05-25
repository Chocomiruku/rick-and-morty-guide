package com.chocomiruku.character_list_feature.data.remote

import android.util.Log
import com.chocomiruku.core.data.Resource
import com.chocomiruku.core.data.Resource.Error
import com.chocomiruku.core.data.Resource.Success
import com.chocomiruku.core.data.data_sources.remote.CharactersApi
import com.chocomiruku.core.data.data_sources.remote.asDomainModel
import com.chocomiruku.core.domain.Character

class CharactersListRemoteSourceImpl(private val charactersService: CharactersApi) :
    CharactersListRemoteSource {
    override suspend fun getCharacters(): Resource<List<Character>> {
        return try {
            Success(charactersService.retrofitService.getCharacters().results.asDomainModel())
        } catch (e: Exception) {
            e.message?.let { Log.e("qqq", it) }
            Error(e)
        }
    }
}