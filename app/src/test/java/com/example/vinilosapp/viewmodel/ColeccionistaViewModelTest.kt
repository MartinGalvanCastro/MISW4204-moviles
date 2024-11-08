package com.example.vinilosapp.viewmodel

import com.example.models.CollectorDetailDTO
import com.example.models.CollectorSimpleDTO
import com.example.vinilosapp.repository.ColeccionistaRepository
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
class ColeccionistaViewModelTest {

    @Mock
    private lateinit var coleccionistaRepository: ColeccionistaRepository

    @InjectMocks
    private lateinit var coleccionistaViewModel: ColeccionistaViewModel

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
    fun `Given successful repository response When fetchAllItems is called Then items are updated`() = runTest {
        val mockCollectorList = listOf(mock(CollectorSimpleDTO::class.java), mock(CollectorSimpleDTO::class.java))
        `when`(coleccionistaRepository.fetchAll()).thenReturn(Result.success(mockCollectorList))

        coleccionistaViewModel.fetchAllItems()
        testDispatcher.scheduler.advanceUntilIdle()

        assertEquals(mockCollectorList, coleccionistaViewModel.filteredItems.first())
        assertThat(coleccionistaViewModel.loading.first(), `is`(false))
    }

    @Test
    fun `Given repository failure When fetchAllItems is called Then errorMessage is set`() = runTest {
        `when`(coleccionistaRepository.fetchAll()).thenReturn(Result.failure(RuntimeException("API error")))

        coleccionistaViewModel.fetchAllItems()
        testDispatcher.scheduler.advanceUntilIdle()

        assertEquals("Error fetching items: API error", coleccionistaViewModel.errorMessage.first())
        assertThat(coleccionistaViewModel.loading.first(), `is`(false))
    }

    @Test
    fun `Given successful repository response When fetchDetailById is called Then detail is updated`() = runTest {
        val collectorId = "1"
        val mockCollectorDetail = CollectorDetailDTO(
            id = BigDecimal(collectorId),
            name = "Test Collector",
            email = "collector@test.com",
            telephone = "123456789",
            favoritePerformers = emptyList(),
            comments = emptyList(),
            collectorAlbums = emptyList(),
        )
        `when`(coleccionistaRepository.fetchById(collectorId)).thenReturn(Result.success(mockCollectorDetail))

        coleccionistaViewModel.fetchDetailById(collectorId)
        testDispatcher.scheduler.advanceUntilIdle()

        assertEquals(mockCollectorDetail, coleccionistaViewModel.detail.first())
        assertThat(coleccionistaViewModel.loading.first(), `is`(false))
    }

    @Test
    fun `Given repository failure When fetchDetailById is called Then errorMessage is set`() = runTest {
        val collectorId = "1"
        `when`(coleccionistaRepository.fetchById(collectorId)).thenReturn(Result.failure(RuntimeException("API error")))

        coleccionistaViewModel.fetchDetailById(collectorId)
        testDispatcher.scheduler.advanceUntilIdle()

        assertEquals("Error fetching item details: API error", coleccionistaViewModel.errorMessage.first())
        assertThat(coleccionistaViewModel.loading.first(), `is`(false))
    }

    @Test
    fun `Given collectors exist When filterCollectors is called with matching query Then filteredItems is updated`() = runTest {
        val collector1 = mock(CollectorSimpleDTO::class.java).apply { `when`(name).thenReturn("Collector One") }
        val collector2 = mock(CollectorSimpleDTO::class.java).apply { `when`(name).thenReturn("Collector Two") }
        val collector3 = mock(CollectorSimpleDTO::class.java).apply { `when`(name).thenReturn("Collector Three") }

        `when`(coleccionistaRepository.fetchAll()).thenReturn(Result.success(listOf(collector1, collector2, collector3)))
        coleccionistaViewModel.fetchAllItems()
        testDispatcher.scheduler.advanceUntilIdle()

        coleccionistaViewModel.filterCollectors("One")
        testDispatcher.scheduler.advanceUntilIdle()

        assertEquals(listOf(collector1), coleccionistaViewModel.filteredItems.first())
    }

    @Test
    fun `Given collectors exist When filterCollectors is called with non-matching query Then filteredItems is empty`() = runTest {
        val collector1 = mock(CollectorSimpleDTO::class.java).apply { `when`(name).thenReturn("Collector One") }
        val collector2 = mock(CollectorSimpleDTO::class.java).apply { `when`(name).thenReturn("Collector Two") }
        val collector3 = mock(CollectorSimpleDTO::class.java).apply { `when`(name).thenReturn("Collector Three") }

        `when`(coleccionistaRepository.fetchAll()).thenReturn(Result.success(listOf(collector1, collector2, collector3)))
        coleccionistaViewModel.fetchAllItems()
        testDispatcher.scheduler.advanceUntilIdle()

        coleccionistaViewModel.filterCollectors("Non-existing collector")
        testDispatcher.scheduler.advanceUntilIdle()

        assertTrue(coleccionistaViewModel.filteredItems.first().isEmpty())
    }

    @Test
    fun `Given collectors exist When filterCollectors is called with blank query Then all items are shown`() = runTest {
        val collector1 = mock(CollectorSimpleDTO::class.java)
        val collector2 = mock(CollectorSimpleDTO::class.java)
        `when`(coleccionistaRepository.fetchAll()).thenReturn(Result.success(listOf(collector1, collector2)))

        coleccionistaViewModel.fetchAllItems()
        testDispatcher.scheduler.advanceUntilIdle()

        coleccionistaViewModel.filterCollectors("")
        testDispatcher.scheduler.advanceUntilIdle()

        assertEquals(listOf(collector1, collector2), coleccionistaViewModel.filteredItems.first())
    }
}
