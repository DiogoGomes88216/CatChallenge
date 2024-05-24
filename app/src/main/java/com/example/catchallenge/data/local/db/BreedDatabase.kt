package com.example.catchallenge.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.catchallenge.data.local.dao.BreedDao
import com.example.catchallenge.data.local.dao.FavouriteDao
import com.example.catchallenge.data.local.dao.RemoteKeysDao
import com.example.catchallenge.data.local.entities.BreedEntity
import com.example.catchallenge.data.local.entities.FavouriteEntity
import com.example.catchallenge.data.local.entities.RemoteKeys

@Database(
    entities = [
        BreedEntity::class,
        RemoteKeys::class,
        FavouriteEntity::class
    ],
    version = 3
)
abstract class BreedDatabase: RoomDatabase() {
    abstract val breedDao: BreedDao
    abstract val favouriteDao: FavouriteDao
    abstract fun remoteKeysDao(): RemoteKeysDao
}