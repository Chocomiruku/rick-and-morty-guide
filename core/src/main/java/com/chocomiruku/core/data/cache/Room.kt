package com.chocomiruku.core.data.cache

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.chocomiruku.core.data.cache.dao.CharactersDao
import com.chocomiruku.core.data.cache.dao.RemoteKeysDao
import com.chocomiruku.core.data.cache.entity.CharacterEntity
import com.chocomiruku.core.data.cache.entity.RemoteKeys

@Database(entities = [CharacterEntity::class, RemoteKeys::class], version = 1)
abstract class CharactersDatabase : RoomDatabase() {
    abstract fun charactersDao(): CharactersDao
    abstract fun remoteKeysDao(): RemoteKeysDao
}

private lateinit var INSTANCE: CharactersDatabase

fun getDatabase(context: Context): CharactersDatabase {
    synchronized(CharactersDatabase::class.java) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(
                context.applicationContext,
                CharactersDatabase::class.java,
                "characters"
            ).build()
        }
    }
    return INSTANCE
}