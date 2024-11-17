package com.example.vinilosapp.repository

import com.example.models.BandDetailDTO
import com.example.models.BandSimpleDTO
import com.example.vinilosapp.di.Cache
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
import org.mockito.Mockito.never
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class BandRepositoryTest {

    @Mock
    private lateinit var bandServiceAdapter: BandServiceAdapter

    @Mock
    private lateinit var networkChecker: NetworkChecker

    @Mock
    private lateinit var cache: Cache

    @InjectMocks
    private lateinit var bandRepository: BandRepository

    @Test
    fun `Given cached data When fetchAll is called Then it should return cached data`() {
        runBlocking {
            val mockBands = listOf(mock(BandSimpleDTO::class.java), mock(BandSimpleDTO::class.java))
            `when`(cache.getList<BandSimpleDTO>("list-BandSimpleDTO")).thenReturn(mockBands)

            val result = bandRepository.fetchAll()

            assertTrue(result.isSuccess)
            assertEquals(mockBands, result.getOrNull())
            verify(bandServiceAdapter, never()).getBands()
        }
    }

    @Test
    fun `Given no cached data and network success When fetchAll is called Then it should fetch from service and cache the result`() {
        runBlocking {
            val mockBands = listOf(mock(BandSimpleDTO::class.java), mock(BandSimpleDTO::class.java))
            `when`(cache.getList<BandSimpleDTO>("list-BandSimpleDTO")).thenReturn(null)
            `when`(networkChecker.isConnected()).thenReturn(true)
            `when`(bandServiceAdapter.getBands()).thenReturn(Result.success(mockBands))

            val result = bandRepository.fetchAll()

            assertTrue(result.isSuccess)
            assertEquals(mockBands, result.getOrNull())
            verify(bandServiceAdapter).getBands()
            verify(cache).putList("list-BandSimpleDTO", mockBands)
        }
    }

    @Test
    fun `Given cached data When fetchById is called Then it should return cached data`() {
        runBlocking {
            val bandId = "1"
            val detailKey = "detail-BandDetailDTO-$bandId"
            val mockBandDetail = mock(BandDetailDTO::class.java)
            `when`(cache.getDetail<BandDetailDTO>(detailKey)).thenReturn(mockBandDetail)

            val result = bandRepository.fetchById(bandId)

            assertTrue(result.isSuccess)
            assertEquals(mockBandDetail, result.getOrNull())
            verify(bandServiceAdapter, never()).getBandById(bandId)
        }
    }

    @Test
    fun `Given no cached data and network success When fetchById is called Then it should fetch from service and cache the result`() {
        runBlocking {
            val bandId = "1"
            val detailKey = "detail-BandDetailDTO-$bandId"
            val mockBandDetail = mock(BandDetailDTO::class.java)
            `when`(cache.getDetail<BandDetailDTO>(detailKey)).thenReturn(null)
            `when`(networkChecker.isConnected()).thenReturn(true)
            `when`(bandServiceAdapter.getBandById(bandId)).thenReturn(Result.success(mockBandDetail))

            val result = bandRepository.fetchById(bandId)

            assertTrue(result.isSuccess)
            assertEquals(mockBandDetail, result.getOrNull())
            verify(bandServiceAdapter).getBandById(bandId)
            verify(cache).putDetail(detailKey, mockBandDetail)
        }
    }

    @Test
    fun `Given no network connection When fetchById is called Then it should return network failure`() {
        runBlocking {
            val bandId = "1"
            val detailKey = "detail-BandDetailDTO-$bandId"
            `when`(cache.getDetail<BandDetailDTO>(detailKey)).thenReturn(null)
            `when`(networkChecker.isConnected()).thenReturn(false)

            val result = bandRepository.fetchById(bandId)

            assertTrue(result.isFailure)
            assertEquals("No internet connection", result.exceptionOrNull()?.message)
        }
    }

    @Test
    fun `Given cached data When clearCache is called Then cache is cleared`() {
        runBlocking {
            bandRepository.clearCache()
            verify(cache).clear()
        }
    }
}
