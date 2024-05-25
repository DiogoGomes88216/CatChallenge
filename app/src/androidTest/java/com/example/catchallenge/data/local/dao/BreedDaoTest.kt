package com.example.catchallenge.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.example.catchallenge.data.local.db.BreedDatabase
import com.example.catchallenge.data.local.entities.BreedEntity
import com.example.catchallenge.getData
import com.google.common.truth.Truth.assertThat
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.util.concurrent.CountDownLatch

@RunWith(AndroidJUnit4::class)
@SmallTest
class BreedDaoTest {

    private lateinit var database: BreedDatabase
    private lateinit var dao: BreedDao

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            BreedDatabase::class.java
        )
            .allowMainThreadQueries()
            .build()

        dao = database.breedDao
    }

    @After
    fun teardown() {
        database.close()
    }

    @Test
    fun insertBreedEntities() = runTest {
        val breed1 = BreedEntity(
            id = "abys",
            name = "Abyssinian",
            origin = "Egypt",
            lifeSpan = "14 - 15",
            temperament = "Active, Energetic, Independent, Intelligent, Gentle",
            description = "The Abyssinian is easy to care for, and a joy to have in your home. They’re affectionate cats and love both people and other animals.",
            imageUrl = "https://cdn2.thecatapi.com/images/0XYvRd7oD.jpg",
            isFavourite = true,
        )
        val breed2 = BreedEntity(
            id = "aege",
            name = "Aegean",
            origin = "Greece",
            lifeSpan = "9 - 12",
            temperament = "Affectionate, Social, Intelligent, Playful, Active",
            description = "Native to the Greek islands known as the Cyclades in the Aegean Sea, these are natural cats, meaning they developed without humans getting involved in their breeding. As a breed, Aegean Cats are rare, although they are numerous on their home islands. They are generally friendly toward people and can be excellent cats for families with children.",
            imageUrl = "https://cdn2.thecatapi.com/images/ozEvzdVM-.jpg",
            isFavourite = false,
        )
        val breedEntities = listOf(breed1,breed2)
        dao.insertBreedEntities(breedEntities)

        val obtainedData = dao.pagingSource().getData()
        assertEquals(obtainedData, breedEntities)
    }

    @Test
    fun clearBreeds() = runTest {
        val breedEntity = BreedEntity(
            id = "abys",
            name = "Abyssinian",
            origin = "Egypt",
            lifeSpan = "14 - 15",
            temperament = "Active, Energetic, Independent, Intelligent, Gentle",
            description = "The Abyssinian is easy to care for, and a joy to have in your home. They’re affectionate cats and love both people and other animals.",
            imageUrl = "https://cdn2.thecatapi.com/images/0XYvRd7oD.jpg",
            isFavourite = true,
        )

        dao.insertBreedEntities(listOf(breedEntity))
        dao.clearBreedEntity()

        val obtainedData = dao.pagingSource().getData()
        assertThat(obtainedData).doesNotContain(breedEntity)
    }

    @Test
    fun getBreedEntityById() = runTest {
        val breedEntity = BreedEntity(
            id = "abys",
            name = "Abyssinian",
            origin = "Egypt",
            lifeSpan = "14 - 15",
            temperament = "Active, Energetic, Independent, Intelligent, Gentle",
            description = "The Abyssinian is easy to care for, and a joy to have in your home. They’re affectionate cats and love both people and other animals.",
            imageUrl = "https://cdn2.thecatapi.com/images/0XYvRd7oD.jpg",
            isFavourite = true,
        )

        dao.insertBreedEntities(listOf(breedEntity))

        val obtainedData = dao.getBreedEntityById("abys").take(1).toList()

        assertThat(obtainedData[0]).isEqualTo(breedEntity)
    }

    @Test
    fun updateBreedEntity() = runTest {
        val breedEntity = BreedEntity(
            id = "abys",
            name = "Abyssinian",
            origin = "Egypt",
            lifeSpan = "14 - 15",
            temperament = "Active, Energetic, Independent, Intelligent, Gentle",
            description = "The Abyssinian is easy to care for, and a joy to have in your home. They’re affectionate cats and love both people and other animals.",
            imageUrl = "https://cdn2.thecatapi.com/images/0XYvRd7oD.jpg",
            isFavourite = true,
        )
        dao.insertBreedEntities(listOf(breedEntity))

        val newBreedEntity = breedEntity.copy(isFavourite = false)

        dao.updateBreedEntity(newBreedEntity)

        val obtainedData = dao.getBreedEntityById("abys").take(1).toList()

        assertThat(obtainedData[0]).isEqualTo(newBreedEntity)
    }


}