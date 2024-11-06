package com.example.vinilosapp.viewmodel

import com.example.models.MusicianDetailDTO
import com.example.models.MusicianSimpleDTO
import com.example.vinilosapp.repository.MusicianRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
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
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner
import java.math.BigDecimal

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class MusicianViewModelTest {

    @Mock
    private lateinit var musicianRepository: MusicianRepository

    @InjectMocks
    private lateinit var musicianViewModel: MusicianViewModel

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `Given successful repository response When fetchMusicians is called Then musicians are updated`() = runTest {
        val mockMusicianList = listOf(mock(MusicianSimpleDTO::class.java), mock(MusicianSimpleDTO::class.java))
        `when`(musicianRepository.fetchMusicians()).thenReturn(Result.success(mockMusicianList))

        musicianViewModel.fetchMusicians()
        testDispatcher.scheduler.advanceUntilIdle()

        assertEquals(mockMusicianList, musicianViewModel.filteredMusicians.first())
        assertThat(musicianViewModel.loading.first(), `is`(false))
    }

    @Test
    fun `Given repository failure When fetchMusicians is called Then errorMessage is set`() = runTest {
        `when`(musicianRepository.fetchMusicians()).thenReturn(Result.failure(RuntimeException("API error")))

        musicianViewModel.fetchMusicians()
        testDispatcher.scheduler.advanceUntilIdle()

        assertEquals("Error fetching musicians", musicianViewModel.errorMessage.first())
        assertThat(musicianViewModel.loading.first(), `is`(false))
    }

    @Test
    fun `Given successful repository response When fetchMusicianById is called Then musician is updated`() = runTest {
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
        `when`(musicianRepository.fetchMusicianById(musicianId)).thenReturn(Result.success(mockMusicianDetail))

        musicianViewModel.fetchMusicianById(musicianId)
        testDispatcher.scheduler.advanceUntilIdle()

        assertEquals(mockMusicianDetail, musicianViewModel.musician.first())
        assertThat(musicianViewModel.loading.first(), `is`(false))
    }

    @Test
    fun `Given repository failure When fetchMusicianById is called Then errorMessage is set`() = runTest {
        val musicianId = "1"
        `when`(musicianRepository.fetchMusicianById(musicianId)).thenReturn(Result.failure(RuntimeException("API error")))

        musicianViewModel.fetchMusicianById(musicianId)
        testDispatcher.scheduler.advanceUntilIdle()

        assertEquals("Error fetching musician details", musicianViewModel.errorMessage.first())
        assertThat(musicianViewModel.loading.first(), `is`(false))
    }

    @Test
    fun `Given musicians exist When filterMusicians is called with matching query Then filteredMusicians is updated`() = runTest {
        val musician1 = mock(MusicianSimpleDTO::class.java).apply { `when`(name).thenReturn("Musician One") }
        val musician2 = mock(MusicianSimpleDTO::class.java).apply { `when`(name).thenReturn("Musician Two") }
        val musician3 = mock(MusicianSimpleDTO::class.java).apply { `when`(name).thenReturn("Musician Three") }

        `when`(musicianRepository.fetchMusicians()).thenReturn(Result.success(listOf(musician1, musician2, musician3)))
        musicianViewModel.fetchMusicians()
        testDispatcher.scheduler.advanceUntilIdle()

        musicianViewModel.filterMusicians("One")
        testDispatcher.scheduler.advanceUntilIdle()

        assertEquals(listOf(musician1), musicianViewModel.filteredMusicians.first())
    }

    @Test
    fun `Given musicians exist When filterMusicians is called with non-matching query Then filteredMusicians is empty`() = runTest {
        val musician1 = mock(MusicianSimpleDTO::class.java).apply { `when`(name).thenReturn("Musician One") }
        val musician2 = mock(MusicianSimpleDTO::class.java).apply { `when`(name).thenReturn("Musician Two") }
        val musician3 = mock(MusicianSimpleDTO::class.java).apply { `when`(name).thenReturn("Musician Three") }

        `when`(musicianRepository.fetchMusicians()).thenReturn(Result.success(listOf(musician1, musician2, musician3)))
        musicianViewModel.fetchMusicians()
        testDispatcher.scheduler.advanceUntilIdle()

        musicianViewModel.filterMusicians("Non-existing musician")
        testDispatcher.scheduler.advanceUntilIdle()

        assertTrue(musicianViewModel.filteredMusicians.first().isEmpty())
    }

    @Test
    fun `Given musicians exist When filterMusicians is called with blank query Then all musicians are shown`() = runTest {
        val musician1 = mock(MusicianSimpleDTO::class.java)
        val musician2 = mock(MusicianSimpleDTO::class.java)
        `when`(musicianRepository.fetchMusicians()).thenReturn(Result.success(listOf(musician1, musician2)))

        musicianViewModel.fetchMusicians()
        testDispatcher.scheduler.advanceUntilIdle()

        musicianViewModel.filterMusicians("")
        testDispatcher.scheduler.advanceUntilIdle()

        assertEquals(listOf(musician1, musician2), musicianViewModel.filteredMusicians.first())
    }
}
