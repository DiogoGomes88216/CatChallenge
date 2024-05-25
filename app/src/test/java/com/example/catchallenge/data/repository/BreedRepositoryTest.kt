package com.example.catchallenge.data.repository

import com.example.catchallenge.data.local.dao.BreedDao
import com.example.catchallenge.data.local.dao.FavouriteDao
import com.example.catchallenge.data.local.entities.BreedEntity
import com.example.catchallenge.data.local.entities.FavouriteEntity
import com.example.catchallenge.data.mappers.BreedMapper.toBreed
import com.example.catchallenge.data.mappers.BreedMapper.toBreedEntity
import com.example.catchallenge.data.mappers.FavouriteMapper.toBreed
import com.example.catchallenge.data.mappers.FavouriteMapper.toFavouriteEntity
import com.example.catchallenge.data.remote.api.BreedApi
import com.example.catchallenge.data.remote.models.BreedDto
import com.example.catchallenge.domain.models.Breed
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(JUnit4::class)
class BreedRepositoryTest {

    @MockK
    private lateinit var api: BreedApi

    @MockK
    private lateinit var breedDao: BreedDao

    @MockK
    private lateinit var favouriteDao: FavouriteDao

    private lateinit var underTest: BreedRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        underTest = BreedRepository(api, breedDao, favouriteDao)
    }

    @Test
    fun `when searchBreeds should return BreedEntities`() = runTest {
        val query = "Aegean"

        val breed = BreedDto(
            id = "aege",
            name = "Aegean",
            origin = "Greece",
            lifeSpan = "9 - 12",
            temperament = "Affectionate, Social, Intelligent, Playful, Active",
            description = "Native to the Greek islands known as the Cyclades in the Aegean Sea, these are natural cats, meaning they developed without humans getting involved in their breeding. As a breed, Aegean Cats are rare, although they are numerous on their home islands. They are generally friendly toward people and can be excellent cats for families with children.",
            image = null
        )

        val listDtos = listOf(breed)
        val expected = listDtos.map { it.toBreedEntity(false) }

        coEvery { api.searchBreeds(query = query) } returns listDtos
        coEvery { favouriteDao.hasFavourite("aege") } returns false

        val result = underTest.searchBreed(query = query)

        Assert.assertEquals(expected, result)
    }

    @Test
    fun `when searchBreeds should return empty list after exception`() = runTest {
        val query = "Aegean"

        val expected = emptyList<BreedEntity>()

        coEvery { api.searchBreeds(query = query) } coAnswers { throw RuntimeException() }
        coEvery { favouriteDao.hasFavourite("aege") } returns false

        val result = underTest.searchBreed(query = query)

        Assert.assertEquals(expected, result)
    }

    @Test
    fun `when getBreeds should return BreedEntities`() = runTest {
        val limit = 0
        val page = 0

        val breed = BreedDto(
            id = "aege",
            name = "Aegean",
            origin = "Greece",
            lifeSpan = "9 - 12",
            temperament = "Affectionate, Social, Intelligent, Playful, Active",
            description = "Native to the Greek islands known as the Cyclades in the Aegean Sea, these are natural cats, meaning they developed without humans getting involved in their breeding. As a breed, Aegean Cats are rare, although they are numerous on their home islands. They are generally friendly toward people and can be excellent cats for families with children.",
            image = null
        )

        val listDtos = listOf(breed, breed, breed)
        val expected = listDtos.map { it.toBreedEntity(false) }

        coEvery { api.getBreeds(limit = limit, page = page) } returns listDtos
        coEvery { favouriteDao.hasFavourite("aege") } returns false

        val result = underTest.getBreeds(limit = limit, page = page)

        Assert.assertEquals(expected, result)
    }

    @Test
    fun `when getBreedDetailsById should return Breed`() = runTest {

        val breedEntity = BreedEntity(
            id = "aege",
            name = "Aegean",
            origin = "Greece",
            lifeSpan = "9 - 12",
            temperament = "Affectionate, Social, Intelligent, Playful, Active",
            description = "Native to the Greek islands known as the Cyclades in the Aegean Sea, these are natural cats, meaning they developed without humans getting involved in their breeding. As a breed, Aegean Cats are rare, although they are numerous on their home islands. They are generally friendly toward people and can be excellent cats for families with children.",
            imageUrl = null,
            isFavourite = false
        )

        val expected = breedEntity.toBreed()

        coEvery { breedDao.getBreedEntityById("aege") } returns flow {emit(breedEntity)}

        val result = underTest.getBreedDetailsById("aege").first()

        Assert.assertEquals(expected, result)
    }

    @Test
    fun `when isFavourite should return a boolean`() = runTest {

        coEvery { favouriteDao.hasFavourite("aege") } returns true

        val result = underTest.isFavourite("aege")

        Assert.assertTrue(result)
    }

    @Test
    fun `when toggleFavourite should delete from favourites and update Breed Entity`() = runTest {

        val breed = Breed(
            id = "aege",
            name = "Aegean",
            origin = "Greece",
            lifeSpan = "9 - 12",
            temperament = "Affectionate, Social, Intelligent, Playful, Active",
            description = "Native to the Greek islands known as the Cyclades in the Aegean Sea, these are natural cats, meaning they developed without humans getting involved in their breeding. As a breed, Aegean Cats are rare, although they are numerous on their home islands. They are generally friendly toward people and can be excellent cats for families with children.",
            imageUrl = null,
            isFavourite = true
        )
        val expected = breed.copy(isFavourite = false)

        coEvery { favouriteDao.deleteFavouriteEntity(entity = expected.toFavouriteEntity())  } returns Unit
        coEvery { breedDao.updateBreedEntity(breed = expected.toBreedEntity()) } returns Unit

        underTest.toggleFavourite(breed = breed)

        coVerify { favouriteDao.deleteFavouriteEntity(entity = expected.toFavouriteEntity()) }
        coVerify { breedDao.updateBreedEntity(breed = expected.toBreedEntity()) }
    }


    @Test
    fun `when toggleFavourite should insert into favourites and update Breed Entity`() = runTest {

        val breed = Breed(
            id = "aege",
            name = "Aegean",
            origin = "Greece",
            lifeSpan = "9 - 12",
            temperament = "Affectionate, Social, Intelligent, Playful, Active",
            description = "Native to the Greek islands known as the Cyclades in the Aegean Sea, these are natural cats, meaning they developed without humans getting involved in their breeding. As a breed, Aegean Cats are rare, although they are numerous on their home islands. They are generally friendly toward people and can be excellent cats for families with children.",
            imageUrl = null,
            isFavourite = false
        )
        val expected = breed.copy(isFavourite = true)

        coEvery { favouriteDao.insertFavouriteEntity(favourite = expected.toFavouriteEntity() )  } returns Unit
        coEvery { breedDao.updateBreedEntity(breed = expected.toBreedEntity()) } returns Unit

        underTest.toggleFavourite(breed = breed)

        coVerify { favouriteDao.insertFavouriteEntity(favourite = expected.toFavouriteEntity() ) }
        coVerify { breedDao.updateBreedEntity(breed = expected.toBreedEntity()) }
    }

    @Test
    fun `when getFavouritesFlow should return the favourite`() = runTest {
        val favouriteEntity = FavouriteEntity(
            id = "aege",
            name = "Aegean",
            origin = "Greece",
            lifeSpan = "9 - 12",
            temperament = "Affectionate, Social, Intelligent, Playful, Active",
            description = "Native to the Greek islands known as the Cyclades in the Aegean Sea, these are natural cats, meaning they developed without humans getting involved in their breeding. As a breed, Aegean Cats are rare, although they are numerous on their home islands. They are generally friendly toward people and can be excellent cats for families with children.",
            imageUrl = null,
        )

        val expected = favouriteEntity.toBreed()

        coEvery { favouriteDao.getFavouritesFlow() } returns flow {emit(listOf(favouriteEntity))}

        val result = underTest.getFavouritesFlow().first().first()

        Assert.assertEquals(expected, result)
    }

}