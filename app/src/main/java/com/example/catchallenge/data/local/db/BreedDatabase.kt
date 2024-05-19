package com.example.catchallenge.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.catchallenge.data.local.dao.BreedDao
import com.example.catchallenge.data.local.dao.RemoteKeysDao
import com.example.catchallenge.data.local.entities.BreedEntity
import com.example.catchallenge.data.local.entities.RemoteKeys

@Database(
    entities = [
        BreedEntity::class,
        RemoteKeys::class,
    ],
    version = 1
)
abstract class BreedDatabase: RoomDatabase() {
    abstract val breedDao: BreedDao
    abstract fun remoteKeysDao(): RemoteKeysDao
}