package com.chocomiruku.core.data.data_sources.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [CharacterEntity::class], version = 1)
abstract class CharactersDatabase : RoomDatabase() {
    abstract val charactersDao: CharactersDao
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