package com.example.vinilosapp.repository

import com.example.models.CollectorDetailDTO
import com.example.models.CollectorSimpleDTO
import com.example.vinilosapp.services.adapters.ColecionistaServiceAdapter
import com.example.vinilosapp.utils.NetworkChecker
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner
import java.math.BigDecimal

@RunWith(MockitoJUnitRunner::class)
class ColeccionistaRepositoryTest {

    @Mock
    private lateinit var coleccionistaServiceAdapter: ColecionistaServiceAdapter

    @Mock
    private lateinit var networkChecker: NetworkChecker

    @InjectMocks
    private lateinit var coleccionistaRepository: ColeccionistaRepository

    @Test
    fun `Given network is connected and service success When fetchAllItems is called Then it should return a list of collectors`() = runBlocking {
        val mockCollectors = listOf(mock(CollectorSimpleDTO::class.java), mock(CollectorSimpleDTO::class.java))
        `when`(networkChecker.isConnected()).thenReturn(true)
        `when`(coleccionistaServiceAdapter.getColecionistas()).thenReturn(Result.success(mockCollectors))

        val result = coleccionistaRepository.fetchAll()

        assertTrue(result.isSuccess)
        assertEquals(mockCollectors, result.getOrNull())
    }

    @Test
    fun `Given network is connected and service failure When fetchAllItems is called Then it should return failure`() = runBlocking {
        val exception = RuntimeException("Service error")
        `when`(networkChecker.isConnected()).thenReturn(true)
        `when`(coleccionistaServiceAdapter.getColecionistas()).thenReturn(Result.failure(exception))

        val result = coleccionistaRepository.fetchAll()

        assertTrue(result.isFailure)
        assertEquals(exception, result.exceptionOrNull())
    }

    @Test
    fun `Given no network connection When fetchAllItems is called Then it should return network failure`() = runBlocking {
        `when`(networkChecker.isConnected()).thenReturn(false)

        val result = coleccionistaRepository.fetchAll()

        assertTrue(result.isFailure)
        assertEquals("No internet connection", result.exceptionOrNull()?.message)
    }

    @Test
    fun `Given network is connected and service success When fetchItemById is called Then it should return collector details`() = runBlocking {
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
        `when`(networkChecker.isConnected()).thenReturn(true)
        `when`(coleccionistaServiceAdapter.getColecionistaById(collectorId)).thenReturn(Result.success(mockCollectorDetail))

        val result = coleccionistaRepository.fetchById(collectorId)

        assertTrue(result.isSuccess)
        assertEquals(mockCollectorDetail, result.getOrNull())
    }

    @Test
    fun `Given network is connected and service failure When fetchItemById is called Then it should return failure`() = runBlocking {
        val collectorId = "1"
        val exception = RuntimeException("Service error")
        `when`(networkChecker.isConnected()).thenReturn(true)
        `when`(coleccionistaServiceAdapter.getColecionistaById(collectorId)).thenReturn(Result.failure(exception))

        val result = coleccionistaRepository.fetchById(collectorId)

        assertTrue(result.isFailure)
        assertEquals(exception, result.exceptionOrNull())
    }

    @Test
    fun `Given no network connection When fetchItemById is called Then it should return network failure`() = runBlocking {
        val collectorId = "1"
        `when`(networkChecker.isConnected()).thenReturn(false)

        val result = coleccionistaRepository.fetchById(collectorId)

        assertTrue(result.isFailure)
        assertEquals("No internet connection", result.exceptionOrNull()?.message)
    }
}
