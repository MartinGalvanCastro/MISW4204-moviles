package com.example.vinilosapp.viewmodel

import com.example.models.BandDetailDTO
import com.example.models.BandSimpleDTO
import com.example.models.PrizeDetailDTO
import com.example.vinilosapp.repository.BandRepository
import com.example.vinilosapp.repository.PrizeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
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
class BandViewModelTest {

    @Mock
    private lateinit var bandRepository: BandRepository

    @Mock
    private lateinit var prizeRepository: PrizeRepository

    private lateinit var bandViewModel: BandViewModel

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)

        Dispatchers.setMain(testDispatcher)

        bandViewModel = BandViewModel(
            bandRepository = bandRepository,
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
    fun `Given successful repository response When fetchAllItems is called Then items are updated`() = runBlocking {
        val mockBandList = listOf(mock(BandSimpleDTO::class.java), mock(BandSimpleDTO::class.java))
        `when`(bandRepository.fetchAll()).thenReturn(Result.success(mockBandList))

        bandViewModel.fetchAllItems()
        testDispatcher.scheduler.advanceUntilIdle()

        assertEquals(mockBandList, bandViewModel.state.value.filteredItems)
        assertThat(bandViewModel.state.value.isLoading, `is`(false))
    }

    @Test
    fun `Given repository failure When fetchAllItems is called Then errorMessage is set`() = runBlocking {
        `when`(bandRepository.fetchAll()).thenReturn(Result.failure(RuntimeException("API error")))

        bandViewModel.fetchAllItems()
        testDispatcher.scheduler.advanceUntilIdle()

        assertEquals("API error", bandViewModel.state.value.errorMessage)
        assertThat(bandViewModel.state.value.isLoading, `is`(false))
    }

    @Test
    fun `Given successful repository response When fetchDetailById is called Then detail is updated`() = runBlocking {
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
        `when`(bandRepository.fetchById(bandId)).thenReturn(Result.success(mockBandDetail))

        bandViewModel.fetchDetailById(bandId)
        testDispatcher.scheduler.advanceUntilIdle()

        assertEquals(mockBandDetail, bandViewModel.state.value.detail)
        assertThat(bandViewModel.state.value.isLoading, `is`(false))
    }

    @Test
    fun `Given repository failure When fetchDetailById is called Then errorMessage is set`() = runBlocking {
        val bandId = "1"
        `when`(bandRepository.fetchById(bandId)).thenReturn(Result.failure(RuntimeException("API error")))

        bandViewModel.fetchDetailById(bandId)
        testDispatcher.scheduler.advanceUntilIdle()

        assertEquals("API error", bandViewModel.state.value.errorMessage)
        assertThat(bandViewModel.state.value.isLoading, `is`(false))
    }

    @Test
    fun `Given bands exist When filterBands is called with matching query Then filteredItems is updated`() = runBlocking {
        val band1 = mock(BandSimpleDTO::class.java).apply { `when`(name).thenReturn("Band One") }
        val band2 = mock(BandSimpleDTO::class.java).apply { `when`(name).thenReturn("Band Two") }
        val band3 = mock(BandSimpleDTO::class.java).apply { `when`(name).thenReturn("Band Three") }

        `when`(bandRepository.fetchAll()).thenReturn(Result.success(listOf(band1, band2, band3)))
        bandViewModel.fetchAllItems()
        testDispatcher.scheduler.advanceUntilIdle()

        bandViewModel.filterBands("One")
        testDispatcher.scheduler.advanceUntilIdle()

        assertEquals(listOf(band1), bandViewModel.state.value.filteredItems)
    }

    @Test
    fun `Given bands exist When filterBands is called with non-matching query Then filteredItems is empty`() = runBlocking {
        val band1 = mock(BandSimpleDTO::class.java).apply { `when`(name).thenReturn("Band One") }
        val band2 = mock(BandSimpleDTO::class.java).apply { `when`(name).thenReturn("Band Two") }
        val band3 = mock(BandSimpleDTO::class.java).apply { `when`(name).thenReturn("Band Three") }

        `when`(bandRepository.fetchAll()).thenReturn(Result.success(listOf(band1, band2, band3)))
        bandViewModel.fetchAllItems()
        testDispatcher.scheduler.advanceUntilIdle()

        bandViewModel.filterBands("Non-existing band")
        testDispatcher.scheduler.advanceUntilIdle()

        assertTrue(bandViewModel.state.value.filteredItems.isEmpty())
    }

    @Test
    fun `Given bands exist When filterBands is called with blank query Then all items are shown`() = runBlocking {
        val band1 = mock(BandSimpleDTO::class.java)
        val band2 = mock(BandSimpleDTO::class.java)
        `when`(bandRepository.fetchAll()).thenReturn(Result.success(listOf(band1, band2)))

        bandViewModel.fetchAllItems()
        testDispatcher.scheduler.advanceUntilIdle()

        bandViewModel.filterBands("")
        testDispatcher.scheduler.advanceUntilIdle()

        assertEquals(listOf(band1, band2), bandViewModel.state.value.filteredItems)
    }

    @Test
    fun `Given successful prize fetch When fetchPrizesForPerformer is called Then prizes are updated`() = runBlocking {
        val prizeIds = listOf("1", "2", "3")
        val mockPrizes = listOf(mock(PrizeDetailDTO::class.java), mock(PrizeDetailDTO::class.java))
        `when`(prizeRepository.fetchPrizes(prizeIds)).thenReturn(mockPrizes)

        bandViewModel.fetchPrizesForPerformer(prizeIds)
        testDispatcher.scheduler.advanceUntilIdle()

        assertEquals(mockPrizes, bandViewModel.prizesState.value.items)
        assertThat(bandViewModel.prizesState.value.isLoading, `is`(false))
    }

    @Test
    fun `Given prize fetch fails When fetchPrizesForPerformer is called Then errorMessage is set`() = runBlocking {
        val prizeIds = listOf("1", "2", "3")
        `when`(prizeRepository.fetchPrizes(prizeIds)).thenThrow(RuntimeException("API error"))

        bandViewModel.fetchPrizesForPerformer(prizeIds)
        testDispatcher.scheduler.advanceUntilIdle()

        assertEquals("API error", bandViewModel.prizesState.value.errorMessage)
        assertThat(bandViewModel.prizesState.value.isLoading, `is`(false))
    }
}
