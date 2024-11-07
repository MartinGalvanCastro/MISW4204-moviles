package com.example.vinilosapp.repository

import com.example.models.PrizeDetailDTO
import com.example.vinilosapp.services.adapters.PremioServiceAdapter
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
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class PrizeRepositoryTest {

    @Mock
    private lateinit var premioServiceAdapter: PremioServiceAdapter

    @Mock
    private lateinit var networkChecker: NetworkChecker

    @InjectMocks
    private lateinit var prizeRepository: PrizeRepository

    private val prizeId = "1"

    @Test
    fun `Given network is connected and service success When fetchPrizeById is called Then it should return prize data`() = runTest {
        val mockPrize = mock(PrizeDetailDTO::class.java)
        `when`(networkChecker.isConnected()).thenReturn(true)
        `when`(premioServiceAdapter.getPremioById(prizeId)).thenReturn(Result.success(mockPrize))

        val result = prizeRepository.fetchById(prizeId)

        assertTrue(result.isSuccess)
        assertEquals(mockPrize, result.getOrNull())
    }

    @Test
    fun `Given no network connection When fetchPrizeById is called Then it should return network failure`() = runBlocking {
        `when`(networkChecker.isConnected()).thenReturn(false)

        val result = prizeRepository.fetchById(prizeId)

        assertTrue(result.isFailure)
        assertEquals("No internet connection", result.exceptionOrNull()?.message)
    }

    @Test
    fun `Given multiple prize IDs When fetchPrizes is called with network Then it should return results only for successful prizes`() = runTest {
        val prizeIds = listOf("1", "2", "3")
        val mockPrize = mock(PrizeDetailDTO::class.java)

        `when`(networkChecker.isConnected()).thenReturn(true)
        `when`(premioServiceAdapter.getPremioById("1")).thenReturn(Result.success(mockPrize))
        `when`(premioServiceAdapter.getPremioById("2")).thenReturn(Result.failure(RuntimeException("Service error")))
        `when`(premioServiceAdapter.getPremioById("3")).thenReturn(Result.success(mockPrize))

        val results = prizeRepository.fetchPrizes(prizeIds)

        assertEquals(2, results.size) // Only successful prizes should be included
        results.forEach { prize ->
            assertEquals(mockPrize, prize)
        }
    }

    @Test
    fun `Given all prize fetches fail When fetchPrizes is called Then it should return an empty list`() = runTest {
        val prizeIds = listOf("1", "2", "3")
        val exception = RuntimeException("Service error")

        `when`(networkChecker.isConnected()).thenReturn(true)
        prizeIds.forEach { id ->
            `when`(premioServiceAdapter.getPremioById(id)).thenReturn(Result.failure(exception))
        }

        val results = prizeRepository.fetchPrizes(prizeIds)

        assertTrue(results.isEmpty()) // No successful fetches, so the result should be empty
    }

    @Test
    fun `Given no network When fetchPrizes is called Then it should return an empty list`() = runBlocking {
        val prizeIds = listOf("1", "2", "3")
        `when`(networkChecker.isConnected()).thenReturn(false)

        val results = prizeRepository.fetchPrizes(prizeIds)

        assertTrue(results.isEmpty()) // No network, so the result should be empty
    }
}
