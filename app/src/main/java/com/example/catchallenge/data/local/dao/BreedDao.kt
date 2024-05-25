package com.example.catchallenge.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.catchallenge.data.local.entities.BreedEntity
import kotlinx.coroutines.flow.Flow

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
    fun getBreedEntityById(id: String): Flow<BreedEntity>


    @Query(
        """
            SELECT *
            FROM breedentity
        """
    )
    fun pagingSource(): PagingSource<Int, BreedEntity>

    @Update
    suspend fun updateBreedEntity(breed: BreedEntity)
}