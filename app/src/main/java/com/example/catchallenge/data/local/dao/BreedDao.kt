package com.example.catchallenge.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.catchallenge.data.local.entities.BreedEntity

@Dao
interface BreedDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBreedEntities(breeds: List<BreedEntity>)

    @Query("DELETE FROM breedentity")
    suspend fun clearBreedEntity()

    @Transaction
    @Query(
        """
            SELECT *
            FROM breedentity
            WHERE id LIKE :id
        """
    )
    suspend fun getBreedEntityById(id: String): BreedEntity


    @Query(
        """
            SELECT *
            FROM breedentity
        """
    )
    fun pagingSource(): PagingSource<Int, BreedEntity>
}