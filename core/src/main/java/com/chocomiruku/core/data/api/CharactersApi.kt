package com.chocomiruku.core.data.api

import retrofit2.http.GET
import retrofit2.http.Query


interface CharactersApi {
    @GET("character")
    suspend fun getCharacters(
        @Query("page") page: Int,
        @Query("name") query: String,
    ): CharactersResponse
}