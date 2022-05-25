package com.chocomiruku.core.domain

import android.os.Parcelable
import com.chocomiruku.core.data.data_sources.local.CharacterEntity
import kotlinx.parcelize.Parcelize

@Parcelize
data class Character(
    val id: Int,
    val name: String,
    val gender: String,
    val status: String,
    val species: String,
    val creationDate: String,
    val profilePicUrl: String
) : Parcelable

fun List<Character>.asDatabaseModel(): List<CharacterEntity> {
    return map {
        CharacterEntity(
            id = it.id,
            name = it.name,
            gender = it.gender,
            status = it.status,
            species = it.species,
            creationDate = it.creationDate,
            picUrl = it.profilePicUrl
        )
    }
}