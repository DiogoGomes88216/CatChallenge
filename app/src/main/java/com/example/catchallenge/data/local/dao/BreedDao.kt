package com.example.catchallenge.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.catchallenge.data.local.entities.BreedEntity

@Dao
interface BreedDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBreedEntities(breeds: List<BreedEntity>)

    @Query("DELETE FROM breedentity")
    suspend fun clearBreedEntity()

    @Query(
        """
            SELECT *
            FROM breedentity
        """
    )
    fun pagingSource(): PagingSource<Int, BreedEntity>
}