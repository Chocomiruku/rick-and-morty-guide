package com.chocomiruku.character_details_feature.data

import com.chocomiruku.character_details_feature.domain.repo.CharacterDetailsRepo
import com.chocomiruku.core.data.cache.dao.CharactersDao
import com.chocomiruku.core.data.cache.entity.asDomainModel
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CharacterDetailsRepoImpl @Inject constructor(private val charactersDao: CharactersDao) :
    CharacterDetailsRepo {
    override fun getCharacter(characterId: Int) =
        charactersDao.getCharacter(characterId).map { it.asDomainModel() }
}