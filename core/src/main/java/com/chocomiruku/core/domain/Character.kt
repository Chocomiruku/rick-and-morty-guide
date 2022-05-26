package com.chocomiruku.core.domain

import android.os.Parcelable
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

