package com.chocomiruku.core.data.cache.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.chocomiruku.core.domain.Character

@Entity(tableName = "characters")
data class CharacterEntity(
    @PrimaryKey
    val id: Int,
    val name: String,
    val gender: String,
    val status: String,
    val species: String,
    val creationDate: String,
    val profilePicUrl: String
)

fun CharacterEntity.asDomainModel(): Character {
    return Character(
        id = id,
        name = name,
        gender = gender,
        status = status,
        species = species,
        creationDate = creationDate,
        profilePicUrl = profilePicUrl
    )
}