package com.example.vinilosapp.repository

import com.example.models.AlbumDetailDTO
import com.example.vinilosapp.di.Cache
import com.example.vinilosapp.models.AlbumDetail
import com.example.vinilosapp.models.AlbumSimple
import com.example.vinilosapp.services.adapters.AlbumServiceAdapter
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
class AlbumRepositoryTest {

    @Mock
    private lateinit var albumServiceAdapter: AlbumServiceAdapter

    @Mock
    private lateinit var networkChecker: NetworkChecker

    @Mock
    private lateinit var cache: Cache

    @InjectMocks
    private lateinit var albumRepository: AlbumRepository

    @Test
    fun `Given cached data When fetchAll is called Then it should return cached data`() {
        runBlocking {
            val mockAlbums = listOf(mock(AlbumSimple::class.java), mock(AlbumSimple::class.java))
            `when`(cache.getList<AlbumSimple>("list-AlbumSimple")).thenReturn(mockAlbums)

            val result = albumRepository.fetchAll()

            assertTrue(result.isSuccess)
            assertEquals(mockAlbums, result.getOrNull())
            verify(albumServiceAdapter, never()).getAllAlbums()
        }
    }

    @Test
    fun `Given no cached data and network success When fetchAll is called Then it should fetch from service and cache the result`() {
        runBlocking {
            val mockAlbums = listOf(mock(AlbumSimple::class.java), mock(AlbumSimple::class.java))
            `when`(cache.getList<AlbumSimple>("list-AlbumSimple")).thenReturn(null)
            `when`(networkChecker.isConnected()).thenReturn(true)
            `when`(albumServiceAdapter.getAllAlbums()).thenReturn(Result.success(mockAlbums))

            val result = albumRepository.fetchAll()

            assertTrue(result.isSuccess)
            assertEquals(mockAlbums, result.getOrNull())
            verify(albumServiceAdapter).getAllAlbums()
            verify(cache).putList("list-AlbumSimple", mockAlbums)
        }
    }

    @Test
    fun `Given no network connection When fetchAll is called Then it should return network failure`() {
        runBlocking {
            `when`(cache.getList<AlbumSimple>("list-AlbumSimple")).thenReturn(null)
            `when`(networkChecker.isConnected()).thenReturn(false)

            val result = albumRepository.fetchAll()

            assertTrue(result.isFailure)
            assertEquals("No internet connection", result.exceptionOrNull()?.message)
        }
    }

    @Test
    fun `Given cached data When fetchById is called Then it should return cached data`() {
        runBlocking {
            val albumId = "1"
            val detailKey = "detail-AlbumDetailDTO-1"
            val mockAlbumDetail = mock(AlbumDetailDTO::class.java)
            `when`(cache.getDetail<AlbumDetailDTO>(detailKey)).thenReturn(mockAlbumDetail)

            val result = albumRepository.fetchById(albumId)

            assertTrue(result.isSuccess)
            assertEquals(mockAlbumDetail, result.getOrNull())
            verify(albumServiceAdapter, never()).getAlbumById(albumId)
        }
    }

    @Test
    fun `Given no cached data and network success When fetchById is called Then it should fetch from service and cache the result`() {
        runBlocking {
            val albumId = "1"
            val detailKey = "detail-AlbumDetailDTO-1"
            val mockAlbumDetail = mock(AlbumDetail::class.java)
            `when`(cache.getDetail<AlbumDetail>(detailKey)).thenReturn(null)
            `when`(networkChecker.isConnected()).thenReturn(true)
            `when`(albumServiceAdapter.getAlbumById(albumId)).thenReturn(Result.success(mockAlbumDetail))

            val result = albumRepository.fetchById(albumId)

            assertTrue(result.isSuccess)
            assertEquals(mockAlbumDetail, result.getOrNull())
            verify(albumServiceAdapter).getAlbumById(albumId)
            verify(cache).putDetail(detailKey, mockAlbumDetail)
        }
    }

    @Test
    fun `Given no network connection When fetchById is called Then it should return network failure`() {
        runBlocking {
            val albumId = "1"
            val detailKey = "detail-AlbumDetailDTO-1"
            `when`(cache.getDetail<AlbumDetailDTO>(detailKey)).thenReturn(null)
            `when`(networkChecker.isConnected()).thenReturn(false)

            val result = albumRepository.fetchById(albumId)

            assertTrue(result.isFailure)
            assertEquals("No internet connection", result.exceptionOrNull()?.message)
        }
    }

    @Test
    fun `Given cached data When createAlbum is called Then cache should be cleared`() = runBlocking {
        val newAlbum = mock(AlbumSimple::class.java)
        `when`(networkChecker.isConnected()).thenReturn(true)
        `when`(albumServiceAdapter.createAlbum(newAlbum)).thenReturn(Result.success(newAlbum))

        val result = albumRepository.createAlbum(newAlbum)

        assertTrue(result.isSuccess)
        assertEquals(newAlbum, result.getOrNull())
        verify(cache).clear() // Verifies that the cache.clear() method is called
    }

    @Test
    fun `Given network is connected and service success When createAlbum is called Then it should return created album`() {
        runBlocking {
            val newAlbum = mock(AlbumSimple::class.java)
            `when`(networkChecker.isConnected()).thenReturn(true)
            `when`(albumServiceAdapter.createAlbum(newAlbum)).thenReturn(Result.success(newAlbum))

            val result = albumRepository.createAlbum(newAlbum)

            assertTrue(result.isSuccess)
            assertEquals(newAlbum, result.getOrNull())
        }
    }

    @Test
    fun `Given no network connection When createAlbum is called Then it should return network failure`() {
        runBlocking {
            val newAlbum = mock(AlbumSimple::class.java)
            `when`(networkChecker.isConnected()).thenReturn(false)

            val result = albumRepository.createAlbum(newAlbum)

            assertTrue(result.isFailure)
            assertEquals("No internet connection", result.exceptionOrNull()?.message)
        }
    }
}
