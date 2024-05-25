package com.example.catchallenge.data.local.dao

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.example.catchallenge.data.local.db.BreedDatabase
import com.example.catchallenge.data.local.entities.BreedEntity
import com.example.catchallenge.data.local.entities.FavouriteEntity
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@SmallTest
class FavouriteDaoTest {

    private lateinit var database: BreedDatabase
    private lateinit var dao: FavouriteDao

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            BreedDatabase::class.java
        )
            .allowMainThreadQueries()
            .build()

        dao = database.favouriteDao
    }

    @After
    fun teardown() {
        database.close()
    }


    @Test
    fun insertFavouriteEntity() = runTest {
        val favourite = FavouriteEntity(
            id = "abys",
            name = "Abyssinian",
            origin = "Egypt",
            lifeSpan = "14 - 15",
            temperament = "Active, Energetic, Independent, Intelligent, Gentle",
            description = "The Abyssinian is easy to care for, and a joy to have in your home. They’re affectionate cats and love both people and other animals.",
            imageUrl = "https://cdn2.thecatapi.com/images/0XYvRd7oD.jpg",
        )

        dao.insertFavouriteEntity(favourite)

        val obtainedFavourite = dao.getFavouritesFlow().take(1).first()
        assert(obtainedFavourite.contains(favourite))
    }

    @Test
    fun deleteFavouriteEntity() = runTest{
        val favourite = FavouriteEntity(
            id = "abys",
            name = "Abyssinian",
            origin = "Egypt",
            lifeSpan = "14 - 15",
            temperament = "Active, Energetic, Independent, Intelligent, Gentle",
            description = "The Abyssinian is easy to care for, and a joy to have in your home. They’re affectionate cats and love both people and other animals.",
            imageUrl = "https://cdn2.thecatapi.com/images/0XYvRd7oD.jpg",
        )

        dao.insertFavouriteEntity(favourite)
        dao.deleteFavouriteEntity(favourite)

        val obtainedFavourite = dao.getFavouritesFlow().take(1).first()
        assert(obtainedFavourite.isEmpty())
    }

    @Test
    fun hasFavourite() = runTest{
        val favourite = FavouriteEntity(
            id = "abys",
            name = "Abyssinian",
            origin = "Egypt",
            lifeSpan = "14 - 15",
            temperament = "Active, Energetic, Independent, Intelligent, Gentle",
            description = "The Abyssinian is easy to care for, and a joy to have in your home. They’re affectionate cats and love both people and other animals.",
            imageUrl = "https://cdn2.thecatapi.com/images/0XYvRd7oD.jpg",
        )

        dao.insertFavouriteEntity(favourite)
        val isFavourite = dao.hasFavourite(favourite.id)
        assert(isFavourite)
    }
}