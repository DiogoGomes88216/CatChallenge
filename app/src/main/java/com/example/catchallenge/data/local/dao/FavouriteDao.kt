package com.example.catchallenge.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.catchallenge.data.local.entities.FavouriteEntity

@Dao
interface FavouriteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavouriteEntity(favourite: FavouriteEntity)

    @Query(
        """
            DELETE 
            FROM favouriteentity
            WHERE id LIKE :id
        """
    )
    suspend fun deleteFavouriteEntity(id: String)

    @Query("DELETE FROM favouriteentity")
    suspend fun clearFavouriteEntity()

    @Transaction
    @Query(
        """
            SELECT EXISTS(
            SELECT *
            FROM favouriteentity
            WHERE id LIKE :id)
        """
    )
    suspend fun hasFavourite(id: String): Boolean

    @Transaction
    @Query(
        """
            SELECT *
            FROM favouriteentity
            ORDER BY name 
            ASC
        """
    )
    suspend fun getFavourites(): List<FavouriteEntity>?
}