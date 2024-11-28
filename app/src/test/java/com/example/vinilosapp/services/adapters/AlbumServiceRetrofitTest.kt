package com.example.vinilosapp.services.adapters

import com.example.vinilosapp.models.AlbumDetail
import com.example.vinilosapp.models.AlbumSimple
import com.example.vinilosapp.models.Genre
import com.example.vinilosapp.models.RecordLabel
import com.example.vinilosapp.services.api.AlbumAPI
import kotlinx.coroutines.runBlocking
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
import retrofit2.Retrofit
import java.math.BigDecimal

@RunWith(MockitoJUnitRunner::class)
class AlbumServiceRetrofitTest {

    @Mock
    private lateinit var retrofit: Retrofit

    @Mock
    private lateinit var albumAPI: AlbumAPI

    @InjectMocks
    private lateinit var albumServiceRetrofit: AlbumServiceRetrofit

    @Before
    fun setUp() {
        `when`(retrofit.create(AlbumAPI::class.java)).thenReturn(albumAPI)
    }

    @Test
    fun `Given successful response When getAllAlbums is called Then it should return a list of albums`() = runBlocking {
        val mockAlbums = listOf(mock(AlbumSimple::class.java), mock(AlbumSimple::class.java))
        `when`(albumAPI.getAllAlbums()).thenReturn(mockAlbums)

        val result = albumServiceRetrofit.getAllAlbums()

        assertTrue(result.isSuccess)
        assertEquals(mockAlbums, result.getOrNull())
    }

    @Test
    fun `Given an error response When getAllAlbums is called Then it should return a failure`() = runBlocking {
        val exception = RuntimeException("Network error")
        `when`(albumAPI.getAllAlbums()).thenThrow(exception)

        val result = albumServiceRetrofit.getAllAlbums()

        assertTrue(result.isFailure)
        assertEquals(exception, result.exceptionOrNull())
    }

    @Test
    fun `Given successful response When getAlbumById is called Then it should return album details`() = runBlocking {
        val albumId = "1"
        val mockAlbumDetail = AlbumDetail(
            id = BigDecimal(1),
            name = "Test Album",
            cover = "Test Cover",
            releaseDate = "2022-01-01",
            description = "Description",
            performers = emptyList(),
            tracks = emptyList(),
            comments = emptyList(),
            genre = Genre.FOLK.toString(),
            recordLabel = RecordLabel.EMI.toString(),
        )
        `when`(albumAPI.getAlbumById(albumId)).thenReturn(mockAlbumDetail)

        val result = albumServiceRetrofit.getAlbumById(albumId)

        assertTrue(result.isSuccess)
        assertEquals(mockAlbumDetail, result.getOrNull())
    }

    @Test
    fun `Given an error response When getAlbumById is called Then it should return a failure`() = runBlocking {
        val albumId = "1"
        val exception = RuntimeException("Network error")
        `when`(albumAPI.getAlbumById(albumId)).thenThrow(exception)

        val result = albumServiceRetrofit.getAlbumById(albumId)

        assertTrue(result.isFailure)
        assertEquals(exception, result.exceptionOrNull())
    }

    @Test
    fun `Given successful response When createAlbum is called Then it should return created album`() = runBlocking {
        val newAlbum = mock(AlbumSimple::class.java)
        `when`(albumAPI.createAlbum(newAlbum)).thenReturn(newAlbum)

        val result = albumServiceRetrofit.createAlbum(newAlbum)

        assertTrue(result.isSuccess)
        assertEquals(newAlbum, result.getOrNull())
    }

    @Test
    fun `Given an error response When createAlbum is called Then it should return a failure`() = runBlocking {
        val newAlbum = mock(AlbumSimple::class.java)
        val exception = RuntimeException("Network error")
        `when`(albumAPI.createAlbum(newAlbum)).thenThrow(exception)

        val result = albumServiceRetrofit.createAlbum(newAlbum)

        assertTrue(result.isFailure)
        assertEquals(exception, result.exceptionOrNull())
    }
}
