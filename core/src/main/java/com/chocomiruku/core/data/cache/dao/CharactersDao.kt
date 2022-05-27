package com.chocomiruku.core.data.cache.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.chocomiruku.core.data.cache.entity.CharacterEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CharactersDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(agents: List<CharacterEntity>)

    @Query("SELECT * from characters WHERE id = :characterId")
    fun getCharacter(characterId: Int): Flow<CharacterEntity>

    @Query("SELECT * from characters WHERE name LIKE '%' || :query || '%'")
    fun getCharactersByName(query: String): PagingSource<Int, CharacterEntity>

    @Query("DELETE FROM characters")
    suspend fun clearCharacters()
}