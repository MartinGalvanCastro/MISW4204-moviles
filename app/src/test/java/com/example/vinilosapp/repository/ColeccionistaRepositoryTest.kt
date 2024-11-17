package com.example.vinilosapp.repository

import com.example.models.CollectorDetailDTO
import com.example.models.CollectorSimpleDTO
import com.example.vinilosapp.di.Cache
import com.example.vinilosapp.services.adapters.ColecionistaServiceAdapter
import com.example.vinilosapp.utils.NetworkChecker
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.mock
import org.mockito.Mockito.never
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class ColeccionistaRepositoryTest {

    @Mock
    private lateinit var coleccionistaServiceAdapter: ColecionistaServiceAdapter

    @Mock
    private lateinit var networkChecker: NetworkChecker

    @Mock
    private lateinit var cache: Cache

    @InjectMocks
    private lateinit var coleccionistaRepository: ColeccionistaRepository

    @Test
    fun fetchAllItemsWithCachedData() = runTest {
        val cacheKey = "list-CollectorSimpleDTO"
        val mockCollectors = listOf(mock(CollectorSimpleDTO::class.java), mock(CollectorSimpleDTO::class.java))
        `when`(cache.getList<CollectorSimpleDTO>(cacheKey)).thenReturn(mockCollectors)

        val result = coleccionistaRepository.fetchAll()

        assertTrue(result.isSuccess)
        assertEquals(mockCollectors, result.getOrNull())
        verify(coleccionistaServiceAdapter, never()).getColecionistas()
    }

    @Test
    fun fetchAllItemsWithoutCachedData() = runTest {
        val cacheKey = "list-CollectorSimpleDTO"
        val mockCollectors = listOf(mock(CollectorSimpleDTO::class.java), mock(CollectorSimpleDTO::class.java))
        `when`(cache.getList<CollectorSimpleDTO>(cacheKey)).thenReturn(null)
        `when`(networkChecker.isConnected()).thenReturn(true)
        `when`(coleccionistaServiceAdapter.getColecionistas()).thenReturn(Result.success(mockCollectors))

        val result = coleccionistaRepository.fetchAll()

        assertTrue(result.isSuccess)
        assertEquals(mockCollectors, result.getOrNull())
        verify(coleccionistaServiceAdapter).getColecionistas()
        verify(cache).putList(cacheKey, mockCollectors)
    }

    @Test
    fun fetchAllItemsWithoutNetwork() = runBlocking {
        val cacheKey = "list-CollectorSimpleDTO"
        `when`(cache.getList<CollectorSimpleDTO>(cacheKey)).thenReturn(null)
        `when`(networkChecker.isConnected()).thenReturn(false)

        val result = coleccionistaRepository.fetchAll()

        assertTrue(result.isFailure)
        assertEquals("No internet connection", result.exceptionOrNull()?.message)
    }

    @Test
    fun fetchItemByIdWithCachedData() = runTest {
        val collectorId = "1"
        val cacheKey = "detail-CollectorDetailDTO-$collectorId"
        val mockCollectorDetail = mock(CollectorDetailDTO::class.java)
        `when`(cache.getDetail<CollectorDetailDTO>(cacheKey)).thenReturn(mockCollectorDetail)

        val result = coleccionistaRepository.fetchById(collectorId)

        assertTrue(result.isSuccess)
        assertEquals(mockCollectorDetail, result.getOrNull())
        verify(coleccionistaServiceAdapter, never()).getColecionistaById(collectorId)
    }

    @Test
    fun fetchItemByIdWithoutCachedData() = runTest {
        val collectorId = "1"
        val cacheKey = "detail-CollectorDetailDTO-$collectorId"
        val mockCollectorDetail = mock(CollectorDetailDTO::class.java)
        `when`(cache.getDetail<CollectorDetailDTO>(cacheKey)).thenReturn(null)
        `when`(networkChecker.isConnected()).thenReturn(true)
        `when`(coleccionistaServiceAdapter.getColecionistaById(collectorId)).thenReturn(Result.success(mockCollectorDetail))

        val result = coleccionistaRepository.fetchById(collectorId)

        assertTrue(result.isSuccess)
        assertEquals(mockCollectorDetail, result.getOrNull())
        verify(coleccionistaServiceAdapter).getColecionistaById(collectorId)
        verify(cache).putDetail(cacheKey, mockCollectorDetail)
    }

    @Test
    fun fetchItemByIdWithoutNetwork() = runBlocking {
        val collectorId = "1"
        val cacheKey = "detail-CollectorDetailDTO-$collectorId"
        `when`(cache.getDetail<CollectorDetailDTO>(cacheKey)).thenReturn(null)
        `when`(networkChecker.isConnected()).thenReturn(false)

        val result = coleccionistaRepository.fetchById(collectorId)

        assertTrue(result.isFailure)
        assertEquals("No internet connection", result.exceptionOrNull()?.message)
    }
}
