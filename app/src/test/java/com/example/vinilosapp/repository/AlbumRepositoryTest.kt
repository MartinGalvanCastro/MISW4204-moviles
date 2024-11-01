package com.example.vinilosapp.repository

import com.example.models.AlbumDetailDTO
import com.example.models.AlbumSimpleDTO
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
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner
import java.math.BigDecimal

@RunWith(MockitoJUnitRunner::class)
class AlbumRepositoryTest {

    @Mock
    private lateinit var albumServiceAdapter: AlbumServiceAdapter

    @Mock
    private lateinit var networkChecker: NetworkChecker

    @InjectMocks
    private lateinit var albumRepository: AlbumRepository

    @Test
    fun `Given network is connected and service success When fetchAlbums is called Then it should return a list of albums`() = runBlocking {
        val mockAlbums = listOf(mock(AlbumSimpleDTO::class.java), mock(AlbumSimpleDTO::class.java))
        `when`(networkChecker.isConnected()).thenReturn(true)
        `when`(albumServiceAdapter.getAllAlbums()).thenReturn(Result.success(mockAlbums))

        val result = albumRepository.fetchAlbums()

        assertTrue(result.isSuccess)
        assertEquals(mockAlbums, result.getOrNull())
    }

    @Test
    fun `Given network is connected and service failure When fetchAlbums is called Then it should return failure`() = runBlocking {
        val exception = RuntimeException("Service error")
        `when`(networkChecker.isConnected()).thenReturn(true)
        `when`(albumServiceAdapter.getAllAlbums()).thenReturn(Result.failure(exception))

        val result = albumRepository.fetchAlbums()

        assertTrue(result.isFailure)
        assertEquals(exception, result.exceptionOrNull())
    }

    @Test
    fun `Given no network connection When fetchAlbums is called Then it should return network failure`() = runBlocking {
        `when`(networkChecker.isConnected()).thenReturn(false)

        val result = albumRepository.fetchAlbums()

        assertTrue(result.isFailure)
        assertEquals("No internet connection", result.exceptionOrNull()?.message)
    }

    @Test
    fun `Given network is connected and service success When fetchAlbumById is called Then it should return album details`() = runBlocking {
        val albumId = "1"
        val mockAlbumDetail = AlbumDetailDTO(
            id = BigDecimal(1),
            name = "Test Album",
            cover = "Cover URL",
            releaseDate = "2022-01-01",
            description = "Description",
            performers = emptyList(),
            tracks = emptyList(),
            comments = emptyList(),
        )
        `when`(networkChecker.isConnected()).thenReturn(true)
        `when`(albumServiceAdapter.getAlbumById(albumId)).thenReturn(Result.success(mockAlbumDetail))

        val result = albumRepository.fetchAlbumById(albumId)

        assertTrue(result.isSuccess)
        assertEquals(mockAlbumDetail, result.getOrNull())
    }

    @Test
    fun `Given network is connected and service failure When fetchAlbumById is called Then it should return failure`() = runBlocking {
        val albumId = "1"
        val exception = RuntimeException("Service error")
        `when`(networkChecker.isConnected()).thenReturn(true)
        `when`(albumServiceAdapter.getAlbumById(albumId)).thenReturn(Result.failure(exception))

        val result = albumRepository.fetchAlbumById(albumId)

        assertTrue(result.isFailure)
        assertEquals(exception, result.exceptionOrNull())
    }

    @Test
    fun `Given no network connection When fetchAlbumById is called Then it should return network failure`() = runBlocking {
        val albumId = "1"
        `when`(networkChecker.isConnected()).thenReturn(false)

        val result = albumRepository.fetchAlbumById(albumId)

        assertTrue(result.isFailure)
        assertEquals("No internet connection", result.exceptionOrNull()?.message)
    }

    @Test
    fun `Given network is connected and service success When createAlbum is called Then it should return created album`() = runBlocking {
        val newAlbum = mock(AlbumSimpleDTO::class.java)
        `when`(networkChecker.isConnected()).thenReturn(true)
        `when`(albumServiceAdapter.createAlbum(newAlbum)).thenReturn(Result.success(newAlbum))

        val result = albumRepository.createAlbum(newAlbum)

        assertTrue(result.isSuccess)
        assertEquals(newAlbum, result.getOrNull())
    }

    @Test
    fun `Given network is connected and service failure When createAlbum is called Then it should return failure`() = runBlocking {
        val newAlbum = mock(AlbumSimpleDTO::class.java)
        val exception = RuntimeException("Service error")
        `when`(networkChecker.isConnected()).thenReturn(true)
        `when`(albumServiceAdapter.createAlbum(newAlbum)).thenReturn(Result.failure(exception))

        val result = albumRepository.createAlbum(newAlbum)

        assertTrue(result.isFailure)
        assertEquals(exception, result.exceptionOrNull())
    }

    @Test
    fun `Given no network connection When createAlbum is called Then it should return network failure`() = runBlocking {
        val newAlbum = mock(AlbumSimpleDTO::class.java)
        `when`(networkChecker.isConnected()).thenReturn(false)

        val result = albumRepository.createAlbum(newAlbum)

        assertTrue(result.isFailure)
        assertEquals("No internet connection", result.exceptionOrNull()?.message)
    }
}
