package com.chocomiruku.character_details_feature.data

import com.chocomiruku.character_details_feature.domain.repo.CharacterDetailsRepo
import com.chocomiruku.core.data.cache.CharactersDatabase
import com.chocomiruku.core.data.cache.entity.asDomainModel
import kotlinx.coroutines.flow.map

class CharacterDetailsRepoImpl(private val database: CharactersDatabase) :
    CharacterDetailsRepo {
    override fun getCharacter(characterId: Int) =
        database.charactersDao().getCharacter(characterId).map { it.asDomainModel() }
}