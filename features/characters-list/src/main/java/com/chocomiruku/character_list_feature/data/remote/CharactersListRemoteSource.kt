package com.chocomiruku.character_list_feature.data.remote

import com.chocomiruku.core.data.Resource
import com.chocomiruku.core.domain.Character

interface CharactersListRemoteSource {
        suspend fun getCharacters(): Resource<List<Character>>
}