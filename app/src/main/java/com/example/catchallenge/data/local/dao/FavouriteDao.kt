package com.example.catchallenge.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.catchallenge.data.local.entities.FavouriteEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FavouriteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavouriteEntity(favourite: FavouriteEntity)

    @Delete
    suspend fun deleteFavouriteEntity(entity: FavouriteEntity)

    @Query(
        """
            SELECT EXISTS(
            SELECT *
            FROM favouriteentity
            WHERE id LIKE :id)
        """
    )
    suspend fun hasFavourite(id: String): Boolean

    @Query(
        """
            SELECT *
            FROM favouriteentity
            ORDER BY name 
            ASC
        """
    )
    fun getFavouritesFlow(): Flow<List<FavouriteEntity>>

}