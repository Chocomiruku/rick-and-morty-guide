package com.chocomiruku.core.data.data_sources.local

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
    val picUrl: String
)

fun CharacterEntity.asDomainModel(): Character {
    return Character(
        id = id,
        name = name,
        gender = gender,
        status = status,
        species = species,
        creationDate = creationDate,
        profilePicUrl = picUrl
    )
}

fun List<CharacterEntity>.asDomainModel(): List<Character> {
    return map {
        Character(
            id = it.id,
            name = it.name,
            gender = it.gender,
            status = it.status,
            species = it.species,
            creationDate = it.creationDate,
            profilePicUrl = it.picUrl
        )
    }
}