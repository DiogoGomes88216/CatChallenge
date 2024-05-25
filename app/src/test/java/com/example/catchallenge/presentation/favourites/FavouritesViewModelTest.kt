package com.example.catchallenge.presentation.favourites

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.cash.turbine.test
import com.example.catchallenge.data.repository.BreedRepository
import com.example.catchallenge.domain.models.Breed
import com.example.catchallenge.utils.MainCoroutineRule
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
@OptIn(ExperimentalCoroutinesApi::class)
class FavouritesViewModelTest  {

    @Rule
    @JvmField
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutineRule = MainCoroutineRule()

    @MockK(relaxed = true)
    private lateinit var repository: BreedRepository

    private lateinit var underTest: FavouritesViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        underTest = FavouritesViewModel(repository)
    }

    @Test
    fun `should load favourites`() = runTest {
        val breeds = listOf(mockk<Breed>())

        coEvery { repository.getFavouritesFlow() } returns flowOf(breeds)

        underTest.favouritesState.test {
            with(awaitItem()){
                Assert.assertTrue(isLoading)
                Assert.assertFalse(hasError)
                Assert.assertEquals(emptyList<Breed>(), favourites)
            }

            with(awaitItem()){
                Assert.assertFalse(isLoading)
                Assert.assertFalse(hasError)
                Assert.assertEquals(breeds, favourites)
            }
        }
    }

    @Test
    fun `should not load favourites if receives an exception`() = runTest {
        coEvery { repository.getFavouritesFlow() } returns flow { throw RuntimeException() }
        underTest.favouritesState.test {
            with(awaitItem()){
                Assert.assertTrue(isLoading)
                Assert.assertFalse(hasError)
                Assert.assertEquals(emptyList<Breed>(), favourites)
            }

            with(awaitItem()){
                Assert.assertFalse(isLoading)
                Assert.assertTrue(hasError)
                Assert.assertEquals(emptyList<Breed>(), favourites)
            }
        }
    }

    @Test
    fun `should call toggle Favourite`() = runTest {
        val breed = mockk<Breed>()
        underTest.toggleFavourite(breed)
        advanceUntilIdle()
        coVerify { repository.toggleFavourite(breed) }
    }

}