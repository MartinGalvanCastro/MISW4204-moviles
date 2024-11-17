package com.example.vinilosapp.repository

import com.example.models.PrizeDetailDTO
import com.example.vinilosapp.di.Cache
import com.example.vinilosapp.services.adapters.PremioServiceAdapter
import com.example.vinilosapp.utils.NetworkChecker
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.anyString
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.mock
import org.mockito.Mockito.never
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class PrizeRepositoryTest {

    @Mock
    private lateinit var premioServiceAdapter: PremioServiceAdapter

    @Mock
    private lateinit var networkChecker: NetworkChecker

    @Mock
    private lateinit var cache: Cache

    @InjectMocks
    private lateinit var prizeRepository: PrizeRepository

    private val prizeId = "1"

    @Test
    fun testFetchPrizeByIdWithCachedData() = runTest {
        val detailKey = "detail-PrizeDetailDTO-$prizeId"
        val mockPrize = mock(PrizeDetailDTO::class.java)
        `when`(cache.getDetail<PrizeDetailDTO>(detailKey)).thenReturn(mockPrize)

        val result = prizeRepository.fetchById(prizeId)

        assertTrue(result.isSuccess)
        assertEquals(mockPrize, result.getOrNull())
        verify(premioServiceAdapter, never()).getPremioById(prizeId)
    }

    @Test
    fun testFetchPrizeByIdWithNoCachedDataAndNetworkSuccess() = runTest {
        val detailKey = "detail-PrizeDetailDTO-$prizeId"
        val mockPrize = mock(PrizeDetailDTO::class.java)
        `when`(cache.getDetail<PrizeDetailDTO>(detailKey)).thenReturn(null)
        `when`(networkChecker.isConnected()).thenReturn(true)
        `when`(premioServiceAdapter.getPremioById(prizeId)).thenReturn(Result.success(mockPrize))

        val result = prizeRepository.fetchById(prizeId)

        assertTrue(result.isSuccess)
        assertEquals(mockPrize, result.getOrNull())
        verify(premioServiceAdapter).getPremioById(prizeId)
        verify(cache).putDetail(detailKey, mockPrize)
    }

    @Test
    fun testFetchPrizeByIdWithNoNetwork() = runBlocking {
        val detailKey = "detail-PrizeDetailDTO-$prizeId"
        `when`(cache.getDetail<PrizeDetailDTO>(detailKey)).thenReturn(null)
        `when`(networkChecker.isConnected()).thenReturn(false)

        val result = prizeRepository.fetchById(prizeId)

        assertTrue(result.isFailure)
        assertEquals("No internet connection", result.exceptionOrNull()?.message)
    }

    @Test
    fun testFetchPrizesWithCachedData() = runTest {
        val prizeIds = listOf("1", "2")
        val cachedPrize1 = mock(PrizeDetailDTO::class.java)
        val cachedPrize2 = mock(PrizeDetailDTO::class.java)

        `when`(cache.getDetail<PrizeDetailDTO>("detail-PrizeDetailDTO-1")).thenReturn(cachedPrize1)
        `when`(cache.getDetail<PrizeDetailDTO>("detail-PrizeDetailDTO-2")).thenReturn(cachedPrize2)

        val results = prizeRepository.fetchPrizes(prizeIds)

        assertEquals(2, results.size)
        assertTrue(results.containsAll(listOf(cachedPrize1, cachedPrize2)))
        verify(premioServiceAdapter, never()).getPremioById(anyString())
    }

    @Test
    fun testFetchPrizesWithNoCachedDataAndNetworkSuccess() = runTest {
        val prizeIds = listOf("1", "2", "3")
        val mockPrize = mock(PrizeDetailDTO::class.java)

        `when`(cache.getDetail<PrizeDetailDTO>(anyString())).thenReturn(null)
        `when`(networkChecker.isConnected()).thenReturn(true)
        `when`(premioServiceAdapter.getPremioById(anyString())).thenReturn(Result.success(mockPrize))

        val results = prizeRepository.fetchPrizes(prizeIds)

        assertEquals(3, results.size)
        assertTrue(results.all { it == mockPrize })
        prizeIds.forEach { verify(cache).putDetail("detail-PrizeDetailDTO-$it", mockPrize) }
    }

    @Test
    fun testFetchPrizesWithNoNetwork() = runBlocking {
        val prizeIds = listOf("1", "2", "3")
        `when`(networkChecker.isConnected()).thenReturn(false)

        val results = prizeRepository.fetchPrizes(prizeIds)

        assertTrue(results.isEmpty())
    }

    @Test
    fun testFetchPrizesWithPartialNetworkSuccess() = runTest {
        val prizeIds = listOf("1", "2", "3")
        val mockPrize1 = mock(PrizeDetailDTO::class.java)
        val mockPrize3 = mock(PrizeDetailDTO::class.java)

        `when`(networkChecker.isConnected()).thenReturn(true)
        `when`(premioServiceAdapter.getPremioById("1")).thenReturn(Result.success(mockPrize1))
        `when`(premioServiceAdapter.getPremioById("2")).thenReturn(Result.failure(RuntimeException("Error")))
        `when`(premioServiceAdapter.getPremioById("3")).thenReturn(Result.success(mockPrize3))

        val results = prizeRepository.fetchPrizes(prizeIds)

        assertEquals(2, results.size)
        assertTrue(results.containsAll(listOf(mockPrize1, mockPrize3)))
    }
}
