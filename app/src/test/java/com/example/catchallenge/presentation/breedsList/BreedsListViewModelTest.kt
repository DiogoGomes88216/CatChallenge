package com.example.catchallenge.presentation.breedsList

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.cash.turbine.test
import com.example.catchallenge.data.remote.BreedRemoteMediatorFactory
import com.example.catchallenge.data.repository.BreedRepository
import com.example.catchallenge.domain.models.Breed
import com.example.catchallenge.utils.MainCoroutineRule
import io.mockk.MockKAnnotations
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
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
class BreedsListViewModelTest {

    @Rule
    @JvmField
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutineRule = MainCoroutineRule()

    @MockK(relaxed = true)
    private lateinit var repository: BreedRepository

    @MockK
    private lateinit var mediatorFactory: BreedRemoteMediatorFactory

    private lateinit var underTest: BreedsListViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        underTest = BreedsListViewModel(mediatorFactory = mediatorFactory, repository = repository)
    }

    @Test
    fun `should isSearchShowing`() = runTest {
        underTest.isSearchShowing.test {
            with(awaitItem()){
                Assert.assertFalse(this)
            }

            underTest.toggleIsSearchShowing()

            with(awaitItem()){
                Assert.assertTrue(this)
            }
        }
    }

    @Test
    fun `should search`() = runTest {
        underTest.search.test {
            with(awaitItem()){
                Assert.assertEquals(this, "")
            }

            underTest.setSearch("abc")

            with(awaitItem()){
                Assert.assertEquals(this, "abc")
            }

            underTest.clearSearch()

            with(awaitItem()){
                Assert.assertEquals(this, "")
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