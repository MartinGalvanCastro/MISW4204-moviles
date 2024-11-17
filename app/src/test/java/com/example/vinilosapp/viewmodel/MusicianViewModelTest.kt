package com.example.vinilosapp.viewmodel

import com.example.models.MusicianDetailDTO
import com.example.models.MusicianSimpleDTO
import com.example.models.PrizeDetailDTO
import com.example.vinilosapp.repository.MusicianRepository
import com.example.vinilosapp.repository.PrizeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import java.math.BigDecimal

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class MusicianViewModelTest {

    @Mock
    private lateinit var musicianRepository: MusicianRepository

    @Mock
    private lateinit var prizeRepository: PrizeRepository

    private lateinit var musicianViewModel: MusicianViewModel

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(testDispatcher)

        musicianViewModel = MusicianViewModel(
            musicianRepository = musicianRepository,
            prizeRepository = prizeRepository,
            ioDispatcher = testDispatcher,
            defaultDispatcher = testDispatcher,
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `Given successful repository response When fetchAllItems is called Then items are updated`() = runTest {
        val mockMusicians = listOf(mock(MusicianSimpleDTO::class.java), mock(MusicianSimpleDTO::class.java))
        `when`(musicianRepository.fetchAll()).thenReturn(Result.success(mockMusicians))

        musicianViewModel.fetchAllItems()
        testDispatcher.scheduler.advanceUntilIdle()

        assertEquals(mockMusicians, musicianViewModel.state.value.filteredItems)
        assertThat(musicianViewModel.state.value.isLoading, `is`(false))
    }

    @Test
    fun `Given repository failure When fetchAllItems is called Then errorMessage is set`() = runTest {
        `when`(musicianRepository.fetchAll()).thenReturn(Result.failure(RuntimeException("API error")))

        musicianViewModel.fetchAllItems()
        testDispatcher.scheduler.advanceUntilIdle()

        assertEquals("API error", musicianViewModel.state.value.errorMessage)
        assertThat(musicianViewModel.state.value.isLoading, `is`(false))
    }

    @Test
    fun `Given successful repository response When fetchDetailById is called Then detail is updated`() = runTest {
        val musicianId = "1"
        val mockMusicianDetail = MusicianDetailDTO(
            id = BigDecimal(musicianId),
            name = "Test Musician",
            description = "Description",
            image = "",
            birthDate = "",
            albums = emptyList(),
            collectors = emptyList(),
            performerPrizes = emptyList(),
        )
        `when`(musicianRepository.fetchById(musicianId)).thenReturn(Result.success(mockMusicianDetail))

        musicianViewModel.fetchDetailById(musicianId)
        testDispatcher.scheduler.advanceUntilIdle()

        assertEquals(mockMusicianDetail, musicianViewModel.state.value.detail)
        assertThat(musicianViewModel.state.value.isLoading, `is`(false))
    }

    @Test
    fun `Given repository failure When fetchDetailById is called Then errorMessage is set`() = runTest {
        val musicianId = "1"
        `when`(musicianRepository.fetchById(musicianId)).thenReturn(Result.failure(RuntimeException("API error")))

        musicianViewModel.fetchDetailById(musicianId)
        testDispatcher.scheduler.advanceUntilIdle()

        assertEquals("API error", musicianViewModel.state.value.errorMessage)
        assertThat(musicianViewModel.state.value.isLoading, `is`(false))
    }

    @Test
    fun `Given musicians exist When filterMusicians is called with matching query Then filteredItems is updated`() = runTest {
        val musician1 = mock(MusicianSimpleDTO::class.java).apply { `when`(name).thenReturn("Musician One") }
        val musician2 = mock(MusicianSimpleDTO::class.java).apply { `when`(name).thenReturn("Musician Two") }
        val musician3 = mock(MusicianSimpleDTO::class.java).apply { `when`(name).thenReturn("Musician Three") }

        `when`(musicianRepository.fetchAll()).thenReturn(Result.success(listOf(musician1, musician2, musician3)))
        musicianViewModel.fetchAllItems()
        testDispatcher.scheduler.advanceUntilIdle()

        musicianViewModel.filterMusicians("One")
        testDispatcher.scheduler.advanceUntilIdle()

        assertEquals(listOf(musician1), musicianViewModel.state.value.filteredItems)
    }

    @Test
    fun `Given musicians exist When filterMusicians is called with non-matching query Then filteredItems is empty`() = runTest {
        val musician1 = mock(MusicianSimpleDTO::class.java).apply { `when`(name).thenReturn("Musician One") }
        val musician2 = mock(MusicianSimpleDTO::class.java).apply { `when`(name).thenReturn("Musician Two") }
        val musician3 = mock(MusicianSimpleDTO::class.java).apply { `when`(name).thenReturn("Musician Three") }

        `when`(musicianRepository.fetchAll()).thenReturn(Result.success(listOf(musician1, musician2, musician3)))
        musicianViewModel.fetchAllItems()
        testDispatcher.scheduler.advanceUntilIdle()

        musicianViewModel.filterMusicians("Non-existing musician")
        testDispatcher.scheduler.advanceUntilIdle()

        assertTrue(musicianViewModel.state.value.filteredItems.isEmpty())
    }

    @Test
    fun `Given musicians exist When filterMusicians is called with blank query Then all items are shown`() = runTest {
        val musician1 = mock(MusicianSimpleDTO::class.java)
        val musician2 = mock(MusicianSimpleDTO::class.java)
        `when`(musicianRepository.fetchAll()).thenReturn(Result.success(listOf(musician1, musician2)))

        musicianViewModel.fetchAllItems()
        testDispatcher.scheduler.advanceUntilIdle()

        musicianViewModel.filterMusicians("")
        testDispatcher.scheduler.advanceUntilIdle()

        assertEquals(listOf(musician1, musician2), musicianViewModel.state.value.filteredItems)
    }

    @Test
    fun `Given successful prize fetch When fetchPrizesForPerformer is called Then prizes are updated`() = runTest {
        val prizeId = "1"
        val mockPrize = mock(PrizeDetailDTO::class.java)
        `when`(prizeRepository.fetchPrizes(listOf(prizeId))).thenReturn(listOf(mockPrize))

        musicianViewModel.fetchPrizesForPerformer(listOf(prizeId))
        testDispatcher.scheduler.advanceUntilIdle()

        assertEquals(listOf(mockPrize), musicianViewModel.prizesState.value.items)
        assertThat(musicianViewModel.prizesState.value.isLoading, `is`(false))
    }

    @Test
    fun `Given repository failure When fetchPrizesForPerformer is called Then errorMessage is set`() = runTest {
        val prizeId = "1"
        `when`(prizeRepository.fetchPrizes(listOf(prizeId))).thenThrow(RuntimeException("API error"))

        musicianViewModel.fetchPrizesForPerformer(listOf(prizeId))
        testDispatcher.scheduler.advanceUntilIdle()

        assertEquals("API error", musicianViewModel.prizesState.value.errorMessage)
        assertThat(musicianViewModel.prizesState.value.isLoading, `is`(false))
    }
}
