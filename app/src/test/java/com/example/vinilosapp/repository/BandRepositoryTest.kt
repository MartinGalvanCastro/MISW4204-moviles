package com.example.vinilosapp.repository

import com.example.models.BandDetailDTO
import com.example.models.BandSimpleDTO
import com.example.vinilosapp.services.adapters.BandServiceAdapter
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
class BandRepositoryTest {

    @Mock
    private lateinit var bandServiceAdapter: BandServiceAdapter

    @Mock
    private lateinit var networkChecker: NetworkChecker

    @InjectMocks
    private lateinit var bandRepository: BandRepository

    @Test
    fun `Given network is connected and service success When fetchAll is called Then it should return a list of bands`() = runBlocking {
        val mockBands = listOf(mock(BandSimpleDTO::class.java), mock(BandSimpleDTO::class.java))
        `when`(networkChecker.isConnected()).thenReturn(true)
        `when`(bandServiceAdapter.getBands()).thenReturn(Result.success(mockBands))

        val result = bandRepository.fetchAll()

        assertTrue(result.isSuccess)
        assertEquals(mockBands, result.getOrNull())
    }

    @Test
    fun `Given network is connected and service failure When fetchAll is called Then it should return failure`() = runBlocking {
        val exception = RuntimeException("Service error")
        `when`(networkChecker.isConnected()).thenReturn(true)
        `when`(bandServiceAdapter.getBands()).thenReturn(Result.failure(exception))

        val result = bandRepository.fetchAll()

        assertTrue(result.isFailure)
        assertEquals(exception, result.exceptionOrNull())
    }

    @Test
    fun `Given no network connection When fetchAll is called Then it should return network failure`() = runBlocking {
        `when`(networkChecker.isConnected()).thenReturn(false)

        val result = bandRepository.fetchAll()

        assertTrue(result.isFailure)
        assertEquals("No internet connection", result.exceptionOrNull()?.message)
    }

    @Test
    fun `Given network is connected and service success When fetchById is called Then it should return band details`() = runBlocking {
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
        `when`(networkChecker.isConnected()).thenReturn(true)
        `when`(bandServiceAdapter.getBandById(bandId)).thenReturn(Result.success(mockBandDetail))

        val result = bandRepository.fetchById(bandId)

        assertTrue(result.isSuccess)
        assertEquals(mockBandDetail, result.getOrNull())
    }

    @Test
    fun `Given network is connected and service failure When fetchById is called Then it should return failure`() = runBlocking {
        val bandId = "1"
        val exception = RuntimeException("Service error")
        `when`(networkChecker.isConnected()).thenReturn(true)
        `when`(bandServiceAdapter.getBandById(bandId)).thenReturn(Result.failure(exception))

        val result = bandRepository.fetchById(bandId)

        assertTrue(result.isFailure)
        assertEquals(exception, result.exceptionOrNull())
    }

    @Test
    fun `Given no network connection When fetchById is called Then it should return network failure`() = runBlocking {
        val bandId = "1"
        `when`(networkChecker.isConnected()).thenReturn(false)

        val result = bandRepository.fetchById(bandId)

        assertTrue(result.isFailure)
        assertEquals("No internet connection", result.exceptionOrNull()?.message)
    }
}
