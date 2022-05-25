package com.chocomiruku.core.data.data_sources.remote

import com.chocomiruku.core.domain.Character
import com.chocomiruku.core.util.formatCreationDate
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.util.*


@JsonClass(generateAdapter = true)
data class CharactersResponse(
    val info: Info,
    val results: List<CharacterJson>
)

@JsonClass(generateAdapter = true)
data class Info(
    val count: Int,
    val pages: Int,
    val next: String?,
    val prev: String?
)

data class CharacterJson(
    @Json(name = "id") val id: Int,
    @Json(name = "name") val name: String,
    @Json(name = "gender") val gender: String,
    @Json(name = "status") val status: String,
    @Json(name = "species") val species: String,
    @Json(name = "created") val creationDate: Date,
    @Json(name = "image") val profilePicUrl: String
)

fun List<CharacterJson>.asDomainModel(): List<Character> {
    return map {
        Character(
            id = it.id,
            name = it.name,
            gender = it.gender,
            status = it.status,
            species = it.species,
            creationDate = it.creationDate.formatCreationDate(),
            profilePicUrl = it.profilePicUrl
        )
    }
}



