package com.example.catchallenge.presentation.breedDetails

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.SavedStateHandle
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
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
@OptIn(ExperimentalCoroutinesApi::class)
class BreedDetailsViewModelTest  {

    @Rule
    @JvmField
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutineRule = MainCoroutineRule()

    @MockK(relaxed = true)
    private lateinit var repository: BreedRepository

    @MockK(relaxed = true)
    private lateinit var stateHandle : SavedStateHandle

    private lateinit var underTest: BreedDetailsViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        underTest = BreedDetailsViewModel(
            repository= repository,
            stateHandle = stateHandle
        )
    }

    /*@Test
    fun `should load Breed`() = runTest {
        val mockkBreed = mockk<Breed>()
        val emptyBreed: Breed = Breed(
            id = "",
            name = "",
            origin = "",
            lifeSpan = "",
            temperament = "",
            description = "",
            imageUrl = "",
            isFavourite = false,
        )

        coEvery { repository.getBreedDetailsById("abc") } returns flowOf(mockkBreed)
        every { stateHandle.get<String>("id") } returns "abc"
        every { stateHandle.contains("id") } returns true

        underTest.breedDetailsState.test {
            with(awaitItem()){
                Assert.assertEquals(emptyBreed, breed)
            }

            with(awaitItem()){
                Assert.assertEquals(mockkBreed, breed)
            }
        }
    }*/

    @Test
    fun `should call toggle Favourite`() = runTest {
        val breed = mockk<Breed>()
        underTest.toggleFavourite(breed)
        advanceUntilIdle()
        coVerify { repository.toggleFavourite(breed) }
    }

}