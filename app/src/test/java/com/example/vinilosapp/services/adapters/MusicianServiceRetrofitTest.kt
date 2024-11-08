package com.example.vinilosapp.services.adapters

import com.example.models.MusicianDetailDTO
import com.example.models.MusicianSimpleDTO
import com.example.vinilosapp.services.api.MusicianAPI
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
class MusicianServiceRetrofitTest {

    @Mock
    private lateinit var retrofit: Retrofit

    @Mock
    private lateinit var musicianAPI: MusicianAPI

    @InjectMocks
    private lateinit var musicianServiceRetrofit: MusicianServiceRetrofit

    @Before
    fun setUp() {
        `when`(retrofit.create(MusicianAPI::class.java)).thenReturn(musicianAPI)
    }

    @Test
    fun `Given successful response When getAllMusicians is called Then it should return a list of musicians`() = runBlocking {
        val mockMusicians = listOf(mock(MusicianSimpleDTO::class.java), mock(MusicianSimpleDTO::class.java))
        `when`(musicianAPI.getMusicians()).thenReturn(mockMusicians)

        val result = musicianServiceRetrofit.getMusicians()

        assertTrue(result.isSuccess)
        assertEquals(mockMusicians, result.getOrNull())
    }

    @Test
    fun `Given an error response When getAllMusicians is called Then it should return a failure`() = runBlocking {
        val exception = RuntimeException("Network error")
        `when`(musicianAPI.getMusicians()).thenThrow(exception)

        val result = musicianServiceRetrofit.getMusicians()

        assertTrue(result.isFailure)
        assertEquals(exception, result.exceptionOrNull())
    }

    @Test
    fun `Given successful response When getMusicianById is called Then it should return musician details`() = runBlocking {
        val musicianId = "1"
        val mockMusicianDetail = MusicianDetailDTO(
            id = BigDecimal(musicianId),
            name = "Test Musician",
            description = "Description",
            image = "",
            birthDate = "",
            albums = emptyList(),
            collectors = emptyList(),
            performerPrizes = emptyList(),
        )
        `when`(musicianAPI.getMusicianById(musicianId)).thenReturn(mockMusicianDetail)

        val result = musicianServiceRetrofit.getMusicianById(musicianId)

        assertTrue(result.isSuccess)
        assertEquals(mockMusicianDetail, result.getOrNull())
    }

    @Test
    fun `Given an error response When getMusicianById is called Then it should return a failure`() = runBlocking {
        val musicianId = "1"
        val exception = RuntimeException("Network error")
        `when`(musicianAPI.getMusicianById(musicianId)).thenThrow(exception)

        val result = musicianServiceRetrofit.getMusicianById(musicianId)

        assertTrue(result.isFailure)
        assertEquals(exception, result.exceptionOrNull())
    }
}
