package com.chocomiruku.core.data.data_sources.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface CharactersDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(agents: List<CharacterEntity>)

    @Query("SELECT * from characters")
    suspend fun getAll(): List<CharacterEntity>

    @Query("SELECT * from characters WHERE id = :characterId")
    suspend fun getCharacter(characterId: Int): CharacterEntity

    @Query("SELECT * from characters WHERE name LIKE '%' || :query || '%'")
    fun searchByName(query: String): Flow<List<CharacterEntity>>
}