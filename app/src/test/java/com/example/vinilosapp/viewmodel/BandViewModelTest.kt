package com.example.vinilosapp.viewmodel

import com.example.models.BandDetailDTO
import com.example.models.BandSimpleDTO
import com.example.vinilosapp.repository.BandRepository
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
class BandViewModelTest {

    @Mock
    private lateinit var bandRepository: BandRepository

    @InjectMocks
    private lateinit var bandViewModel: BandViewModel

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
    fun `Given successful repository response When fetchBands is called Then bands are updated`() = runTest {
        val mockBandList = listOf(mock(BandSimpleDTO::class.java), mock(BandSimpleDTO::class.java))
        `when`(bandRepository.fetchBands()).thenReturn(Result.success(mockBandList))

        bandViewModel.fetchBands()
        testDispatcher.scheduler.advanceUntilIdle()

        assertEquals(mockBandList, bandViewModel.filteredBands.first())
        assertThat(bandViewModel.loading.first(), `is`(false))
    }

    @Test
    fun `Given repository failure When fetchBands is called Then errorMessage is set`() = runTest {
        `when`(bandRepository.fetchBands()).thenReturn(Result.failure(RuntimeException("API error")))

        bandViewModel.fetchBands()
        testDispatcher.scheduler.advanceUntilIdle()

        assertEquals("Error fetching bands", bandViewModel.errorMessage.first())
        assertThat(bandViewModel.loading.first(), `is`(false))
    }

    @Test
    fun `Given successful repository response When fetchBandById is called Then band is updated`() = runTest {
        val bandId = "1"
        val mockBandDetail = BandDetailDTO(
            id = BigDecimal(bandId),
            name = "Test Band",
            description = "Description",
            albums = emptyList(),
            musicians = emptyList(),
            image = "",
            creationDate = "",
            collectors = emptyList(),
            performerPrizes = emptyList(),
        )
        `when`(bandRepository.fetchBandById(bandId)).thenReturn(Result.success(mockBandDetail))

        bandViewModel.fetchBandById(bandId)
        testDispatcher.scheduler.advanceUntilIdle()

        assertEquals(mockBandDetail, bandViewModel.band.first())
        assertThat(bandViewModel.loading.first(), `is`(false))
    }

    @Test
    fun `Given repository failure When fetchBandById is called Then errorMessage is set`() = runTest {
        val bandId = "1"
        `when`(bandRepository.fetchBandById(bandId)).thenReturn(Result.failure(RuntimeException("API error")))

        bandViewModel.fetchBandById(bandId)
        testDispatcher.scheduler.advanceUntilIdle()

        assertEquals("Error fetching band details", bandViewModel.errorMessage.first())
        assertThat(bandViewModel.loading.first(), `is`(false))
    }

    @Test
    fun `Given bands exist When filterBands is called with matching query Then filteredBands is updated`() = runTest {
        val band1 = mock(BandSimpleDTO::class.java).apply { `when`(name).thenReturn("Band One") }
        val band2 = mock(BandSimpleDTO::class.java).apply { `when`(name).thenReturn("Band Two") }
        val band3 = mock(BandSimpleDTO::class.java).apply { `when`(name).thenReturn("Band Three") }

        `when`(bandRepository.fetchBands()).thenReturn(Result.success(listOf(band1, band2, band3)))
        bandViewModel.fetchBands()
        testDispatcher.scheduler.advanceUntilIdle()

        bandViewModel.filterBands("One")
        testDispatcher.scheduler.advanceUntilIdle()

        assertEquals(listOf(band1), bandViewModel.filteredBands.first())
    }

    @Test
    fun `Given bands exist When filterBands is called with non-matching query Then filteredBands is empty`() = runTest {
        val band1 = mock(BandSimpleDTO::class.java).apply { `when`(name).thenReturn("Band One") }
        val band2 = mock(BandSimpleDTO::class.java).apply { `when`(name).thenReturn("Band Two") }
        val band3 = mock(BandSimpleDTO::class.java).apply { `when`(name).thenReturn("Band Three") }

        `when`(bandRepository.fetchBands()).thenReturn(Result.success(listOf(band1, band2, band3)))
        bandViewModel.fetchBands()
        testDispatcher.scheduler.advanceUntilIdle()

        bandViewModel.filterBands("Non-existing band")
        testDispatcher.scheduler.advanceUntilIdle()

        assertTrue(bandViewModel.filteredBands.first().isEmpty())
    }

    @Test
    fun `Given bands exist When filterBands is called with blank query Then all bands are shown`() = runTest {
        val band1 = mock(BandSimpleDTO::class.java)
        val band2 = mock(BandSimpleDTO::class.java)
        `when`(bandRepository.fetchBands()).thenReturn(Result.success(listOf(band1, band2)))

        bandViewModel.fetchBands()
        testDispatcher.scheduler.advanceUntilIdle()

        bandViewModel.filterBands("")
        testDispatcher.scheduler.advanceUntilIdle()

        assertEquals(listOf(band1, band2), bandViewModel.filteredBands.first())
    }
}
