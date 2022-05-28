package com.chocomiruku.core.domain

data class Character(
    val id: Int,
    val name: String,
    val gender: String,
    val status: String,
    val species: String,
    val creationDate: String,
    val profilePicUrl: String
)