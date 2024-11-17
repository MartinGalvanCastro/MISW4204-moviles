package com.example.vinilosapp.viewmodel

import com.example.models.CollectorDetailDTO
import com.example.models.CollectorSimpleDTO
import com.example.vinilosapp.repository.ColeccionistaRepository
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
class ColeccionistaViewModelTest {

    @Mock
    private lateinit var coleccionistaRepository: ColeccionistaRepository

    private lateinit var coleccionistaViewModel: ColeccionistaViewModel

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(testDispatcher)

        coleccionistaViewModel = ColeccionistaViewModel(
            colecionistaRepository = coleccionistaRepository,
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
        val mockCollectorList = listOf(mock(CollectorSimpleDTO::class.java), mock(CollectorSimpleDTO::class.java))
        `when`(coleccionistaRepository.fetchAll()).thenReturn(Result.success(mockCollectorList))

        coleccionistaViewModel.fetchAllItems()
        testDispatcher.scheduler.advanceUntilIdle()

        assertEquals(mockCollectorList, coleccionistaViewModel.state.value.filteredItems)
        assertThat(coleccionistaViewModel.state.value.isLoading, `is`(false))
    }

    @Test
    fun `Given repository failure When fetchAllItems is called Then errorMessage is set`() = runTest {
        `when`(coleccionistaRepository.fetchAll()).thenReturn(Result.failure(RuntimeException("API error")))

        coleccionistaViewModel.fetchAllItems()
        testDispatcher.scheduler.advanceUntilIdle()

        assertEquals("API error", coleccionistaViewModel.state.value.errorMessage)
        assertThat(coleccionistaViewModel.state.value.isLoading, `is`(false))
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

        assertEquals(mockCollectorDetail, coleccionistaViewModel.state.value.detail)
        assertThat(coleccionistaViewModel.state.value.isLoading, `is`(false))
    }

    @Test
    fun `Given repository failure When fetchDetailById is called Then errorMessage is set`() = runTest {
        val collectorId = "1"
        `when`(coleccionistaRepository.fetchById(collectorId)).thenReturn(Result.failure(RuntimeException("API error")))

        coleccionistaViewModel.fetchDetailById(collectorId)
        testDispatcher.scheduler.advanceUntilIdle()

        assertEquals("API error", coleccionistaViewModel.state.value.errorMessage)
        assertThat(coleccionistaViewModel.state.value.isLoading, `is`(false))
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

        assertEquals(listOf(collector1), coleccionistaViewModel.state.value.filteredItems)
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

        assertTrue(coleccionistaViewModel.state.value.filteredItems.isEmpty())
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

        assertEquals(listOf(collector1, collector2), coleccionistaViewModel.state.value.filteredItems)
    }
}
